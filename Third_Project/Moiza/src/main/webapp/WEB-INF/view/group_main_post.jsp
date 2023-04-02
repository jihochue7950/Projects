<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 부트스트랩 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
   

<title>Group_main</title>
<style type="text/css">
header {
   border: 2px solid #772181;
   border-radius: 15px;
}

#container {
   display: flex;
   flex-flow: row nowrap;
   justify-content: center;
   align-items: stretch;
   grid-template-columns: 20% 50% 30%;
   grid-template-rows: 200px 50px;
}

aside {
   flex-basis: 200px;
   text-align: center;
}

#mgroupMainImg {
   width: 200px;
   height: 200px;
   opacity: 0.9;
}

section {
   border: 2px solid #ccd0d5;
   border-radius: 25px;
   word-wrap: break-word;
   flex-basis: 100px;
   flex-grow: 1;
}

bside {
   flex-basis: 200px;
}
</style>

<script>
      $(document).ready(function(){
    	  $("#disbandGroup").click(function(){
	         if (!confirm("모임을 해산합니다.\n삭제 후에는 복구가 불가능합니다.")) {
	            return false;
	        } else {
	            alert("모임을해산합니다.");
	        }
    	 });	 
      });
</script>

</head>
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
      <div id="container">

         <aside>
            <div><img id="mgroupMainImg"
               src="${mgroup.mgroup_img_url}"/> </div>
             <h2>${mgroup.mgroup_title}</h2>
            <h4>${mgroup.mgroup_introduce}</h4>
            <h5>테마 : ${mgroup.mgroup_maincategory}/${mgroup.mgroup_middlecategory}</h5>
            <h5>지역 : ${mgroup.mgroup_local_name}</h5>
            <h5>인원수 :${count} /${mgroup.mgroup_limit}</h5>
            <h5>회원등급 ${theUsergroupRole}</h5>
            <c:if test="${theUsergroupRole eq 'guest'}">   
            <c:url value = "/joingroup?mgroupIndex=${mgroup.mgroup_index}" var ="joingroup"/>
               <a href = "${joingroup}" style = "text-decoration-line: none"><b>가입하기</b></a>
            </c:if>
         </aside>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;



         <section>

            <c:forEach var="tempPost" items="${post}">
               <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);">
               <div>작성자 : ${tempPost.post_usergroup_index} </div>
               <div>${tempPost.post_date} ${tempPost.post_time}</div>
                      
               <div style="font-size:35px">${tempPost.post_maintext}</div>
               <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);">
               <c:url value = "/like" var = "like">
               <c:param name ="post_index" value= "${tempPost.post_index}"/>
               <c:param name ="mgroupIndex" value= "${mgroup.mgroup_index}"/>
               </c:url>
               <a href ="${like}">&#x1F496; ${tempPost.post_like}</a>
                            
                           
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           
               
               <c:url value="/deletePost" var="delete_post">
               <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
               <c:param name ="post_index" value= "${tempPost.post_index}"/>
               </c:url>
               <a href="${delete_post}" style="text-decoration-line: none; float:right;">글삭제  &nbsp;&nbsp;&nbsp;&nbsp;</a>
                            
				<div style="float:right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
				
               <c:url value="/edit_post" var="edit_post">
               <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
               <c:param name ="post_index" value= "${tempPost.post_index}"/>
               <c:param name ="post_usergroup_index" value= "${tempPost.post_usergroup_index}"/>
               <c:param name ="post_maintext" value= "${tempPost.post_maintext}"/>
          		 </c:url>
                <a href="${edit_post}" style="text-decoration-line: none; float:right;" >글수정</a>

                           
                          
                </div><br>
              </div>   
            </c:forEach>
            
         </section>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

         <bside>
            <security:authorize access="isAuthenticated()">
               <p>안녕하세요. <security:authentication property="principal.username"/>님.</p>
            </security:authorize>
            <ul>
               <li><a href="${pageContext.request.contextPath}/logout"> Logout</a></li>
               <li><a href="${pageContext.request.contextPath}/Mypage"> My Page</a></li>
               <c:if test="${theUsergroupRole eq 'admin'}">
                  <li><c:url value="/ViewGroupMemberSetting" var="managing">
                     <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
                  </c:url> 
                  <a href="${managing}" style="text-decoration-line: none">모임 및 회원 관리하기</a></li>
                  <li><c:url value="/writing_post" var="writingPost">
                     <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
                  </c:url> 
                  <a href="${writingPost}" style="text-decoration-line: none">글쓰기</a></li>
                  
                  <c:url value="/DeleteGroup" var="DeleteGroup">
                     <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
                     <c:param name="count" value="${count}" />
                  </c:url>  
                  <li><a href="${DeleteGroup}" id ="disbandGroup" style="text-decoration-line: none">모임해제</a></li>
                  <div>
                     <%String errorDelete = (String)session.getAttribute("errorDelete");%>
                     <p style = "color: red;">${errorDelete}</p>
                  </div>
                   <% session.removeAttribute("errorDelete"); %>
                   
               </c:if>
               <c:if test="${theUsergroupRole eq 'normal'}">
                  <li><c:url value="/ViewGroupMemberSetting" var="managing">
                     <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
                  </c:url> 
                  <a href="${managing}" style="text-decoration-line: none">모임 관리하기</a></li>
                  <li><c:url value="/writing_post" var="writingPost">
                     <c:param name="mgroupIndex" value="${mgroup.mgroup_index}" />
                  </c:url>
                  <a href="${writingPost}" style="text-decoration-line: none">글쓰기</a></li>
               </c:if>
            </ul>
         </bside>
      </div>
   </div>
</body>
</html>