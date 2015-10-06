<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/ace.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/mode-java.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.0/theme-textmate.js" type="text/javascript" charset="utf-8"></script>
<script>
	if(document.getElementById('editor')) {
		var editor = ace.edit("editor");
		editor.setTheme("ace/theme/textmate");
		editor.getSession().setMode("ace/mode/java");
		editor.getSession().on('change', function() {
			update();
		});
		
		function update() {
			var hiddenTextField = document.getElementById('text');
			var aceContent = editor.getValue();
			hiddenTextField.value = aceContent;		
		}
	}
</script>