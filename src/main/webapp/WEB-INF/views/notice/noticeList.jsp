<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>hulzzuk</title>
    <!-- css 적용 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notice/noticePage.css">
    <!-- font 적용 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="notice-container">
    <h2>공지 사항</h2>

    <!-- 검색 영역 -->
    <div class="search-wrapper">
        <form class="search-box" action="page.do" method="get" id="searchForm">
            <input type="hidden"  name="limit" id="limit"  value="10">
            <input class="search-txt" type="text" name="keyword" placeholder="검색어를 입력하세요.">
            <button class="search-btn" type="submit">
                <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색">
            </button>
        </form>
    </div>

    <!-- 작성하기 버튼 -->
    <div class="write-button-wrapper">
        <c:if test="${sessionScope.loginUser.userId eq 'admin@gmail.com'}">
            <a href="${pageContext.request.contextPath}/notice/moveCreate.do" class="write-button">작성하기</a>
        </c:if>
    </div>

    <!-- 목록 테이블 -->
    <table class="notice-table">
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="notice" items="${noticeList}" varStatus="status">
            <tr>
                <td>
                    <c:if test="${notice.isPinned}">
                        📌
                    </c:if>
                    <c:if test="${!notice.isPinned}">
                        ${paging.listCount - ((paging.currentPage - 1) * paging.limit) - status.index}</td>
                    </c:if>
                <td>
                    <a href="${pageContext.request.contextPath}/notice/select.do?noticeId=${notice.noticeId}">
                            ${notice.title}
                    </a>
                </td>
                <td><fmt:formatDate value="${notice.createdAt}" pattern="yyyy.MM.dd"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:import url="/WEB-INF/views/common/pagination.jsp" />

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>