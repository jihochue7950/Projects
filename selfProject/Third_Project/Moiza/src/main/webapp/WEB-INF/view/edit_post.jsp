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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Writing Post</title>
<link rel="stylesheet" href="css/style.css">
<jsp:useBean id="now" class="java.util.Date" />
</head>
<body>
	<main>
		<div class="row">
			<div class="colm-logo">

			 <div class="container">
      <div class="jumbotron">
         <div class="container text-center">
         			<a href="${pageContext.request.contextPath}/">
				<img src="${pageContext.request.contextPath}/img/moiza_logo.jpg" />
				</a>
            <h1>Moiza</h1>          
         </div>
      </div>
   </div>
			</div>
			<div class="colm-form">
				<div class="form-container">

					<form:form action="save_edit_post" modelAttribute="editedPost" method="GET">
						<form:hidden path="post_index"/>
						<form:hidden path="post_usergroup_index" />
						<form:textarea path="post_maintext" rows="5" class="form-control" placeholder="글을 작성하세요" />
						<fmt:formatDate value="${now}" pattern="yyyy년 MM월 dd일" var="nowdate" />
						<form:hidden path="post_date" value="${nowdate}" />
						<fmt:formatDate value="${now}" pattern="HH:mm" var="nowtime" />
						<form:hidden path="post_time" value="${nowtime}" />
						<form:hidden path="post_like" value="0"/>
						<form:hidden path="post_view" value="0"/>
						<button type="submit" value="Save" class="btn-login">글수정하기</button>
					</form:form>

				</div>
			</div>
		</div>
	</main>
	<a href="${pageContext.request.contextPath}/">Back to Home Page</a>
</body>
</html>
