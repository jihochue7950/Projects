$(document).ready(function() {
	const checkboxes = document.querySelectorAll('input[type=checkbox]');
	const upbutton = document.getElementsByClassName("UpBtn");
	const downbutton = document.getElementsByClassName("DownBtn");

	// 체크박스 변경 이벤트 처리 함수
	function handleCheckboxChange() {
		for (let j = 0; j < upbutton.length; j++) {
			upbutton[j].disabled = !checkboxes[j].checked;
		}
		for (let j = 0; j < downbutton.length; j++) {
			downbutton[j].disabled = !checkboxes[j].checked;
		}
	}

	// 모든 이벤트에 대해 이벤트 처리 함수를 호출합니다.
	$(document).on('change click keydown', handleCheckboxChange);
	$(document).on('click', '#selectAll', toggleSelect);

	function toggleSelect() {
		let allChecked = true;
		checkboxes.forEach((checkbox) => {
			if (!checkbox.checked) {
				allChecked = false;
			}
		});
		if (allChecked) {
			// 모든 체크박스가 선택된 상태이면 모두 선택 해제합니다.
			checkboxes.forEach((checkbox) => {
				checkbox.checked = false;
			});
			document.querySelector("#selectAll").textContent = '전체 선택';
			$("#totalPriceForlast").text(0 + "원");
			$("#totalPriceAndDelivery").text(0 + "원");
			checking = $("#selectAll").text();
			console.log("checking==" + checking);
			if (checking == "전체 선택") {
				console.log("제발");
			}
		} else {
			// 모든 체크박스가 선택되지 않은 상태이면 모두 선택합니다.
			let priceArr = [];
			checkboxes.forEach((checkbox) => {
				totalpricecomplete = 0;
				checkbox.checked = true;
				var totalprice = $("#totalprice" + checkbox.value).text();
				strtotalPrice = totalprice.replace(",", "").replace("원", "");
				totalpriceint = parseInt(strtotalPrice);
				priceArr.push(totalpriceint);
			});

			let totalPriceComplete = 0;
			for (let i = 0; i < priceArr.length; i++) {
				totalPriceComplete += priceArr[i];
			}

			document.querySelector("#selectAll").textContent = '전체 해제';
			$("#totalPriceForlast").text(totalPriceComplete.toLocaleString() + "원");
			$("#totalPriceAndDelivery").text(totalPriceComplete.toLocaleString() + "원");
		}
		// 체크박스 변경 이벤트를 처리합니다.
		handleCheckboxChange();
	}
});













function agreeCheck(frm) {
	if (frm.checkButton.disabled == true)
		frm.checkButton.disabled = false
	else
		frm.checkButton.disabled = true
}


$(document).ready(function() {

	$(".UpBtn").click(function() {

		var token = $("meta[name='_csrf']").attr("content");
		//var token = $("input[name='_csrf']").val();
		var header = $("meta[name='_csrf_header']").attr("content");
		cartId = this.value;
		bookPrice = $("#price" + cartId).text();
		strbookPrice = bookPrice.replace(",", "").replace("원", "");
		totalPriceForBuy = $("#totalprice" + cartId).text();
		strtotalPriceForBuy = totalPriceForBuy.replace(",", "").replace("원", "");
		bookPriceint = parseInt(strbookPrice, 10);
		totalPriceForBuyint = parseInt(strtotalPriceForBuy, 10);
		totalcompletePrice = bookPriceint + totalPriceForBuyint;
		lastComplet = totalcompletePrice.toLocaleString();
		plusprice = $("#totalPriceForlast").text().replace(",", "").replace("원", "");
		pluspriceint = parseInt(plusprice, 10);
		console.log("pluspriceint=" + pluspriceint);
		console.log("pluspriceinttype=" + typeof pluspriceint);
		pluspriceComplete = pluspriceint + bookPriceint;
		$.ajax({
			type: 'POST',       // 요청 메서드
			url: '/BookQuest/up',  // 요청 URI
			headers: { "content-type": "application/json" }, // 요청 헤더
			dataType: 'json', // 전송받을 데이터의 타입
			data: JSON.stringify(cartId),
			beforeSend: function(xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				bookQuantity = JSON.parse(result);    // 서버로부터 응답이 도착하면 호출될 함수
				//   alert("수량이 " + result + "권으로 변경 되었습니다 ");        // result는 서버가 전송한 데이터
				$("#quantity" + cartId).text(bookQuantity);
				$("#totalprice" + cartId).text(lastComplet + '원');
				$("#totalPriceForBuy").text(lastComplet + '원');
				$("#totalPriceForlast").text(pluspriceComplete.toLocaleString() + "원");
				$("#totalPriceAndDelivery").text(pluspriceComplete.toLocaleString() + "원");
			},

			error: function() { alert("error") } // 에러가 발생했을 때, 호출될 함수
		}); // $.ajax()

	});

});


