$(document).ready(function() {
	$("#bookCover").change(function() {
		fileSize = this.files[0].size;
		if (fileSize > 1048576 * 2) {
			this.setCustomValidity("You must choose an image less than 2MB!");
			this.reportValidity();
		} else {
			this.setCustomValidity("");
			showImageThumbnail(this);
		}
	});
});

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
}

$(document).ready(function() {
	$("#deleteButton").click(function() {
		if (!confirm("책정보를 삭제합니다")) {
			return false;
		} else {
			if (!confirm("삭제 후에는 복구가 불가능합니다.\n정말로 삭제하시겟습니까?")) {
				return false;
			} else {
				alert("삭제가 완료되었습니다.");
			}
		}
	});
});

