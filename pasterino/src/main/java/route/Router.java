package route;

import static route.RandomStringGenerator.generate;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import freemarker.template.Configuration;
import redis.clients.jedis.Jedis;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Router {
    private static final String TITLE = "Pasteri.no";
    private static final String REDIS_HOST = "192.168.99.100";
    private static final int REDIS_PORT = 32768;
    private static final Jedis DB = new Jedis(REDIS_HOST, REDIS_PORT);
    
    public static void main(String[] args) throws Throwable {
        DB.flushDB();
        
       	final File STATIC_DIR = new File(Router.class.getResource("/").toURI());
        
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(STATIC_DIR);
        FreeMarkerEngine fme = new FreeMarkerEngine(config);

        port(8080);

        get("/", (req, res) -> {
            Map<String, Object> fields = new HashMap<>();
            fields.put("title", TITLE);
            fields.put("desc", "A place for your Java snippets.");

            return new ModelAndView(fields, "index.ftl");
        }, fme);

        get("/favicon.ico",
                (req, res) -> {
                    res.raw().setContentType("image/x-icon");
                    res.status(200);
                    byte[] icon = Files.readAllBytes(new File(STATIC_DIR
                            .getAbsolutePath() + File.separator + "favicon.ico")
                            .toPath());
                    res.raw().getOutputStream().write(icon);
                    return "";
                });

        get("/browse",
                (req, res) -> {
                    Map<String, Object> fields = new HashMap<>();
                    fields.put("title", TITLE);
                    fields.put("desc", "A place for your Java snippets.");

                    StringBuilder keyList = new StringBuilder();
                    Set<String> keys = DB.keys("*");

                    if (keys.size() == 0)
                        keyList.append("Nothing to see here yet :-(")
                                .append("<br>")
                                .append("Go ahead and <a href=\"/\">create a paste</a>!");
                    else {
                        keyList.append("<ul>");
                        keys.forEach((file) -> {
                            keyList.append("<li>").append("<a href=\"/pastes/")
                                    .append(file).append("\">").append(file)
                                    .append("</a></li>");
                        });
                        keyList.append("</ul>");
                    }

                    fields.put("pastelist", keyList.toString());
                    return new ModelAndView(fields, "browse.ftl");
                }, fme);

        get("/pastes/*", (req, res) -> {
            if (req.splat().length == 0) {
                return new ModelAndView(null, "404.ftl");
            }
            Map<String, Object> fields = new HashMap<>();
            fields.put("title", TITLE);
            fields.put("desc", "A place for your Java snippets.");
            String storedPaste = load(req.splat()[0]);
            if (storedPaste.length() == 0) {
                res.redirect("/404");
                fields.put("code", "Paste not found.");
            } else
                fields.put("code", load(req.splat()[0]));

            return new ModelAndView(fields, "paste.ftl");
        }, fme);

        post("/addPaste", (req, res) -> {
            String id = generate(16);
            String text = req.queryParams("text");
            if (text.trim().length() != 0) {
                store(text, id);
                res.redirect("/pastes/" + id);
            } else {
                res.redirect("/");
            }
            return "";
        });

        get("/about", (req, res) -> {
            Map<String, Object> fields = new HashMap<>();
            fields.put("title", TITLE);
            fields.put("desc", "A place for your Java snippets.");
            return new ModelAndView(fields, "about.ftl");
        }, fme);

        get("*", (req, res) -> {
            return new ModelAndView(null, "404.ftl");
        }, fme);
    }

    public static void store(String text, String key) {
        DB.set(key, text);
    }

    public static String load(String key) {
        String result = DB.get(key);
        return Objects.isNull(result) ? "" : result;
    }
}