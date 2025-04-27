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
            <img src="${pageContext.request.contextPath}/resources/images/search.png" style="border:none;">
        </button>
    </form>
</div>


<c:choose>
		<c:when test="${ locationEnum == 'ACCO' }">
			<H1 class="custom-title">숙소</H1>
		</c:when>
		<c:when test="${ locationEnum == 'REST' }">
			<H1 class="custom-title">맛집</H1>
		</c:when>
		<c:when test="${ locationEnum == 'ATTR' }">
			<H1 class="custom-title">즐길거리</H1>
		</c:when>
</c:choose>


<div style="text-align: center;">
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


<center>
	<button onclick="location.href='${ pageContext.servletContext.contextPath }/loc/page.do?locationEnum=ACCO&page=1&sortEnum=LOVEDESC';">목록</button>
	&nbsp; &nbsp;
</center>
<br>

<%-- 조회된 공지사항 목록 출력 --%>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="0">
	<tr>
		<th>장소명</th>
		<th>장소 주소</th>
	</tr>
	<c:forEach items="${ requestScope.list }" var="location">
		<tr align="center">
			<td>${ location.placeName }</td>
			<td>${ location.roadAddressName }</td>
		</tr>
	</c:forEach>
</table>
<br>

<c:import url="/WEB-INF/views/common/pagination.jsp" />
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>