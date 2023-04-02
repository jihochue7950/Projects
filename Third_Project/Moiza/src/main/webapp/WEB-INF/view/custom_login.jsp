<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
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
		<div class="row">

			<div class="colm-form">
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<hr>
				<h1>
					<b>LogIn</b>
				</h1>
				<div class="form-container">
					<c:if test="${param.error==''}">
						<div style="color: red;">ID와 PASSWORD를 확인 해주세요</div>
					</c:if>

					<form:form action="authenticateTheUser" method="POST"
						class="was-validated">
						<div class="form-group">
							<input type="text" name="username" placeholder="userID" required>
							<div class="valid-feedback"></div>
							<div class="invalid-feedback">ID를 입력해주세요</div>
						</div>

						<div class="form-group">
							<input type="password" name="password" placeholder="Password"
								required>
							<div class="valid-feedback"></div>
							<div class="invalid-feedback">PASSWORD를 입력해주세요</div>
						</div>
						<button type="submit" value="Login" class="btn-login">로그인하기</button>
						<a href="${pageContext.request.contextPath}/join">Create new
							Account</a>
						<a href="${pageContext.request.contextPath}/">홈으로 가기</a>
					</form:form>
				</div>
			</div>
		</div>
	</main>

</body>
</html>