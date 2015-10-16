package route;

import static route.RandomStringGenerator.generate;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.threadPool;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JOptionPane;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Router {
    private static final String TITLE = "Pasteri.no";
    private static final String REDIS_PASSWORD = "";
    private static final String REDIS_URI = "redis://" + REDIS_PASSWORD + "@bluegill.redistogo.com:9645/0";
    private static final RedisClient REDIS_CLIENT = RedisClient.create(REDIS_URI);
    private static final RedisCommands<String, String> DB = REDIS_CLIENT.connect().sync();
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    
    public static void main(String[] args) throws Throwable {
        Configuration config = new Configuration();
        config.setTemplateLoader(new ClassTemplateLoader(Router.class, "/"));
        FreeMarkerEngine fme = new FreeMarkerEngine(config);

        threadPool(AVAILABLE_PROCESSORS * 2);
        port(8080);

        DB.flushdb();
        
        get("/", (req, res) -> {
            Map<String, Object> fields = new HashMap<>();
            fields.put("title", TITLE);
            fields.put("desc", "A place for your Java snippets.");

            return new ModelAndView(fields, "resources/index.ftl");
        }, fme);

        get("/favicon.ico",
                (req, res) -> {
                    res.raw().setContentType("image/x-icon");
                    res.status(200);
                    res.raw().getOutputStream().write(Favicon.iconData);
                    return "";
                });

        get("/browse",
                (req, res) -> {
                    Map<String, Object> fields = new HashMap<>();
                    fields.put("title", TITLE);
                    fields.put("desc", "A place for your Java snippets.");

                    StringBuilder keyList = new StringBuilder();
                    List<String> keys = DB.keys("*");

                    if (keys.size() == 0)
                        keyList.append("Nothing to see here yet :-(")
                                .append("<br>")
                                .append("Go ahead and <a href=\"/\">create a paste</a>!");
                    else {
                        keyList.append("<ul>");
                        keys.forEach((key) -> {
                            keyList.append("<li>").append("<a href=\"/pastes/")
                                    .append(key).append("\">").append(key)
                                    .append("</a></li>");
                        });
                        keyList.append("</ul>");
                    }

                    fields.put("pastelist", keyList.toString());
                    return new ModelAndView(fields, "resources/browse.ftl");
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

            return new ModelAndView(fields, "resources/paste.ftl");
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
        
        get("/exit", (req, res) -> {
        	System.exit(0);
        	return "";
        });

        get("*", (req, res) -> {
            return new ModelAndView(null, "resources/404.ftl");
        }, fme);
        
        Desktop.getDesktop().browse(URI.create("http://localhost:8080/"));
        
        JOptionPane.showMessageDialog(null, "Visit localhost:8080/exit to stop the server.");
    }

    public static void store(String text, String key) {
        DB.set(key, text);
    }

    public static String load(String key) {
        String result = DB.get(key);
        return Objects.isNull(result) ? "" : result;
    }
}