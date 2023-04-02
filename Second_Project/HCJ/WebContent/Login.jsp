<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
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
<div class="container">
	<div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>

<form action="hcj_servlet" method="post" class="was-validated">
<input type="hidden" name="command" value="Login" />
 <div class="form-group">
 		id: <input id= "idinput" type="text" class="form-control" name="id" required>
           <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
      </div>
      <div class="form-group">
         password: <input id= "idinput" type="password" class="form-control" name="password" required>
           <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
          </div>
     
         <input class="btn btn-info" type="submit" value="로그인">
         <input class="btn btn-outline-info"type="reset" value="지우기">
    </form>  

</div>
</body>