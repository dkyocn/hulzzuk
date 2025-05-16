<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>여행 플랫폼</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/header.css">

</head>

<body>
<nav class="options">
    <div class="lmenu">
        <div class="menu-trigger" onclick="toggleMenu()">
            <span></span>
            <span></span>
            <span></span>
        </div>
        <div class="logo">
            <a href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/resources/images/logo2.png" alt="logo" />
            </a>
        </div>
    </div>

    <div class="rmenu">
        <a href="${pageContext.request.contextPath}/love/moveLove.do">My 찜</a>
        <a href="${pageContext.request.contextPath}/log/page.do?page=1">여행로그</a>
        <a href="${pageContext.servletContext.contextPath}/plan/page.do?page=1">일정</a>
        <c:if test="${empty loginUser }">
      	 	<a href="${pageContext.request.contextPath}/user/loginSelect.do">로그인</a>
        </c:if>
        <c:if test="${!empty loginUser}">
         	<a href="${pageContext.request.contextPath}/user/select.do?userId=${loginUser.userId}">프로필</a>
        </c:if>
    </div>
</nav>

<!-- 사이드 메뉴 -->
<div class="side-menu" id="sideMenu">
    <div class="side-top">
        <select class="chip" id="chip"> 
            <option value="">🌐 한국어</option>
            <option value="eng">🌐 English</option>
        </select>
        <button class="close-btn" onclick="closeMenu()">&times;</button>
    </div>

    <ul class="menu-section">
     <li><a href="${pageContext.servletContext.contextPath}/loc/page.do?locationEnum=ACCO&page=1&sortEnum=LOVEDESC">숙소</a></li>
    <li><a href="${pageContext.servletContext.contextPath}/loc/page.do?locationEnum=REST&page=1&sortEnum=LOVEDESC">맛집</a></li>
    <li><a href="${pageContext.servletContext.contextPath}/loc/page.do?locationEnum=ATTR&page=1&sortEnum=LOVEDESC">즐길거리</a></li>
        <li><a href="${ pageContext.servletContext.contextPath }/log/page.do?page=1">여행로그</a></li>
    </ul>

    <ul class="menu-section">
        <li><a href="${ pageContext.servletContext.contextPath }/notice/page.do?page=1">공지사항</a></li>
        <li><a href="${ pageContext.servletContext.contextPath }/faq/page.do?page=1">FAQ</a></li>
        <li><a href="${ pageContext.servletContext.contextPath }/voc/page.do?vocEnum=ALL&page=1">고객의 소리</a></li>
    </ul>

    <ul class="menu-section">
	    <c:if test="${ !empty sessionScope.loginUser }">
    		<li><a href="${pageContext.request.contextPath}/user/select.do?userId=${loginUser.userId}">마이페이지</a></li>
	         <li class="mymenu"><a href="${ pageContext.servletContext.contextPath }/log/myTripLog.do?">My 여행로그</a></li>
	        <li class="mymenu"><a href="${ pageContext.servletContext.contextPath }/plan/page.do?page=1">My 일정</a></li>
	        <li class="mymenu"><a href="${pageContext.request.contextPath}/love/moveLove.do">My 찜</a></li>
	        
	        <c:url var="myrev" value="review/select.do">
            <c:param name="userId" value="${loginUser.userId}" />
        </c:url>
        <li class="mymenu"><a href="${ pageContext.servletContext.contextPath }/review/select.do?userId=${loginUser.userId}">My 리뷰</a></li>
	    </c:if>
	    <c:if test="${ empty sessionScope.loginUser }">
	    	<li><a href="${pageContext.request.contextPath}/user/loginSelect.do">마이페이지</a></li>
	        <li class="mymenu"><a href="${pageContext.request.contextPath}/user/loginSelect.do">My 여행로그</a></li>
	        <li class="mymenu"><a href="${pageContext.request.contextPath}/user/loginSelect.do">My 일정</a></li>
	        <li class="mymenu"><a href="${pageContext.request.contextPath}/user/loginSelect.do">My 찜</a></li>
	        <li class="mymenu"><a href="${pageContext.request.contextPath}/user/loginSelect.do">My 리뷰</a></li>
   		</c:if>
    </ul>
</div>

<!-- 메뉴 토글 스크립트 -->
<script>
    function toggleMenu() {
        const menu = document.getElementById("sideMenu");
        menu.classList.toggle("open");
    }

    function closeMenu() {
        document.getElementById("sideMenu").classList.remove("open");
    }

    document.addEventListener("click", function(event) {
        const menu = document.getElementById("sideMenu");
        const menuButton = document.querySelector(".menu-trigger");

        if (menuButton.contains(event.target)) {
            return;
        }

        if (!menu.contains(event.target) && menu.classList.contains("open")) {
            menu.classList.remove("open");
        }
    });
</script>

</body>
</html>
