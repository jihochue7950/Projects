<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>GroupRegistry</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- 제이쿼리 모달-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<jsp:useBean id="now" class="java.util.Date" />
</head>

<style>
.active {
	left: 350px;
	color: aqua;
}
/* Remove the navbar's default rounded borders and increase the bottom margin */
.navbar {
	margin-bottom: 50px;
	border-radius: 0;
	background-color: #f0f2f5;
	border-color: #f0f2f5;
}

/* Remove the jumbotron's default bottom margin */
.jumbotron {
	margin-bottom: 0;
	background-color: #f0f2f5;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #f0f2f5;
	padding: 25px;
}

body {
	background-color: #f0f2f5;
}
</style>
<script>
	var malls = false;

	function update_selected() {
		$("#middleSelect").val(0);
		$("#middleSelect").find("option[value!=0]").detach();

		$("#middleSelect").append(malls.filter(".main" + $(this).val()));
	}

	$(function() {
		malls = $("#middleSelect").find("option[value!=0]");
		malls.detach();

		$("#mainSelect").change(update_selected);
		$("#mainSelect").trigger("change");
	});
	

	$(document).ready(function(){
		$("#img_select").click(function() {
			 $('#img_select').modal('show');
		});

		$('#confirm_id').click(function() {

			//부모창의 id가 id인 input 태그에 'dragon' 이라는 문자열 저장하기
			$('#id', opener.document).val('dragon');

			//아래와 같이 명시하는 것도 가능함
			//$(opener.document).find('#id').val('dragon');

			self.close();
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
		<h2>환영합니다~</h2>
		<p>
			밴드 개설을 원하시면
			<code>형식에 알맞게</code>
			항목을 채워주세요~
		</p>
		<hr style="border: outset 10px;">
		<form:form action="groupCreation" modelAttribute="mgroup"
			class="was-validated" method="GET">

			<label>원하시는 이미지를 골라주세요~</label>
			<form:hidden path="mgroup_img" value = "${theImg.img_index}"/>
			<form:hidden path="mgroup_img_url" value = "${theImg.img_url}"/>
			<form:hidden path="mgroup_local_name" value = "${mgroup_local_name}"/>
			<%-- <div class="form-group">
				<form:select class="form-control" path="mgroup_img">
					<option value="0" selected="selected">이미지를 선택해주세요</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</form:select>
			</div> --%>
			<div class="form-group">
				<a href="${pageContext.request.contextPath}/select_img">
					<c:choose>
					<c:when test="${theImg.img_index == null}">
							<img src="${pageContext.request.contextPath}/img/select_img.png"
								class="rounded" style="border: 2px Dashed black" alt=" 이미지
								선택하기" width="200" height="200" />
						</c:when>
					<c:otherwise>
						<img src="${theImg.img_url}" class="rounded" alt="${theImg.img_index}" width="200" height="200"/>			
					</c:otherwise>
					</c:choose>
				</a>	
			</div>	
	
			<!-- <a href="#imgmodal" id = "img_select">이미지 선택하기(모달x)</a>
				<div id="imgmodal" class="modal">
					<p>모임 이미지를 선택하세요</p>
					<a href="" rel="modal:close">닫기</a>
					<input id = "selectedimg">
					<button id = "button">선택</button>
				</div>
			<br> -->
			
			<label for="usr">원하시는 밴드 이름 적어주세요~</label>
			<div class="form-group">
				<form:input path="mgroup_title" class="form-control" id="uname"
					placeholder="밴드 이름" />
				<div class="valid-feedback"></div>
				<div class="invalid-feedback">Please fill out this field.</div>
			</div>
			
			<label for="comment">간단한 모임 소개 ~</label>
			<div class="form-group">
				<form:textarea class="form-control" rows="5" id="comment"
					path="mgroup_introduce"></form:textarea>
			</div>
			
			<label>원하시는 분류를 골라주세요~</label>
			<div class="form-group">
				<form:select id="mainSelect" class="form-control"
					path="mgroup_maincategory">
					<option value="0" selected="selected">대분류를 선택해주세요</option>
					<option value="취미">취미</option>
					<option value="스포츠레저">스포츠/레저</option>
					<option value="어학외국어">어학/외국어</option>
					<option value="문화예술">문화/예술</option>
					<option value="여행캠핑">여행/캠핑</option>
					<option value="음악">음악</option>
					<option value="건강다이어트">건강/다이어트</option>
					<option value="친목모임">친목/모임</option>
					<option value="맛집요리">맛집/요리</option>
					<option value="인문과학">인문/과학</option>
					<option value="팬클럽">팬클럽</option>
					<option value="게임">게임</option>
					<option value="만화애니메이션">만화/애니메이션</option>
					<option value="반려동물">반려/동물</option>
					<option value="교육공부">교육/공부</option>
					<option value="종교봉사">종교/봉사</option>
					<option value="자연귀농">자연/귀농</option>
				</form:select>
			</div>
			<label>원하시는 소분류를 골라주세요~</label>
			<div class="form-group">
				<form:select id="middleSelect" class="form-control"
					path="mgroup_middlecategory">
					<option value="0">소분류를 선택 해주세요</option>
					<option class="main취미">독서</option>
					<option class="main취미">글쓰기</option>
					<option class="main취미">DIY/공예</option>
					<option class="main취미">가죽/목공</option>
					<option class="main취미">드론/액션캠/RC</option>
					<option class="main취미">바둑</option>
					<option class="main취미">기타</option>
					<option class="main스포츠레저">러닝/걷기</option>
					<option class="main스포츠레저">자전거</option>
					<option class="main스포츠레저">동산/산악</option>
					<option class="main스포츠레저">테니스</option>
					<option class="main스포츠레저">배드민턴</option>
					<option class="main스포츠레저">골프</option>
					<option class="main스포츠레저">볼링</option>
					<option class="main스포츠레저">탁구</option>
					<option class="main스포츠레저">야구</option>
					<option class="main스포츠레저">축구/풋살</option>
					<option class="main스포츠레저">농구</option>
					<option class="main스포츠레저">배구</option>
					<option class="main스포츠레저">족구</option>
					<option class="main스포츠레저">볼링</option>
					<option class="main스포츠레저">당구</option>
					<option class="main스포츠레저">바이크/스쿠터</option>
					<option class="main스포츠레저">수영/다이빙</option>
					<option class="main스포츠레저">스노우보드/스키</option>
					<option class="main스포츠레저">서핑/웨이크보드</option>
					<option class="main스포츠레저">낚시</option>
					<option class="main스포츠레저">자동차</option>
					<option class="main스포츠레저">기타</option>
					<option class="main어학외국어">영어</option>
					<option class="main어학외국어">일본어</option>
					<option class="main어학외국어">중국어</option>
					<option class="main어학외국어">외국어</option>
					<option class="main어학외국어">스페인어/포르투갈어</option>
					<option class="main문화예술">그림/일러스트</option>
					<option class="main문화예술">자동차</option>
					<option class="main문화예술">디자인</option>
					<option class="main문화예술">캘리그라피/서예</option>
					<option class="main문화예술">사진</option>
					<option class="main문화예술">댄스/무용</option>
					<option class="main문화예술">공연/뮤지컬</option>
					<option class="main문화예술">영화</option>
					<option class="main문화예술">미술/전시</option>
					<option class="main여행캠핑">국내여행</option>
					<option class="main여행캠핑">해외여행</option>
					<option class="main여행캠핑">캠핑/백패킹</option>
					<option class="main음악">노래</option>
					<option class="main음악">악기</option>
					<option class="main음악">음악</option>
					<option class="main건강다이어트">피트니스</option>
					<option class="main건강다이어트">요가/필라테스</option>
					<option class="main친목모임">침목/모임</option>
					<option class="main친목모임">육아/맘 모임</option>
					<option class="main맛집요리">맛집</option>
					<option class="main맛집요리">요리/레시피</option>
					<option class="main인문과학">역사</option>
					<option class="main인문과학">심리학</option>
					<option class="main인문과학">인문/철학</option>
					<option class="main팬클럽">프로야구</option>
					<option class="main팬클럽">팬클럽</option>
					<option class="main게임">게임</option>
					<option class="main만화애니메이션">만화/애니메이션</option>
					<option class="main반려동물">반려동물/동물</option>
					<option class="main교육공부">IT/재테크</option>
					<option class="main종교봉사">봉사/자선</option>
					<option class="main자연귀농">꽃/식물</option>
				</form:select>
			</div>
			<label>지역을 골라주세요~</label>
			<div class="form-group">
		<%-- 		<form:select path="mgroup_local_name" class="form-control" id="sel1"
					placeholder="선택하세요">
						<!-- <option value="">지역을 선택해 주세요</option> -->
						<option value=0>지역제한 없음</option>
					<c:forEach var="myLocal" items="${theLocal}">
						<option value="${myLocal.local_name}">${myLocal.local_name}</option>

					</c:forEach>						
						
				</form:select> --%>
				<form:select id="mainSelect" class="form-control"
					path="mgroup_local_name">
					<option value="0" selected="selected">지역을 선택하세요</option>
					<option value="서울">서울</option>
					<option value="경기">경기</option>
					<option value="인천">인천</option>
					<option value="강원">강원</option>
					<option value="충청남도">충청남도</option>
					<option value="대전">대전</option>
					<option value="충청북도">충청북도</option>
					<option value="세종">세종</option>
					<option value="부산">부산</option>
					<option value="울산">울산</option>
					<option value="대구">대구</option>
					<option value="경상북도">경상북도</option>
					<option value="경상남도">경상남도</option>
					<option value="광주">광주</option>
					<option value="전라북도">전라북도</option>
					<option value="제주">제주</option>
					
				</form:select>
			</div>

			<label>최소 나이제한 설정해 주세요~</label>
			<div class="form-group">
				<form:select path="mgroup_minage" class="form-control" id="sel1"
					placeholder="선택하세요">
					<fmt:formatDate value="${now}" pattern="yyyy" var="nowyear" />
						<!-- <option value="">나이를 선택해 주세요</option> -->
						<option value=0>제한없음</option>
					<c:forEach var="i" begin="1" end="100">
						<option value="${i}">${nowyear-i+1}년생</option>  
					</c:forEach>
				</form:select>
			</div>

			<label>최대 나이제한 설정해 주세요~</label>
			<div class="form-group">
				<form:select path="mgroup_maxage" class="form-control" id="sel1"
					placeholder="선택하세요">
						<!-- <option value="">나이를 선택해 주세요</option> -->
						<option value=0>제한없음</option>
					<c:forEach var="i" begin="1" end="100">
						<option value="${i}">${nowyear-i+1}년생</option>  
					</c:forEach>
				</form:select>
			</div>

			<label>성별을 제한해 주세요~</label>
			<br>
			<div class="form-group">
				<form:select path="mgroup_gender" class="form-control" id="sel1"
					placeholder="선택하세요">
					<!-- <option value="">성별을 제한해 주세요</option> -->
					<option value="none">상관없음</option>
					<option value="male">남성만~</option>
					<option value="female">여성만~</option>
				</form:select>
			</div>

			<label>인원을 제한해 주세요~</label>
			<br>
			<div class="form-group">
				<form:select path="mgroup_limit" class="form-control" id="sel1"
					placeholder="선택하세요">
						<!-- <option value="">인원을 제한해 주세요</option> -->
					<c:forEach var="i" begin="3" end="30">
						<option value="${i}">${i}명</option>  
					</c:forEach>
				</form:select>	
			</div>
			<button type="submit" class="btn btn-primary">등록!</button>
		</form:form>
	</div>

</body>
</html>