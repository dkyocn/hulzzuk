<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
    <!-- css 적용 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notice/noticeDetailView.css">
    <!-- font 적용 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="notice-detail-container">
<!-- 제목 -->
<div class="first-notice-title">
    <h2>공지사항</h2>
</div>
<div class="notice-title">
    <h3>${notice.title}</h3>
</div>
<div class="notice-info-bar">
    <div class="notice-meta">
        작성일: <fmt:formatDate value="${notice.createdAt}" pattern="yyyy.MM.dd"/>
    </div>
    <c:if test="${sessionScope.loginUser.userId eq 'admin@gmail.com'}">
        <div class="notice-actions">
            <a href="${pageContext.request.contextPath}/notice/moveUpdate.do?noticeId=${notice.noticeId}" class="action-btn edit">수정</a>
            <a href="${pageContext.request.contextPath}/notice/delete.do?noticeId=${notice.noticeId}" class="action-btn delete">삭제</a>
        </div>
    </c:if>
</div>

<!-- 내용 -->
<div class="notice-content-box">
    <p>${notice.content}</p>
</div>

<!-- 목록 버튼 -->
<div class="back-to-list">
    <a href="${pageContext.request.contextPath}/notice/page.do?page=1" class="list-btn">목록</a>
</div>

</div>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>