<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "com.web.jdbc.*" %>
<%@ page import= "java.util.UUID" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Join_Member</title>
   <meta charset="utf-8">
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
          if$(${check_id} ==true){
             alert("아이디가 존재합니다");
          }
          
          
          });             
       });      
       
    </script>
    
    
    <script>
       $(document).ready(function(){   
      
          if($("#pwd").val() != $("#pwd_check").val()){
             alert("비밀번호가 맞지 않습니다.")
             return false;
             }
          });             
       });
    
    </script>
    
     <script>
       $(document).ready(function(){
         $("#checkid").click(function(){         
            var ii = $("#id_input").val();
               if(ii == null || ii == ""){
                  alert("is empty");               
               }else{
                  alert("Failed to enter ID");
               }
            $("#id_input").focus();
         });
      });
   </script>  
 
   
</head>
<body>  
<div class="container">
   <div class="text-center">
		<a href="hcj_servlet?command=list" style = "text-decoration-line: none">
			<img src ="image/movie.png" width="80px"><h2>HCJ cinema</h2>
		</a>
	</div>

         <div>
         <form action = "hcj_servlet" method = "POST" class="was-validated">
              
         id : <input id= "idinput" type="text"class="form-control" name = "id"  placeholder = "only English" required >
            <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
           <input type="hidden" name="command" value="checkId" />        
          <input class="btn btn-info" id="check_id" type = "submit" value = "checkId"><br><br>
     
      </form>
      </div>
   
         <%--    <c:set var = "id" value = <%request.getAttribute("id"); %>/> --%>
      <form action = "hcj_servlet" method = "POST" class="was-validated" >
      <input type="hidden" name="command" value="JOIN" />
      <%-- <input type ="hidden" name="id" value =  "<%request.getAttribute("id_");%>"/> --%>
      <input type ="hidden" name="id_check" value = "${id_}"/>

      <%-- id : ${id_} <br><br> --%>
     
       
      <!-- id : <input type = "text" name = "id" id = "id_input" placeholder = "only English" > -->
      
      <!-- <input type = "button" id = "checkid" class="btn btn-outline-dark" value= "checkId"> -->
               
<%--       
      <c:url var = "checkLink" value="/hcj_servlet">
            <c:set var="j" value ="${id}"/>
            <c:param name = "command" value = "JOIN"/>
            <c:param name = "id" value = "${j}"/>
      </c:url>
      <a href = "${checkLink}" class="btn btn-outline-warning">Update</a> 
      
      <input type="button" onclick="checkId()" value="중복 확인">
      
      
       <a href="#" onclick="location.href/hcj_servlets">꾸울팁</a>
      <br><br>
                --%>
         
      <hr>
      id : ${id_ }
     <br><br>
      password : <input type = "password" name = "password" id= "pwd" placeholder = "only Number" required>
      <br><br>
      password(check) : <input type = "password" id = "pwd_check" placeholder = "only Number" required>
     <br><br>
      
      name : <input type = "text" name = "name" placeholder = "only English" required >
     <br><br>
       
      birth : <input type = "text" name = "birth" placeholder = "ex)xxxx-xx-xx" required>
      <br><br>
       
      phone_num : <input type = "text" name = "phone_num" placeholder = "ex)xxx-xxxx-xxxx" required>
     <br><br>   
   
      <% String str = UUID.randomUUID().toString().split("-")[1]; %>
      <% session.setAttribute("auth", str); %>
      <input class="btn btn-info" type ="submit" id = "join" value = "join">
 
   </form>
</div>
   </body>
</html>