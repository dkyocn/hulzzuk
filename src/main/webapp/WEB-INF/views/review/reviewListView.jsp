<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/review/reviewListView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>

<body>

<div class="review-container">
    <div class="review-header">
        <button class="write-review-btn" onclick="location.href='#'">리뷰 작성하기</button>
    </div>

    <c:forEach var="review" items="${reviewList}">
        <div class="review-item">
            <div class="review-info">
            	<%-- <span class="review-location"><c:out value="${ }"></c:out></span> --%>
            	<c:if test="${review['userId'] != null}">
    <span>작성자: <c:out value="${userNicks[review['userId']]}" /></span>
</c:if>
<c:if test="${review['userId'] == null}">
    <span>작성자: 알 수 없음</span>
</c:if>
                
<%--                 <span class="review-rating"><c:out value="${review.rating}" /></span> --%>
                <span class="review-date">작성일: <c:out value="${review.createAt}" /></span>
            </div>
            <div class="review-content">
                <p><c:out value="${review.userReviewText}" /></p>
            </div>
        </div>
    </c:forEach>

</div>




</body>
</html>