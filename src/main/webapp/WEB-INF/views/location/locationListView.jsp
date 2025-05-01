<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

 
  <c:set var="nowpage" value="1" />
 <c:if test="${ !empty requestScope.paging.currentPage }">
 	<c:set var="nowpage" value="${ requestScope.paging.currentPage }" />
 </c:if>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Location List</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationListView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

</head>
<body>

<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="inner">
    <form class="search-box" action="page.do" method="get" id="searchForm">
        <select class="category" id="category" name="locationEnum" onchange="document.getElementById('searchForm').submit();">
            <option value="">전체</option>
            <option value="ACCO" <c:if test="${param.locationEnum == 'ACCO'}">selected</c:if>>숙소</option>
            <option value="REST" <c:if test="${param.locationEnum == 'REST'}">selected</c:if>>맛집</option>
            <option value="ATTR" <c:if test="${param.locationEnum == 'ATTR'}">selected</c:if>>즐길거리</option>
        </select> 
        <input class="search-txt" type="text" name="keyword" placeholder="검색어를 입력하세요.">
        <button class="search-btn" type="submit">
            <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색" style="border:none;">
        </button>
    </form>
</div>

<c:choose>
	<c:when test="${ locationEnum == 'ACCO' }">
		<h1 class="custom-title">
				<a href="${pageContext.request.contextPath}/loc/page.do?locationEnum=ACCO&page=1">숙소</a>
		</h1>
	</c:when>
	<c:when test="${ locationEnum == 'REST' }">
		<h1 class="custom-title">
				<a href="${pageContext.request.contextPath}/loc/page.do?locationEnum=REST&page=1">맛집</a>
		</h1>
	</c:when>
	<c:when test="${ locationEnum == 'ATTR' }">
		<h1 class="custom-title">
				<a href="${pageContext.request.contextPath}/loc/page.do?locationEnum=ATTR&page=1">즐길거리</a>
		</h1>
	</c:when>
</c:choose>

<div class="sort-box">
    <form method="get" action="page.do">
        <input type="hidden" name="locationEnum" value="${param.locationEnum}" />
        <input type="hidden" name="keyword" value="${param.keyword}" />
        <input type="hidden" name="page" value="${param.page}" />
        <select name="sortEnum" onchange="this.form.submit()">
            <option value="LOVEDESC" ${param.sortEnum == 'LOVEDESC' ? 'selected' : ''}>찜 많은 순</option>
            <option value="REVIEWDESC" ${param.sortEnum == 'REVIEWDESC' ? 'selected' : ''}>리뷰 많은 순</option>
            <option value="NAMEDESC" ${param.sortEnum == 'NAMEDESC' ? 'selected' : ''}>이름 내림차순</option>
            <option value="NAMEASC" ${param.sortEnum == 'NAMEASC' ? 'selected' : ''}>이름 오름차순</option>
        </select>
    </form>
</div>

<div id="logGallery">
<div class="location-grid">
    <c:forEach items="${ requestScope.list }" var="location">
        <div class="location-card">
	            <div class="location-image">
	            	<a href="${pageContext.request.contextPath}/loc/select.do?locationEnum=${locationEnum }&locId=${location.locId}">
	                	<img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색" style="border:none;">
	                 </a>
	            </div>
            <div class="location-info">
                <h3>${ location.placeName }</h3>
                <p>❤️</p>
                <p>${ location.roadAddressName }</p>
            </div>
        </div>
    </c:forEach>
</div>
</div>

 
<!-- <div id="logGallery"> -->
<%--     <div class="galleryGrid">
        <c:forEach var="log" items="${logs}">
            <div class="galleryItem">
                <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색" style="border:none;">
                <div class="overlay">
                    <h3><c:out value="${location.placeName}"/></h3>
                    <p>❤️</p>
                    <p>${ location.roadAddressName }</p>
                </div>
            </div>
        </c:forEach>
    </div> --%>
<!-- </div> -->



<c:import url="/WEB-INF/views/common/pagination.jsp" />
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>