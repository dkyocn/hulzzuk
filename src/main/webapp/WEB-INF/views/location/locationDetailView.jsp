<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationDetailView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<div class="location-container">
    <div class="title-section">
        <h2>${location.placeName}</h2>
       <%--  <div class="rating-likes">
            <span class="star">★ ${location.starAvg}</span>
            <span class="like">♥ ${location.loveCount}</span>
        </div> --%>
    </div>

 

    <div class="info-icons">
        <span>찜하기</span>
        <span>일정추가</span>
        <span>리뷰</span>
        <span>공유하기</span>
    </div>

    <div class="basic-info">
        <h3>기본 정보</h3>
       <%--  <div class="info-content">
            <img src="${location.subImage}" alt="서브 이미지" /> --%>
            <div class="text-info">
                <p>주소: ${location.roadAddressName}</p>
                <p>전화번호: ${location.phone}</p>
                <button class="reserve-btn">예약하기</button>
            </div>
        </div>
    </div>

    <%-- <div class="review-section">
        <h3>리뷰 (${location.reviewList.size()})</h3>
        <c:forEach var="review" items="${location.reviewList}">
            <div class="review-item">
                <p class="writer">${review.writer} 작성일: ${review.date}</p>
                <p class="stars">평점: ${review.star}</p>
                <p class="content">${review.content}</p>
                <div class="review-images">
                    <c:forEach var="img" items="${review.images}">
                        <img src="${img}" alt="리뷰 이미지" />
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
        <button class="more-btn">더보기</button>
    </div> --%>

    <%-- <div class="log-section">
        <h3>로그 (${location.logList.size()})</h3>
        <div class="log-slider">
            <c:forEach var="log" items="${location.logList}">
                <div class="log-item">
                    <img src="${log.image}" alt="로그 이미지" />
                    <p>${log.title}</p>
                </div>
            </c:forEach>
        </div>
        <button class="more-btn">더보기</button>
    </div>
</div> --%>


</body>
</html>