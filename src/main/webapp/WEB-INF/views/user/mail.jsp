<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="fail" value="${requestScope.mailFalse}" />
<c:set var="userId" value="${requestScope.userId}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

<style>
div.loginLogo{
text-align: center;
}

div.loginLogo img{
width: 500px;
}

div#mailForm{
text-align: center;
margin: 0 auto;
}

div#mailForm input.pos{
border: none;
outline: none;
width: 80%; 
height: 100%;
right: 10%;
padding: 0;
margin: 0;
font-size: 16px;
border: none;
outline: none;
box-sizing: border-box; /* 패딩 포함해서 width 100% 유지 */
}

table.mail{
border: 1px solid #585858;
border-collapse: collapse;	/* 테이블 선 겹치기 방지 */
margin: 0 auto;	
}

table.mail th{
width: 150px;
border: 1px solid #585858;
padding: 10px 25px;
text-align: center;
}

table.mail td{
width: 400px;
height: 43px;
border: 1px solid #585858;
padding: 0;
text-align: center;
}

button.mailButton{
width: 50px;
height: 35px;
font-size: 16px;
cursor: pointer;
}

div.errorMsg{
color: red;
font-size: 13px;
margin-top: 10px;
margin-bottom: 10px;
text-align: center;
line-height: 1.5;
}
</style>
<script type="text/javascript">
 function openMailPopUp() {
	 const userId = document.querySelector('input[name="userId"]').value;
	 
	 const url = '${pageContext.request.contextPath}/user/sendMail.do'
			+ '?userId=' + encodeURIComponent(userId)
		 	+ '&width=350&height=300';
	 
    // 팝업 창 열기 (가로 400px, 세로 300px)
    window.open(url, 'mailPopUp', 'width=350,height=250');
} 

 window.onload = function() {
	 var fail = '${fail}';
	 
	 if(fail === 'N'){
		 document.querySelector('input[name="inputCode"]').focus();
	 }
 };
</script>
</head>
<body>
<%
	String userId = (String)session.getAttribute("authUserId");
%>

<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="loginLogo">
	<br><br><br><br>
	<a href="${pageContext.request.contextPath}/"> 
	<img src="${pageContext.request.contextPath}/resources/images/logo2.png" alt="logo" /></a>
</div>
<br>
<!-- 아이디가 있으면 이메일 전송, 아이디가 없으면 회원가입 페이지로
비밀번호 재설정 할 때 입력했던 아이디 값을 파라미터로 같이 넘겨줘야 함 -->
<div id="mailForm">
	<table class="mail">
		<tr><th>아이디(이메일)</th>
			<td>
		<!-- 아이디 -->
		<c:if test="${empty userId}">
			<input type="text" name="userId" class="pos">
		</c:if>
		<c:if test="${!empty userId}">
			<input type="text" name="userId" class="pos" value="<%=userId %>">
		</c:if>
		<button class="mailButton" onclick="openMailPopUp()">전송</button></td></tr>
	</table>
	
	<form action="${pageContext.request.contextPath}/user/verifyCode.do" method="post">
        <table class="mail">		
			<tr><th>인 증 번 호</th>
			<td><input type="password" name="inputCode" class="pos">
                <input type="hidden" name="userId" value="${userId}">
				<button type="submit" class="mailButton">확인</button></td></tr>
		</table>
	</form>

	<!-- 오류 메세지 표시 -->
	<c:if test="${not empty fail and fail eq 'N'}">
		<div class="errorMsg">
			인증번호가 잘못 되었습니다. <br>
			인증번호를 정확히 입력해 주세요.
		</div>
	</c:if>
	<br>
</div>
</body>
</html>














