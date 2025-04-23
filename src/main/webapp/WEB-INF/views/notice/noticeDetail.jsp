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
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<h1 align="center">${ requestScope.notice.noticeId } 번 게시글 상세보기</h1>
<hr>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
    <tr><th>제 목</th><td>${ requestScope.notice.title }</td></tr>
    <tr><th>등록날짜</th>
        <td><fmt:formatDate value="${ requestScope.notice.updatedAt }" pattern="YYYY-MM-DD" /></td></tr>
    <tr><th>내 용</th><td><c:out value="${ requestScope.notice.content }" escapeXml="false"/></td></tr>
    <tr><th colspan="2">
        <button onclick="location.href='blist.do?page=${ requestScope.currentPage }';">목록</button> &nbsp;
        <button onclick="history.go(-1);">이전 페이지로 이동</button>
    </th></tr>
</table>
</body>
</html>