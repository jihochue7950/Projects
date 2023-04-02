function validateForm() {
	// 체크박스 요소를 가져옵니다.
	const checkboxes = document.querySelectorAll('input[type="checkbox"]');

	// 선택된 체크박스의 개수를 셉니다.
	const checkedCount = Array.from(checkboxes).filter(checkbox => checkbox.checked).length;

	// 체크박스가 하나도 선택되지 않은 경우를 처리합니다.
	if (checkedCount === 0) {
		alert('적어도 하나의 결제수단을 선택해주세요.');
		return false;
	}

	// 체크박스 중 두 개 이상이 선택된 경우를 처리합니다.
	if (checkedCount > 1) {
		alert('한개의 결제수단만 선택해주세요.');
		return false;
	}

	// 유효성 검사가 통과되었으므로, 폼을 제출합니다.
	if (confirm("구매 하시겠습니까?")) {
		return true;
	} else {
		return false;
	}


}