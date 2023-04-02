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

<script>
      $(document).ready(function(){
    	  $("#leaveMoiza").click(function(){
	         if (!confirm("모이자사이트에서 탈퇴합니다.")) {
	            return false;
	        } else {
	        	 if (!confirm("삭제 후에는 복구가 불가능합니다.\n정말로 탈퇴하시겟습니까?")) {
	 	            return false;
	 	        } else {
	 	            alert("탈퇴가 완료되었습니다.");
	 	        }
	        }
    	 });	 
      });
</script>
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
  <h2>회원정보를 형식에 맞게 변경하여 주세요 </h2>
  <form:form action="userModification" method="GET">
    <div class="form-group">
      <label for="email">user_ID:</label>
      <input type = "text" name = "username" class="form-control" id="username" value="${users.get(0).username}"disabled/>
    </div>
       <div class="form-group">
      <label for="email">password:</label>
      <input type = "password" name = "password" class="form-control" id="user_phone" value="${users.get(0).password}"/>
    </div>  
      <div class="form-group">
      <label for="email">user_Name:</label>
      <input type = "text" name = "user_name" class="form-control" id="user_name" value="${users.get(0).user_name}"/>
    </div> 
    <div class="form-group">
      <label for="email">user_phone:</label>
      <input type = "text" name = "user_phone" class="form-control" id="user_phone" value="${users.get(0).user_phone}"/>
    </div>
     <div class="form-group">
      <label for="email">user_Birth:</label>
      <input type = "text" name = "user_birth" class="form-control" id="user_birth" value="${users.get(0).user_birth}"/>
    </div>
     <div class="form-group">
      <label for="email">user_Gender:</label>
      <input type = "text" name = "user_gender" class="form-control" id="user_gender" value="${users.get(0).user_gender}"disabled/>
    </div>
   
     <input type = "hidden" name = "user_index" value="${users.get(0).user_index}"/>
      
 
    <button type="submit" class="btn btn-primary">Submit</button>
  </form:form>
 
     <c:url value="/withdraw" var="withdraw" >
      <c:param name ="user_index" value="${users.get(0).user_index}"/>   
     </c:url>   
      <a class="nav-link"id = "leaveMoiza" href="${withdraw}">회원탈퇴</a>
 

</div>

</body>
</html>