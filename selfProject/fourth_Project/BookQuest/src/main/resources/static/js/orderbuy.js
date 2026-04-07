function showEasyPaymentBox() {
	var paymentSelectBox = document.getElementById("payment_select_box");
	paymentSelectBox.style.display = "none";
	var easyPaymentButtonBox = document.getElementById("easyPayment_button_box");
	if (easyPaymentButtonBox.style.display === "none") {
		easyPaymentButtonBox.style.display = "flex";
	} else {
		easyPaymentButtonBox.style.display = "none";
	}
}

function showPaymentSelectBox() {
	var easyPaymentButtonBox = document.getElementById("easyPayment_button_box");
	easyPaymentButtonBox.style.display = "none";
	var paymentSelectBox = document.getElementById("payment_select_box");
	if (paymentSelectBox.style.display === "none") {
		paymentSelectBox.style.display = "flex";
	} else {
		paymentSelectBox.style.display = "none";
	}
}

function confirmPayment() {
	if (confirm("결제하시겠습니까?")) {
		return true;
	} else {
		return false;
	}
}
