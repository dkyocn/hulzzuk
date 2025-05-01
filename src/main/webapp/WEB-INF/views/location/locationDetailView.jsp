<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationDetailView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<%-- <div class="container">
    <div class="title-section">
        <h1>${location.placeName}</h1>
        <div class="rating">
            <span>★ 4.8/5</span> <span>❤️ 20</span>
        </div>
    </div>

    <div class="main-image">
         <img src="${pageContext.request.contextPath}/resources/images/logo1.png"  alt="메인 이미지">
    </div>

    <div class="button-group">
        <button>찜하기</button>
        <button>일정추가</button>
        <button onclick="#">리뷰</button>
        <button>공유하기</button>
    </div>

    <div class="basic-info">
	    <div class="title-section-info">
	        <h1>기본 정보</h1>
	    </div>
        <img src="${pageContext.request.contextPath}/resources/images/plan/busan.jpeg"  alt="지도 이미지">
        <div class="info-text">
            <p>주소: ${location.roadAddressName}</p>
            <p>전화번호: ${location.phone}</p>
            <button class="reserve-btn">예약하기</button>
        </div>
    </div>

    <div class="review-section">
    
        <c:import url="/WEB-INF/views/review/reviewListView.jsp" />
       
        <div class="more-button-container">
            <a href="reviewList?locId=${location.locId}" class="more-button">더보기</a>
        </div>
    </div>

</div> --%>

<div class="container">

    <!-- 제목 + 별점 + 찜 수 -->
    <div class="title-section">
        <h1>${location.placeName}</h1>
        <div class="rating">
            ★ 4.8/5 &nbsp;&nbsp; ❤️ 20
        </div>
    </div>

    <!-- 메인 이미지 -->
    <div class="main-image">
        <img src="${pageContext.request.contextPath}/resources/images/logo1.png" alt="메인 이미지">
    </div>

    <!-- 버튼 그룹 -->
    <div class="button-group">
        <button>찜하기</button>
        <button>일정추가</button>
        <button>리뷰</button>
        <button>공유하기</button>
    </div>

    <!-- 기본 정보 + 예약 버튼 -->
    <div class="basic-info">
    <h2 class="section-title">기본 정보</h2>
        <img src="${pageContext.request.contextPath}/resources/images/plan/busan.jpeg" alt="지도 이미지">
        <div class="info-text">
            <p>주소: ${location.roadAddressName}</p>
            <p>전화번호: ${location.phone}</p>
            <button class="reserve-btn">예약하기</button>
        </div>
    </div>

    <!-- 리뷰 영역 -->
    <div class="review-section">
        <h2>리뷰 (15)</h2>

        <div class="review-container">
    <div class="review-header">
        <h2>리뷰</h2>
	<c:import url="/WEB-INF/views/review/reviewListView.jsp" />

        <!-- 더보기 버튼 -->
        <div class="more-button-container">
            <a href="reviewList?locId=${location.locId}" class="more-button">더보기</a>
        </div>
    </div>

    <!-- 로그 영역 -->
    <div class="log-section">
        <h2>로그 (10)</h2>
        <%-- <div class="log-grid">
            <img src="${pageContext.request.contextPath}/resources/images/log1.jpg" alt="로그1">
            <img src="${pageContext.request.contextPath}/resources/images/log2.jpg" alt="로그2">
            <img src="${pageContext.request.contextPath}/resources/images/log3.jpg" alt="로그3">
        </div> --%>

        <div class="more-button-container">
            <a href="logList?locId=${location.locId}" class="more-button">더보기</a>
        </div>
    </div>

</div>
</div>

<hr>


<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>