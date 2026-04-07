<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>MovieList</title>
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
    
    <script>
		$(document).ready(function(){
		  $('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	
	<style>
	#background_image{
		opacity: 0.14;
	}
	
	</style>

</head>


<body><!--  style = "background-color : black"> -->
	<div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>
	
	<%String tempid = (String)session.getAttribute("id");%>
	<div class="container">
   <div style = "text-align: right;">      
   <c:if test="${id == null}">
	   <a href="Login.jsp" class="btn btn-primary">Login</a>   
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
   </div>
	<div id="demo" class="carousel slide" data-ride="carousel" style = "position :relative;">
	
	
	
	  <!-- Indicators -->
	  <ul class="carousel-indicators">
	    <li data-target="#demo" data-slide-to="0" class="active"></li>
	    <li data-target="#demo" data-slide-to="1"></li>
	    <li data-target="#demo" data-slide-to="2"></li>
	    <li data-target="#demo" data-slide-to="3"></li>
	  </ul>
	  
	  <!-- The slideshow -->
	  <div class="carousel-inner">
		  <div style = "text-align: center;"> 
		    <div class="carousel-item active">
		        <a href="hcj_servlet?command=movieTime&movieNum=${movie_list[0].movie_num}">
		               <img src = "image/${movie_list[0].poster}" id = "background_image" width="100%" height="600"/>
		            </a>
		    </div>
		    <div class="carousel-item">
		                 <a href="hcj_servlet?command=movieTime&movieNum=${movie_list[1].movie_num}">
		               <img src = "image/${movie_list[1].poster}" id = "background_image" width="100%" height="600"/>
		            </a>
		    </div>
		    <div class="carousel-item">
		                  <a href="hcj_servlet?command=movieTime&movieNum=${movie_list[2].movie_num}">
		               <img src = "image/${movie_list[2].poster}" id = "background_image" width="100%" height="600"/>
		            </a>
		    </div>
		     <div class="carousel-item">
		                  <a href="hcj_servlet?command=movieTime&movieNum=${movie_list[3].movie_num}">
		               <img src = "image/${movie_list[3].poster}" id = "background_image" width="100%" height="600"/>
		            </a>
		    </div>		  
		  
		  <!-- Left and right controls -->
		  <!-- <a class="carousel-control-prev" href="#demo" data-slide="prev">
		    <span class="carousel-control-prev-icon"></span>
		  </a>
		  <a class="carousel-control-next" href="#demo" data-slide="next">
		    <span class="carousel-control-next-icon"></span>
		  </a> -->
		  </div>
	  </div>
	</div>
   
	<div class ="container">
		<div class="row" style = "flex-wrap: nowrap; position: absolute; top:250px; border: 0px solid #999;">  		       
			<c:forEach var = "arrMovie" items = "${movie_list}">
				<div class="col-3"><!--  style = "flex-direction : column;"> -->
					<!-- &nbsp;&nbsp;&nbsp; -->
					<a href="hcj_servlet?command=movieTime&movieNum=${arrMovie.movie_num}" id ="movie_${arrMovie.movie_num}" data-toggle="tooltip" title="${arrMovie.title}">
					   <img src = "image/${arrMovie.poster}" id = "poster" width="200px"/>
					</a>
					<br>
					<div class="popupModalVideo">
					    <a data-video="${arrMovie.trailer_url}" class="btn btn-info">예고편</a>
					</div>
				
				</div>					
			</c:forEach>
		</div>
		<div class="video_modal_popup" style = "border: 1px solid black; position: absolute; top:610px;">
			<div class="video_modal_popup-closer"></div>
		</div>	
	</div>
	

	
	<script>
	$(".popupModalVideo a").click(function() {
	    $(".video_modal_popup").addClass("reveal"),
	    $(".video_modal_popup .video-wrapper").remove(),
	    $(".video_modal_popup").append("<div class='video-wrapper'><iframe width='560' height='315' src='https://youtube.com/embed/" + $(this).data("video") + "?rel=0&playsinline=1&autoplay=1' allow='autoplay; encrypted-media' allowfullscreen></iframe></div>")
	}),
	$(".video_modal_popup-closer").click(function() {
	    $(".video_modal_popup .video-wrapper").remove(),
	    $(".video_modal_popup").removeClass("reveal")
	});
	</script> 
</body>
</html>