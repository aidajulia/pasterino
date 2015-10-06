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
			    height: 600px;
			    width: 100%;
			    font-size: 14px;
			    border-color: #444;
			}
		</style>
		
		<div class="jumbotron">
			<h2>About ${title}</h2>
			<p>${title} is a little fun project by <a href="http://selim.co/" target="_blank">Selim Dincer</a>. It allows you to store your Java code snippets and was created for educational purposes.</p>
			
			<p>Check out my github profile to find more projects</p>
			<a class="btn btn-default" href="http://www.github.com/wowselim/" target="_blank"><i class="fa fa-external-link"></i> github</a>
		</div>
	</div>
	
	<#include "footer.ftl">
    
    <#include "scripts.ftl">
</body>
</html> 