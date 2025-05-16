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
<!-- <script type="text/javascript">
	const raw = document.getElementById("rawText").innerHTML;
	const tempDiv = document.createElement("div");
	tempDiv.innerHTML = raw;
	document.getElementById("cleanText").textContent = tempDiv.textContent;
</script> -->
</head>

<body>

<div class="review-container">
    <div class="review-header">
	    <c:choose>
	        <c:when test="${not empty sessionScope.loginUser}">
	            <a href="${pageContext.request.contextPath}/review/moveCreate.do?locationEnum=${locationEnum}&locId=${location.locId}" class="write-review-btn">리뷰 작성하기</a>
	        </c:when>
	        <c:otherwise>
	            <a href="${pageContext.request.contextPath}/user/login.do" class="write-review-btn">리뷰 작성하기</a>
	        </c:otherwise>
	    </c:choose>
	</div>

    <c:forEach var="review" items="${reviewList}">
        <div class="review-item">
            <div class="review-info">
            	<%-- <span class="review-location"><c:out value="${ }"></c:out></span> --%>
            	<c:if test="${review['userId'] != null}">
            	<div class="review-author">
	            	<span>작성자: <c:out value="${userNicks[review['userId']]}" /></span><br>
	    			<!-- 별점 시각화 -->
                    <span>별점: 
                        <c:forEach begin="1" end="5" var="i">
                            <c:choose>
                                <c:when test="${i <= review.userRev}">
                                    &#9733; <!-- 채워진 별 -->
                                </c:when>
                                <c:otherwise>
                                    &#9734; <!-- 빈 별 -->
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        (${review.userRev} / 5)
                    </span>
            	</div>
				</c:if>
				<c:if test="${review['userId'] == null}">
				    <span>작성자: 알 수 없음</span>
				</c:if>
                
<%--                 <span class="review-rating"><c:out value="${review.rating}" /></span> --%>
                <span class="review-date">작성일: <c:out value="${review.createAt}" /></span>
            </div>
            <div class="review-content">
			    ${review.userReviewText}
			</div>
		</div>
    </c:forEach>

</div>
</body>
</html>