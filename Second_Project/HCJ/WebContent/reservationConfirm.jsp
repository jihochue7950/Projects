<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>reservationConfirm</title>
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
	<style>
	.background_image{
		
		position: relative;
	}
	.reservation_ticket{
	position:absolute;
	top:50%;
	left:50%;
	transform : translate ( -50%, -50%);
	}
	
	</style>
<body>

<div class="container">
   <div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>
   <%String tempid = (String)session.getAttribute("id");%>
   <div style = "text-align: right;">
      <c:if test="${id != null}">       
         <i>hello <u><%=tempid %></u>! nice to meet you</i><br>
      </c:if>
   </div>
<div class="container">
   <div style = "text-align: right;">      
   <c:if test="${id == null}">
	   <a href="Login.jsp" class="btn btn-primary">Login</a>   
	   <a href ="Join_Member.jsp" class="btn btn-primary">Join_member</a>  
	</c:if>  
	<c:if test="${id != null}"> 
		
		<a href="Logout.jsp" class="btn btn-primary">Logout</a>
		<c:url var = "reservationTicket" value="/hcj_servlet">
				<c:param name = "command" value = "reservationticket"/>
		</c:url>
		<td><a href = "${reservationTicket}" class="btn btn-info">reservationCheck</a>
	</c:if>
   </div>   
   </div>
 <div class="background_image">
<img src = "image/${reservationConfirm[0].poster}"width="100%" height="600" style="opacity:0.20;">
 <h3 class="reservation_ticket">
  ${reservationConfirm[0].title}<br>
 
  상영관:${reservationConfirm[0].theater} 관<br>
  관람일시:${reservationConfirm[0].date}
  ${reservationConfirm[0].time}<br>
 
  상영시간:${reservationConfirm[0].running_time}<br>
 관람좌석:<c:forEach var = "reservationConfirms" items = "${reservationConfirm}">
  ${reservationConfirms.seat_name}         
</c:forEach>
</h3>
</div>
  </div>
 
 <%--   <h2>Reservation complete</h2>
      <table border = "2">
         <tr>
            <th>title</th>
            <th>date</th>
            <th>time</th>
            <th>running_time</th>
            <th>theater</th>
            <th>seat_name</th>
            
         </tr>

   		<tr>
         	<td>${reservationConfirm[0].title}</td>
            <td>${reservationConfirm[0].date}</td>
            <td>${reservationConfirm[0].time}</td>
            <td>${reservationConfirm[0].running_time}</td>
            <td>${reservationConfirm[0].theater}</td>
      		<td> <c:forEach var = "reservationConfirms" items = "${reservationConfirm}">
           		${reservationConfirms.seat_name}         
	      </c:forEach></td>
      	</tr>
	</table> --%>
   

    </div>
</body>
</html>