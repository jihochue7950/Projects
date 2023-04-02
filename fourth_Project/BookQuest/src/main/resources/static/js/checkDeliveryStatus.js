function showOrderDetailTable(num) {
	var div = document.getElementById("orderDetailTable"+num);
	if (div.style.display === "none") {
		div.style.display = "block";
	} else {
		div.style.display = "none";
	}
}
