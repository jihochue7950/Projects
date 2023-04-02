<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
   href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<title>Login or Sign up</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
   <jsp:useBean id="now" class="java.util.Date" />

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
                  <b>Sign UP</b>
               </h1>
               <div class="form-container">
                  <c:if test="${error!=''}">
                     <div style="color: red;">${error}</div>
                  </c:if>
                  <form:form action="saveUser" method="GET" class="was-validated">
                     <div class="form-group">
                        <input type="text" name="username" placeholder="userID" required>
                        <div class="valid-feedback"></div>
                        <div class="invalid-feedback">ID를 입력해주세요</div>
                 <c:if test="${errorId!=''}">
                     <div style="color: red;">${errorId}</div>
                  </c:if>
                     </div>
                     <div class="form-group">
                     <input type="password" id="password" name="password" placeholder="Password" required>
                        <div class="valid-feedback"></div>
                     <div class="invalid-feedback">PASSWORD를 입력해주세요</div>
                     </div>

                     <input type="text" name="user_name" placeholder="Name">
                     
                     <input type="text" name="user_phone"
                        placeholder="Phone number (don't exception -)">
                     <input type="text" name="user_birth"
                        placeholder="Birth (input six numbers)">
                     <!-- <input type="text" name="user_gender" placeholder="Gender"> -->
                     <div class="form-group">
                     <select name="user_gender">
                         <option value="">--Please choose an Gender--</option>
                         <option value="male">남성</option>
                         <option value="female">여성</option>
                     </select>
                     </div>
                     <fmt:formatDate value="${now}" pattern="yyyyMMdd" var="now" />
                     <input type="hidden" name="user_joinday" value="${now}">
                     <input type="hidden" name="user_out" value="0">
                     <input type="hidden" name="enabled" value="1">
                     <input type="hidden" name="authority" value="ROLE_EMPLOYEE">

                     <button type="submit" value="Save" class="btn-login">가입하기</button>
                     <a href="${pageContext.request.contextPath}/">홈으로 가기</a>
                  </form:form>
               </div>
            </div>
         </div>
      </div>

   </main>


</body>
</html>