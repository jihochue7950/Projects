function increment() {
	var value = parseInt(document.getElementById("number").value, 10);
	value++;
	document.getElementById("number").value = value;
}

function decrement() {
	var value = parseInt(document.getElementById("number").value, 10);
	if (value > 1) {
		value--;
	}
	document.getElementById("number").value = value;
}

function incrementbook() {
	var value = parseInt(document.getElementById("bookquantity").value, 10);
	value++;
	document.getElementById("bookquantity").value = value;
}

function decrementbook() {
	var value = parseInt(document.getElementById("bookquantity").value, 10);
	if (value > 1) {
		value--;
	}
	document.getElementById("bookquantity").value = value;
}

function openTab(evt, tabName) {
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(tabName + "-content").style.display = "block";
	evt.currentTarget.className += " active";
}

$(document).ready(function() {
	//리뷰작성할떄 별점 없으면 alert창
	$('.buttonDoneStar').click(function(event) {
		if (!$('input[name="starRating"]').is(':checked')) {
			// prevent form submission
			event.preventDefault();
			alert('별점을 선택해주세요.');
		}
	});
});

function putonitem() {
	// 확인 메시지 띄우기
	if (confirm("장바구니에 담으시겠습니까 ?")) {
		return true;
	} else {
		return false;
	}
}

function editComment(num) {
	// 수정된 코멘트 가져오기
	const newComment = document.querySelector(`#reivewsBoxCommentEdit` + num + ` #reivewsBoxCommentInput`).value;
	// form 요소에 수정된 코멘트 값 설정하기
	const form = document.querySelector(`#reivewsBoxCommentEdit` + num + ` form`);
	const input = form.querySelector(`input[name="updateReivew"]`);
	input.value = newComment;

	// form 요소 서버로 전송하기
	form.submit();
}

//클릭했을때 리뷰 수정창 보이게하기
$(document).ready(function() {
	$('.showEditForm').click(function() {
		var num = $(this).attr('id').replace('reivewsBoxComment', '');
		document.getElementById("reivewsBoxComment" + num).style.display = "none";
		document.getElementById("reivewsBoxCommentEdit" + num).style.display = "block";
	});
});

function cancelEdit(num) {
	document.getElementById("reivewsBoxComment" + num).style.display = "block";
	document.getElementById("reivewsBoxCommentEdit" + num).style.display = "none";
}


//리뷰 삭제하기
function submitDeleteForm() {
	if (confirm("정말 리뷰를 삭제 하시겠습니까?")) {
		document.getElementById("deleteForm").submit();
	} else {
		return false;
	}
}

//별점 가로막대 그래프
$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var barChart = null;

	var bookId = parseInt($('input[name="book"]').val());
	console.log("bookId: " + bookId);

	$.ajax({
		type: "POST",
		url: '/BookQuest/countStarRating',
		headers: { "content-type": "application/json" },
		dataType: "json",
		data: JSON.stringify(bookId),
		beforeSend: function(xhr) {  /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
			xhr.setRequestHeader(header, token);
		},

		success: function(countStarRating) {
			var labels = ["5점", "4점", "3점", "2점", "1점"];
			var data = [];
			data.push(countStarRating[5]);
			data.push(countStarRating[4]);
			data.push(countStarRating[3]);
			data.push(countStarRating[2]);
			data.push(countStarRating[1]);
			console.log(labels);
			console.log(data);

			chartData = {
				labels: labels,
				datasets: [
					{
						backgroundColor: "rgba(255, 221, 92, 1)",
						borderColor: "rgba(255,255,255,1)",
						data: data,
					},
				],
			};
			var options = {
				indexAxis: 'y',
				responsive: true,
				plugins: {
					legend: {
						display: false
					}
				},
				maintainAspectRatio: false,
				scales: {
					y: {
						grid: {
							display: false
						}
					},
					x: {
						ticks: {
							display: false
						},
						grid: {
							display: false
						}
					}
				},
				elements: {
					bar: {
						borderRadius: 100
					}
				},
				barThickness: 6
			};

			var ctx = document.getElementById("bar-chart").getContext("2d");
			barChart = new Chart(ctx, {
				type: "bar",
				data: chartData,
				options: options,
			});
		},
		error: function(xhr, textStatus, errorThrown) {
			console.error("Error fetching JSON data:", textStatus, errorThrown);
		},
	});
});
