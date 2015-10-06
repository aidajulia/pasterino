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
		
		<h3>All pastes</h3>
		${pastelist}
	</div>
	
	<#include "footer.ftl">
    
    <#include "scripts.ftl">
</body>
</html> 