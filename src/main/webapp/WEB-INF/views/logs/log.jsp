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
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="logHeader">
    <div class="left" >
        <a href="${pageContext.request.contextPath}/log/create.do" class="btn-create">로그 생성</a>
    </div>
    <div class="right">
        <select id="logFilter" onchange="filterLogs()">
            <option value="recent">최신순</option>
            <option value="love">인기순 (좋아요)</option>
            <option value="my">나의 로그</option>
        </select>
    </div>
</div>

<div id="logGallery">
    <div class="galleryGrid">
        <c:forEach var="log" items="${logs}">
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


<script type="text/javascript" src-"${pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
<script>
function filterLogs() {
    // 아직 기능 없음
}
</script>
</script>



<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>






</html>