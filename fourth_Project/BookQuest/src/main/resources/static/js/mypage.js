$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var chartData = null; // 적용 버튼을 눌렀을 때 업데이트할 그래프 데이터
	var radarChart = null; // 레이더 차트 객체
	var barChart = null;

	$.ajax({
		type: "POST",
		url: '/BookQuest/bookCountsByCategory',
		headers: { "content-type": "application/json" },
		dataType: "json",
		beforeSend: function(xhr) {  /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
			xhr.setRequestHeader(header, token);
		},

		success: function(bookCountsByCategories) {
			var labels = [];
			var data = [];
			var sortedCounts = Object.entries(bookCountsByCategories[1]).sort((a, b) => b[1] - a[1]);
			var topCounts = sortedCounts.slice(0, 6).map(([category, count]) => count);
			$.each(sortedCounts, function(index, [category, count]) {
				if (topCounts.includes(count)) {
					labels.push(category);
					data.push(count);
				} else {
					return false; // top 6 이외의 데이터는 추가하지 않음
				}
			});

			chartData = {
				labels: labels,
				datasets: [
					{
						backgroundColor: "rgba(87,71,230,0.7)",
						borderColor: "rgba(167,159,242,0.6)",
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
				barThickness: 12
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

	$.ajax({
		type: "POST",
		url: '/BookQuest/bookCountsByCategory',
		headers: { "content-type": "application/json" },
		dataType: "json",
		beforeSend: function(xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
			xhr.setRequestHeader(header, token);
		},

		success: function(bookCountsByCategories) {
			var labels = [];
			var data = [];
			var categories = bookCountsByCategories[0];
			var counts = bookCountsByCategories[1];
			var sortedCounts = Object.entries(counts).sort((a, b) => b[1] - a[1]);
			var topCategories = sortedCounts.slice(0, 6).map(([category, count]) => category);
			$.each(categories, function(index, category) {
				if (counts.hasOwnProperty(category)) {
					if (topCategories.includes(category)) { // top 6 카테고리 중 포함된 경우에만 label에 push
						labels.push(category);
						data.push(counts[category]);
					}
				} else {
					data.push(0);
				}
			});

			chartData = {
				labels: labels,
				datasets: [
					{
						backgroundColor: "rgba(87,71,230,0.5)",
						borderColor: "rgba(167,159,242,0.6)",
						pointBackgroundColor: "rgba(87,71,230,0.5)",
						pointBorderColor: "#fff",
						pointHoverBackgroundColor: "#fff",
						pointHoverBorderColor: "rgba(87,71,230,0.9)",
						data: data,
					},
				],
			};
			var options = {
				responsive: true,
				plugins: {
					legend: {
						display: false,
					}
				},
				maintainAspectRatio: false,
				scale: {
					ticks: {
						beginAtZero: true,
						maxTicksLimit: 5
					}
				}
			};

			var ctx = document.getElementById("radar-chart").getContext("2d");
			radarChart = new Chart(ctx, {
				type: "radar",
				data: chartData,
				options: options,
			});
		},
		error: function(xhr, textStatus, errorThrown) {
			console.error("Error fetching JSON data:", textStatus, errorThrown);
		},
	});

	// category_select_button 클릭 이벤트 핸들러
	$('#category_select_button').on('click', function() {
		// radio button을 포함한 div의 jQuery 객체
		var categorySelectDiv = $('#category_select_div');

		// 숨겨져 있던 div를 보이도록 하거나, 보여져 있던 div를 숨기도록 toggle() 메서드 사용
		categorySelectDiv.toggle();
	});

	// 적용 버튼 클릭 이벤트 핸들러
	$(".apply_button").click(function() {
		token = $("meta[name='_csrf']").attr("content");
		header = $("meta[name='_csrf_header']").attr("content");
		// 선택된 체크박스 항목들의 값을 가져와서 그래프 데이터 업데이트
		let arr = [];

		$('input[name="category_select"]:checked').each(function() {
			var checkVal = $(this).val();
			arr.push(checkVal);
		});

		if (arr.length !== 6) {
			alert('6개의 카테고리를 선택해주세요!');
			return;
		}

		$.ajax({
			type: 'POST',
			url: '/BookQuest/selectedCategory',

			headers: { "content-type": "application/json" }, // 요청 헤더
			dataType: 'json', // 전송받을 데이터의 타입
			data: JSON.stringify(arr),
			beforeSend: function(xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(selectedBookCountsByCategories) {
				var newLabels = [];
				var newData = [];
				var newCategories = selectedBookCountsByCategories[0]; // 카테고리 리스트
				var newCounts = selectedBookCountsByCategories[1]; // 카테고리별 책 개수
				$.each(newCategories, function(index, category) {
					newLabels.push(category);
					if (newCounts.hasOwnProperty(category)) {
						newData.push(newCounts[category]);
					} else {
						newData.push(0);
					}
				});
				console.log(newLabels);
				console.log(newData);

				// 차트 데이터 업데이트
				radarChart.data.labels = newLabels;
				radarChart.data.datasets[0].data = newData;
				radarChart.update();

				barChart.data.labels = newLabels;
				barChart.data.datasets[0].data = newData;
				barChart.update();

				// 로컬 스토리지에 차트 데이터 저장(안 되고 있음..)
				localStorage.setItem('chartData', JSON.stringify(chartData));

				// 로컬 스토리지에서 차트 데이터 불러오기(안 되고 있음..)
				var storedChartData = localStorage.getItem('chartData');
				if (storedChartData) {
					chartData = JSON.parse(storedChartData);
					radarChart.data = chartData;
					radarChart.update();
				}
			},
			error: function() { alert("error!") }
		});

		// div 다시 숨기기
		$('#category_select_div').toggle();
	});

	// 취소 버튼 클릭 이벤트 핸들러
	$('.cancel_button').on('click', function() {
		// div 다시 숨기기
		$('#category_select_div').toggle();
	});

	// 체크박스 버튼 중 선택 가능한 수 제한
	var count = 6;
	var checkboxes = $('input[type="checkbox"]');
	checkboxes.on('change', function(evt) {
		var currentCount = checkboxes.filter(':checked').length;
		if (currentCount == count) { // add check for currentCount > 0
			checkboxes.filter(':not(:checked)').prop('disabled', true);
		} else {
			checkboxes.filter(':not(:checked)').prop('disabled', false);
		}
	});
});