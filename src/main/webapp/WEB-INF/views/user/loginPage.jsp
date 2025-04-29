<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="fail" value="${requestScope.fail}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<style>
div#loginLogo{
text-align: center;
}

div#loginLogo img{
width: 500px;
}

div#loginForm{
text-align: center;
margin: 0 auto;
}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
	<div id="loginLogo">
		<br><br><br><br>
		<a href="${pageContext.request.contextPath}/"> <img
			src="${pageContext.request.contextPath}/resources/images/logo2.png"
			alt="logo" />
		</a>
	</div>
	<br>
	<div id="loginForm">
		<form action="login.do" method="post">
			<label>아이디: <input type="text" id="userid" name="userId" class="pos"></label> <br>
			<label>암 호 : <input type="password" id="userpwd" name="userPwd" class="pos"></label> <br>
			<input type="submit" value="로그인">
		</form>
	</div>
</body>
</html>