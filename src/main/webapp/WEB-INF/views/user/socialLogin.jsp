<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
	<c:import url="/WEB-INF/views/common/header.jsp" />
	
	<hr>
	
	<!-- 카카오 로그인 창으로 이동 -->
	<div id="kakao_id_login" class="kakao_id_login"
		style="text-align: center">
		<a href="${kakaoUrl}"> <img width="230" height="60"
			src="${ pageContext.servletContext.contextPath }/resources/images/kakao_login.png"
			alt="카카오로그인">
		</a>
	</div>
	<br>

	<!-- 네이버 로그인 화면으로 이동 시키는 URL -->
	<!-- 네이버 로그인 화면에서 ID, PW를 올바르게 입력하면 callback 메소드 실행 요청 -->
	<div id="ndfin" style="text-align: center">
		<a href="${naverUrl}" id='.cp_naver'> <img width="230"
			height="60"
			src="${ pageContext.servletContext.contextPath }/resources/images/naver.png"
			alt="네이버로그인">
		</a>
	</div>
	<br>
	<div id="emailLogin" style="text-align: center">
		<a href="${pageContext.request.contextPath}/user/login.do">이메일로 회원가입</a>
	</div>
</body>
</html>