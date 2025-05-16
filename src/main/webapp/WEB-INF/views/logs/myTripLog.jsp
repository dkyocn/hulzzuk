<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My 여행로그</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/MyTripLog.css">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<h1>${pageContext.request.requestURI}</h1>
<%-- ✅ 마이로그 전용 제목 표시 --%>
<h2 id="myTripLogTitle">My 여행 로그</h2>


<c:if test="${pageContext.request.requestURI !=  '/hulzzuk/log/myTripLog.do'}">
    <div class="logHeader">
        <div class="left">
            <a href="${pageContext.request.contextPath}/log/selectPID.do" class="btn-create">로그 생성</a>
        </div>
        <div class="right">
            <select id="logFilter" onchange="filterLogs(this.value)">
                <option value="recent" <c:if test="${selectedFilter == 'recent' || empty selectedFilter}">selected</c:if>>최신순</option>
                <option value="love" <c:if test="${selectedFilter == 'love'}">selected</c:if>>인기순 (좋아요)</option>
                <option value="my" <c:if test="${selectedFilter == 'my'}">selected</c:if>>나의 로그</option>
            </select>
        </div>
    </div>
</c:if>

<div id="logGallery">
    <div class="galleryGrid">
        <c:forEach var="log" items="${logList}">
            <a href="${pageContext.request.contextPath}/log/detail.do?logId=${log.logId}" class="galleryItem">
                <div class="galleryItem">
                    <img src="<c:out value='${log.imagePath}'/>" alt="Log Image" />
                    <div class="overlay">
                        <h3><c:out value="${log.logTitle}"/></h3>
                        <p>❤️ <c:out value="${log.loveCount}" /></p>
                        <p><fmt:formatDate value="${log.logStartDate}" pattern="yyyy-MM-dd" /></p>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>