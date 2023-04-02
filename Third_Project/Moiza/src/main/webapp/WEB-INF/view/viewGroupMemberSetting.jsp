<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>View Group Member</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
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
	
	<c:if test="${theUsergroupRole eq 'admin'}">
	<div class = "container">
	<table border ="2px">
		<p> admin = 모임장 / normal =일반회원 / employee = 가입대기중인 회원</p>
		
		<tr>
			<th>회왼등급</th>
			<th>회원이름</th>
			<th>연락처</th>
			<th>생일</th>
			<th>성별</th>
			<th>로그인한 아이디</th>
			<th>가입일</th>
			<th>가입 승인하기</th>
			<th>가입 거부 & 추방</th>
			<th>모임장 위임하기</th>
		</tr>
	<c:forEach var="viewGroupUserInfo" items="${GroupUserInfo}">

		<tr>
		<td>${viewGroupUserInfo.usergroup_user_role}</td>
		<td>${viewGroupUserInfo.user_name}</td>
		<td>${viewGroupUserInfo.user_phone}</td>
		<td>${viewGroupUserInfo.user_birth}</td>
		<td>${viewGroupUserInfo.user_gender}</td>
		<td>${viewGroupUserInfo.username}</td>	
		<td>${viewGroupUserInfo.user_joinday}</td>
		<td><c:if test="${viewGroupUserInfo.usergroup_user_role eq 'employee'}">
				<c:url value="/ApprovNonMembers" var="YesNonMembers">			
					<c:param name="usergroup_index" value="${viewGroupUserInfo.usergroup_index}"/>
					<c:param name="mgroupIndex" value="${viewGroupUserInfo.usergroup_group_index}"/>
				</c:url>
				<a href="${YesNonMembers}" style="text-decoration-line: none">가입 승인하기</a>
			</c:if></td>
		<td><c:if test="${viewGroupUserInfo.usergroup_user_role eq 'employee'}">
				<c:url value="/RejectNonMembers" var="NoNonMembers">						
					<c:param name="usergroup_index" value="${viewGroupUserInfo.usergroup_index}" />
					<c:param name="mgroupIndex" value="${viewGroupUserInfo.usergroup_group_index}"/>
				</c:url>
				<a href="${NoNonMembers}" style="text-decoration-line: none">가입 거부하기</a>
			</c:if>
			<c:if test="${viewGroupUserInfo.usergroup_user_role eq 'normal'}">
				<c:url value="/kickout" var="exile">						
					<c:param name="usergroup_index" value="${viewGroupUserInfo.usergroup_index}" />
					<c:param name="mgroupIndex" value="${viewGroupUserInfo.usergroup_group_index}"/>
				</c:url>
				<a href="${exile}" style="text-decoration-line: none">추방하기</a>
			</c:if></td>
					<td><c:if test="${viewGroupUserInfo.usergroup_user_role eq 'normal'}">
			<c:url value="/HandOverSeats" var="Mandate">			
				<c:param name="usergroup_index" value="${viewGroupUserInfo.usergroup_index}"/>
				<c:param name="mgroupIndex" value="${viewGroupUserInfo.usergroup_group_index}"/>
			</c:url>
			<a href="${Mandate}" style="text-decoration-line: none">모임장 위임하기</a>
		</c:if></td>
		</tr>
	</c:forEach>
	</table>
	</div>
	</c:if>
	<br>
	<c:if test="${theUsergroupRole eq 'admin'}">
	<div class="container">
		모임 해체하기
	</div>
	</c:if>
	
	<c:if test="${theUsergroupRole eq 'normal'}">
	<br>
	<br>
	<br>
	<br>
	<div class="container">
		<c:url value="/goout" var="exile">						
			<c:param name="usergroup_index" value="${GroupMyInfo.usergroup_index}" />
			<c:param name="mgroupIndex" value="${GroupMyInfo.usergroup_group_index}"/>
		</c:url>
		<a href="${exile}" style="text-decoration-line: none">탈퇴하기</a>
	</div>
	</c:if>
	
	
	
	
</body>
</html>