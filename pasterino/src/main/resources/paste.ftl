<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="${desc}">
	<meta name="keywords" content="java, paste, snippet, code, sourcecode">
	<meta name="author" content="Selim Dincer">
	<title>${title}</title>
	<#include "stylesheets.ftl">
</head>
<body>
	<#include "header.ftl">
	
	<div class="container" style="margin-bottom: 80px;">
		<style>
			#editor {
			    height: 620px;
			    width: 100%;
			    font-size: 14px;
			    border-color: #444;
			}
		</style>
		
		<pre id="editor">${code}</pre>
	</div>
	
	<#include "footer.ftl">
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/ace.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/mode-java.js" type="text/javascript" charset="utf-8"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/theme-textmate.js" type="text/javascript" charset="utf-8"></script>
  	<script>
		var editor = ace.edit("editor");
		editor.setTheme("ace/theme/textmate");
		editor.getSession().setMode("ace/mode/java");
		editor.setOptions({
		    readOnly: true,
		    highlightActiveLine: false,
		    highlightGutterLine: false
		});
		editor.renderer.$cursorLayer.element.style.opacity=0;
	</script>
</body>
</html> 