$(document).ready(function() {

	$(".DownBtn").click(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		cartId = this.value;
		bookPrice = $("#price" + cartId).text();
		console.log(bookPrice);
		strbookPrice = bookPrice.replace(",", "").replace("원", "");
		totalPriceForBuy = $("#totalprice" + cartId).text();
		strtotalPriceForBuy = totalPriceForBuy.replace(",", "").replace("원", "");
		bookPriceint = parseInt(strbookPrice, 10);
		totalPriceForBuyint = parseInt(strtotalPriceForBuy, 10);
		totalPriceForBuyint = parseInt(totalPriceForBuy.replace(",", "").replace("원", ""), 10);
		totalcompletePrice = totalPriceForBuyint - bookPriceint;
		lastComplet = totalcompletePrice.toLocaleString();
		minusprice = $("#totalPriceForlast").text().replace(",", "").replace("원", "");
		minuspriceint = parseInt(minusprice, 10);
		minuspriceComplete = minuspriceint - bookPriceint;
		$.ajax({
			type: 'POST',       // 요청 메서드
			url: '/BookQuest/down',  // 요청 URI
			headers: { "content-type": "application/json" }, // 요청 헤더
			dataType: 'json', // 전송받을 데이터의 타입
			data: JSON.stringify(cartId),
			beforeSend: function(xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {

				bookQuantity = JSON.parse(result);
				if (bookQuantity == -1) {
					alert("이미 최소 수량입니다");
					return;
				}

				//alert("수량이 " + result + "권으로 되었습니다 ");       // result는 서버가 전송한 데이터
				$("#quantity" + cartId).text(bookQuantity);
				$("#totalprice" + cartId).text(lastComplet + '원');
				$("#totalPriceForBuy").text(lastComplet + '원');
				$("#totalPriceForlast").text(minuspriceComplete.toLocaleString() + "원");
				$("#totalPriceAndDelivery").text(minuspriceComplete.toLocaleString() + "원");
			},
			error: function() { alert("error") } // 에러가 발생했을 때, 호출될 함수
		}); // $.ajax()

	});

});


function validForm() {
	var checkboxes = document.querySelectorAll('input[type=checkbox]');
	var checked = false;
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			checked = true;
			break;
		}
	}
	if (!checked) {
		alert("한 가지의 상품을 선택해주세요");
		return false;
	}
	return true;
}

$(document).ready(function() {
	$(".myCheckbox").click(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		let arr = [];//배열을 하나 만들어주고
		$("input:checkbox[name=selectItem]:checked").each(function() {
			var checkVal = $(this).val();
			arr.push(checkVal);
		});
		// 그 안에다가 체크박스중에 check가 되어 있는 체크박스들의 value= cartId를 가져와서 하나씩 넣어줍니다.
		document.querySelector("#selectAll").textContent = '전체 선택';
		$.ajax({
			type: 'POST',       // 요청 메서드
			url: '/BookQuest/price',  // 요청 URI
			headers: { "content-type": "application/json" }, // 요청 헤더
			dataType: 'json', // 전송받을 데이터의 타입
			data: JSON.stringify(arr),
			beforeSend: function(xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {

				totalPrice = JSON.parse(result);

				$("#totalPriceForlast").text(totalPrice.toLocaleString() + "원");
				$("#totalPriceAndDelivery").text(totalPrice.toLocaleString() + "원");

			},
			error: function() { alert("error") } // 에러가 발생했을 때, 호출될 함수
		}); // $.ajax()

	});
});