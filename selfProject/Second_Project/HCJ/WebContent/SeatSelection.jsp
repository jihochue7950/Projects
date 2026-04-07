<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="iso-8859-1">
<title>SeatSelection</title>
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

	<style>
	/* css not applied checkbox not visible */
	input[class="seat_box"] {
		display: none;
	}
	/* css not applied checkbox not visible */
 	input[class="seat_reserved"] {
		display: none;
	}
	/* empty seat unchecked */
	input[class="seat_box"] + label {
		display: inline-block;
		width: 30px;
		height: 30px;
		border: 2px solid #bcbcbc;
		background-color: #123456;
		cursor: pointer;
	}
	/* empty seat checked */
	input[class="seat_box"]:checked + label {
		background-color: #8b00ff;		
		/* background-image:url('./images/check-off.png'); */
		/* background: url('./images/check-off.png') no-repeat 0 0px / contain; */
	}
	/* reserved seat */
	input[class="seat_reserved"] + label {
		display: inline-block;
		width: 30px;
		height: 30px;
		border: 2px solid #bcbcbc;
		background-color: #cdcdcd;
	}
	</style>

</head>

<body>
<div class="container">
	<div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>
	
	
	<%String sessionid = (String)session.getAttribute("id"); %>

   <div style = "text-align: right;">      
   <c:if test="${id == null}">
	   <a href="Login.jsp" class="btn btn-primary">Login</a>   
	   <!-- <a href ="Join_Member.jsp" class="btn btn-primary">Join_member</a>  -->
	   <a href ="Join_Member.jsp" class="btn btn-primary">Join_member</a>
	</c:if>  
	<c:if test="${id != null}"> 
		<i>hello <u>${id}</u>! nice to meet you</i><br>
		<a href="Logout.jsp" class="btn btn-primary">Logout</a>
		<c:url var = "reservationTicket" value="/hcj_servlet">
				<c:param name = "command" value = "reservationticket"/>
		</c:url>
		<td><a href = "${reservationTicket}" class="btn btn-info">reservationCheck</a>
	</c:if>
   </div>
   
   <h3>Seat Choice</h3>

	<form action = "hcj_servlet" method="GET" class="was-validated">
		<input type="hidden" name="command" value="reservation" />
		<input type="hidden" name="sch_num" value="${sch_num}"/>		
		<input type="hidden" name ="sessionId" value ="<%=sessionid%>"/>
		
		<table border = "2">
		
		    <tr><th>\</th>
			<%char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F','G', 'H', 'I', 'J'};%>
			<%request.setAttribute("alphabet", alphabet);%>
				<c:forEach var = "i" begin = "1" end = "10">
					<th scope="col">${i}</th>
				</c:forEach>
		    </tr>
				<c:forEach var = "temp_seat" items = "${select_seat}" varStatus ="loop" >	
				<c:set var = "Vertical" value = "${(loop.count-1)/10}"/>
				
		   		<c:if test="${temp_seat.seat_index % 10 eq 1}">
					<tr> <td>${alphabet[Vertical]}</td>
				</c:if>
		   
				<c:choose>
					<c:when test="${temp_seat.sch_num eq 0}">
						<td><input type = "checkbox" id = "seat_box_${temp_seat.seat_index}" class = "seat_box" name = "seat_to_reserve" value = "${temp_seat.seat_index}">
						${temp_seat.seat_name}
						<label for="seat_box_${temp_seat.seat_index}"></label></td>
					</c:when>
				
					<c:otherwise>
						<td><input type = "checkbox" id = "seat_reserved_${temp_seat.seat_index}" class = "seat_reserved" disabled>
						${temp_seat.seat_name}
						<label for="seat_reserved_${temp_seat.seat_index}"></label></td>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${temp_seat.seat_index % 10 eq 0}">
					</tr>
				</c:if>
				
		</c:forEach>	
		</table>	
		<input class="btn btn-info" type = "submit" value ="reservation"/>
	</form>
</div>
</body>
</html>