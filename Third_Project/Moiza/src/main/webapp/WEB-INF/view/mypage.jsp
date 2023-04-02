<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<style>
    footer {
      background-color:#f0f2f5;
      padding: 25px;
    }
    body{
      background-color: #f0f2f5;
    }
    .jumbotron{
      background-color: #f0f2f5;
    }
    #a{
       font-size :50px;
       font-family : "Arial";
    }
    #b{
       font-size :50px;
    }
    .nav-item{
       font-size :30px;
    }

</style>
<body>


   <div class="container">
      <div class="jumbotron">
         <div class="container text-center">
         			<a href="${pageContext.request.contextPath}/">
				<img src="${pageContext.request.contextPath}/img/moiza_logo.jpg" />
				</a>
                     
         </div>
      </div>
   </div>

<div class="container">
 

  
  <p class="text-center" id ="a">마이페이지</p>
  <ul class="nav justify-content-center">
   <li class="nav-item">
     <c:url value="/modification" var="modification" >
      <c:param name ="userId" value="${userId}"/>   
     </c:url>             
      <a class="nav-link" href="${modification}">개인정보수정</a>
      <input type="hidden" name="userId" value="${userId}">
    </li>
 
    <li class="nav-item">
    <a class="nav-link"
   href="${pageContext.request.contextPath}/controlmygroup"> 내모임 관리하기
   </a></li>
  </ul>
    
</div>

</body>
</html>