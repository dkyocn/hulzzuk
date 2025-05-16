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
<meta http-equiv="refresh" content="0; url=https://nid.naver.com/nidlogin.logout">
   <script>
       // 네이버 로그아웃 후 우리 서버로 이동
       setTimeout(function () {
           window.location.href = '<c:url value="/main.do" />';
       }, 1000);  // 1초 후 메인으로 이동
   </script>
</head>
<body>
 로그아웃 중입니다...
</body>
</html>