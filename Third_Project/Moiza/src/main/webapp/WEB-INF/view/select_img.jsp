<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Writing Post</title>
<link rel="stylesheet" href="css/style.css">
<jsp:useBean id="now" class="java.util.Date" />

<style>
#img_div{
	display: flex;
	flex-direction: row;
	flex-wrap: wrap;
	justify-content: center;
}

#img_div_child {
	padding: 10px;
	text-align: center;;
}

</style>

</head>
<body>
	<main>
		 <div class="container">
      <div class="jumbotron">
         <div class="container text-center">
         			<a href="${pageContext.request.contextPath}/">
				<img src="${pageContext.request.contextPath}/img/moiza_logo.jpg" />
				</a>
                     
         </div>
      </div>
   </div>
			${mgroup.mgroup_title}<br>
			<div id = "img_div">
				<c:forEach var="tempImg" items="${theImg}">
					<div id = "img_div_child">
					<a href="${pageContext.request.contextPath}/beforeGroupCreation?img_index=${tempImg.img_index}">
						<img src="${tempImg.img_url}" class="rounded" alt="이미지를 선택하세요" width="200" height="200"/>			
					</a>
					</div>
				</c:forEach>
			</div>
			<a href="${pageContext.request.contextPath}/">Back to Group Registry</a>
		</div>
	</main>

</body>
</html>
