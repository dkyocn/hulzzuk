<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationAllListView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="inner">
    <form class="search-box" action="page.do" method="get" id="searchForm">
        <select class="category" id="category" name="locationEnum" onchange="document.getElementById('searchForm').submit();">
            <option value="ALL">전체</option>
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

<%-- <div class="sort-box">
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
</div> --%>

<!-- 즐길거리 -->
<c:import url="/WEB-INF/views/location/attrList.jsp" />

<!-- 맛집 -->
<c:import url="/WEB-INF/views/location/restList.jsp" />

<!--숙소 -->
<c:import url="/WEB-INF/views/location/accoList.jsp" />

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>