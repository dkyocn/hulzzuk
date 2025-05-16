<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>여행 로그</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/log.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/pagination.css">
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<%-- 🔥 filter 파라미터를 받아서 JSTL 변수로 저장 --%>
<c:set var="selectedFilter" value="${param.filter}" />

<div class="logHeader">
    <%-- 로그 생성 버튼 --%>
    <div class="left">
        <a href="${pageContext.request.contextPath}/log/selectPID.do" class="btn-create">로그 생성</a>
    </div>

    <%-- 필터 선택 드롭다운 --%>
    <div class="right">
        <select id="logFilter" onchange="filterLogs(this.value)">
            <option value="recent" <c:if test="${selectedFilter == 'recent' || empty selectedFilter}">selected</c:if>>최신순</option>
            <option value="love" <c:if test="${selectedFilter == 'love'}">selected</c:if>>인기순 (좋아요)</option>
            <option value="my" <c:if test="${selectedFilter == 'my'}">selected</c:if>>나의 로그</option>
        </select>
    </div>
</div>

<div id="logGallery">
    <div class="galleryGrid">
        <c:forEach var="log" items="${logs}">
        <a href="${pageContext.request.contextPath}/log/detail.do?logId=${log.logId}">
            <div class="galleryItem">
                <img src="<c:out value='${log.imagePath}'/>" alt="Log Image" />
                <div class="overlay">
                    <h3><c:out value="${log.logTitle}"/></h3>
                    <p>❤️ <c:out value="${log.loveCount}" /></p>
                    <p><fmt:formatDate value="${log.logStartDate}" pattern="yyyy-MM-dd" /></p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- pagination 영역 -->
<div class="pagination">
    <c:forEach begin="1" end="${totalCount / amount + (totalCount % amount == 0 ? 0 : 1)}" var="i">
        <c:choose>
            <c:when test="${i == page}">
                <b class="currentPage">${i}</b>
            </c:when>
            <c:otherwise>
                <a class="page" href="/hulzzuk/log/page.do?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>


<script type="text/javascript" src="${pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
function filterLogs(value) {
    if (value === "my") {
        location.href = "${pageContext.request.contextPath}/log/myTripLog.do?filter=my";
    } else if (value === "love") {
        location.href = "${pageContext.request.contextPath}/log/loveRank.do?filter=love";
    } else {
        location.href = "${pageContext.request.contextPath}/log/page.do?filter=recent";
    }
}
</script>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>






</html>