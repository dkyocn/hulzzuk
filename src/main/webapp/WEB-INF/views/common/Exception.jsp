<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Exception Page</title>
</head>
<body>
<h1>에러 발생</h1>
<c:if test="${ empty exceptionType }">
    <p>예외 정보가 없습니다.</p>
</c:if>
<c:if test="${ not empty exceptionType }">
    <p>예외 유형: <c:out value="${exceptionType}" /></p>
    <p>에러 메시지: <c:out value="${exceptionMessage}" /></p>
    <button onclick="history.back()">이전 페이지로 돌아가기</button>
</c:if>
</body>
</html>
