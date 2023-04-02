$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.location = "[[@{/sign}]]";
	});
});

var formSubmitting = false;


/*$(document).ready(function() {
	$('button#duplicateCheckButton').click(function(event) {
		event.preventDefault();
		checkEmailUniqueButton($('#userId').val(), $('#email').val());
	});
});*/

function checkPassword() {
	var password1 = document.getElementById("password").value;
	var password2 = document.getElementById("password_check").value;
	if (password1 != password2) {
		document.getElementById("passwordError").innerHTML = "비밀번호가 일치하지 않습니다.";
	} else {
		document.getElementById("passwordError").innerHTML = "";
	}
}