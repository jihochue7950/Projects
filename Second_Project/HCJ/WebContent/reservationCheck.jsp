<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>reservationCheck</title>
   <!-- 부트스트랩 -->
     <meta name="viewport" content="width=device-width, initial-scale=1">
     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
     <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
   <!-- 제이쿼리 -->   
   <script src="https://code.jquery.com/jquery-2.2.4.min.js" 
    integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" 
    crossorigin="anonymous"></script>    
    <script src = "js/script.js"></script>
    <link rel ="stylesheet" href="css/style.css">

</head>

<body>
	<div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>
	<%String tempid = (String)session.getAttribute("id");%>
	<div style = "text-align: right;">
		<c:if test="${id != null}"> 
		<i>hello <u>${id}</u>! nice to meet you</i><br>
		<a href="Logout.jsp" class="btn btn-primary">Logout</a>
		<c:url var = "reservationTicket" value="/hcj_servlet">
				<c:param name = "command" value = "reservationticket"/>
		</c:url>
		<td><a href = "${reservationTicket}" class="btn btn-info">reservationCheck</a>
	</c:if>
	</div>
	<div class="container">
	<h2>Reservation information</h2>
		<table class="table table-hover" border = "2">
			<tr>
				<th>title</th>
				<th>date</th>
				<th>time</th>
				<th>running_time</th>
				<th>theater</th>
				<th>seat_name</th>
				<th>cancel</th>
			</tr>
		<c:forEach var= "reservationTicket" items = "${reservationTicketTemp}">
						
			<tr>
				<td>${reservationTicket.title}</td>
				<td>${reservationTicket.date}</td>
				<td>${reservationTicket.time}</td>
				<td>${reservationTicket.running_time}</td>
				<td>${reservationTicket.theater}</td>
				<td> ${reservationTicket.seat_name}</td>
				<td><c:url var = "cancelLink" value="/hcj_servlet">
					<c:param name = "command" value = "cancel"/>
					<c:param name = "tempindex" value = "${reservationTicket.index}"/>
				</c:url>
				<a href = "${cancelLink}" class="btn btn-dark">Cancel</a></td>
			</tr>
		
		</c:forEach>
		</table>
		<hr>
	
	<h2>Reservation cancellation information</h2>
	<table border = "2">
				<tr>
					<th>title</th>
					<th>date</th>
					<th>time</th>
					<th>running_time</th>
					<th>theater</th>
					<th>seat_name</th>
					<th>cancel</th>
				</tr>
			<c:forEach var= "deleteCancelTicket" items = "${canceledTicketTemp}">
							
				<tr>
					<td>${deleteCancelTicket.title}</td>
					<td>${deleteCancelTicket.date}</td>
					<td>${deleteCancelTicket.time}</td>
					<td>${deleteCancelTicket.running_time}</td>
					<td>${deleteCancelTicket.theater}</td>
					<td> ${deleteCancelTicket.seat_name}</td>
					<td><c:url var = "deleteLink" value="/hcj_servlet">
						<c:param name = "command" value = "delete"/>
						<c:param name = "tempindex" value = "${deleteCancelTicket.index}"/>
					</c:url>
					<a href = "${deleteLink}" class="btn btn-dark">Delete</a></td>
				</tr>
			
			</c:forEach>
			</table>
			</div>
	
</body>
</html>
