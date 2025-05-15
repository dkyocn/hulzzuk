<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
 
    <c:set var="queryParams"
       value="action=${ requestScope.action }&keyword=${ requestScope.keyword }&begin=${ requestScope.begin }&end=${ end }&locationEnum=ALL&sortEnum=${requestScope.sortEnum }" />
       
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/accoList.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>


<div class="acco-title-section">
    <h1>숙소</h1>
    <div class="underline"></div>
</div>


<div id="logGallery">
<div class="location-grid">
    <c:forEach items="${ requestScope.accoList }" var="location" >
        <div class="location-card">
	            <div class="location-image">
	            	<a href="${pageContext.request.contextPath}/loc/select.do?locationEnum=ACCO &locId=${location.locId}">
	                	<img src="${ location.imgPath }" alt="검색" style="border:none;">
	                 </a>
	            </div>
            <div class="location-info">
                <h3>${ location.placeName }</h3>
                <p>❤️</p>
                <p>${ location.addressName }</p>
            </div>
        </div>
    </c:forEach>
</div>
</div>

</body>
</html>