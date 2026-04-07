$(document).ready(function() {
	 $('#question').focus();
	
	$('#question').on('keydown', function(e) {
		if (e.key === 'Enter' && !e.shiftKey) {
			e.preventDefault();
			if (!this.value.trim()) return;
			$('#questionForm').submit();
		} else if (e.keyCode == 13 && e.shiftKey) {
			var content = this.value;
			var caretPos = this.selectionStart;
			this.value = content.substring(0, caretPos) + '\n' + content.substring(caretPos);
			this.setSelectionRange(caretPos + 1, caretPos + 1);
			autoResize.call(this);
			e.preventDefault();
		}
	});

	var textarea = document.querySelector('#question');
	textarea.addEventListener('input', autoResize, false);

	function autoResize() {
		this.style.height = 'auto';
		this.style.height = this.scrollHeight + 'px';
	}

});