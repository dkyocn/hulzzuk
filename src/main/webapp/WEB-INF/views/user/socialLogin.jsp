<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

button.submitId{
	width: 440px;
	height: 43px;
	background-color: lightgray;
	border: 1px solid #585858;
	cursor: pointer;
	font-size: 16px;
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 10px;
	padding: 0 20px;
}

div.loginBtn {
	display: flex;
	flex-direction: column;
	align-items: center;  
	gap: 15px;            
	margin-top: 30px;
}

/* 카카오 버튼 */
#kakaoLogin {
	background-color: #FAE100;
	color: #3B1E1E;
	border: 1px solid #E0C300;
}

/* 네이버 버튼 */
#naverLogin {
	background-color: #00BF18;
	color: white;
	border: 1px solid #009B14;
}

/* 이메일 버튼 */
#emailLogin {
	background-color: white;
	color: black;
	border: 1px solid #585858;
}

/* 로고 이미지 사이즈 조절 */
button.submitId img {
	height: 20px;
}
</style>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", async function () {
	console.log("아무거나 나와랏 : ${requestScope.naverUrl}");
});
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="loginLogo">
<br><br><br><br>
<a href="${pageContext.request.contextPath}/"> <img
	src="${pageContext.request.contextPath}/resources/images/logo2.png"
		alt="logo" />
	</a>
</div>
<br>

<div class="loginBtn">
<!-- 카카오 로그인 -->
<button type="button" onclick="location.href='${kakaoUrl}'" class="submitId" id="kakaoLogin">
	<img src="/hulzzuk/resources/images/kakao_logo.png" alt="카카오 로고">
	카카오로 로그인
</button>
<%-- <a href="${kakaoUrl}"><img src="/hulzzuk/resources/images/kakao_logo.png" alt="카카오 로고"></a> --%>

<!-- 네이버 로그인 -->
<button type="button" onclick="location.href='${naverUrl}'" class="submitId" id="naverLogin">
	<img src="/hulzzuk/resources/images/naver_logo.png" alt="네이버 로고">
	네이버로 로그인
</button>

<!-- 이메일 로그인 -->
<button type="button" class="submitId" id="emailLogin" onclick="location.href='login.do'">
	이메일로 로그인
</button>
</div>

</body>
</html>