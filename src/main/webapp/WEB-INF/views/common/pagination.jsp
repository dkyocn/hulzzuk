<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 4. 24.
  Time: 오후 3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="queryParams"
       value="action=${ requestScope.action }&keyword=${ requestScope.keyword }&begin=${ requestScope.begin }&end=${ end }&locationEnum=${requestScope.locationEnum }&sortEnum=${requestScope.sortEnum }&vocEnum=${requestScope.vocEnum}" />
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/pagination.css">
    <title></title>
</head>
<body>
<div class="pagination">
  <%-- 1페이지로 이동 --%>
  <c:if test="${ paging.currentPage eq 1 }">
    ❮❮ &nbsp;
  </c:if>
  <c:if test="${ paging.currentPage gt 1 }"> <%-- gt : greater than, > 연산자를 의미함 --%>
    <a class="page" href="${ paging.urlMapping }?page=1&${ queryParams }">❮❮</a> &nbsp;
  </c:if>

  <%-- 이전 페이지 그룹으로 이동 --%>
  <%-- 이전 그룹이 있다면 --%>
  <c:if test="${ paging.currentPage != 1 }">
    <a class="page" href="${ paging.urlMapping }?page=${paging.currentPage-1}&${ queryParams }">❮</a> &nbsp;
  </c:if>
  <%-- 이전 그룹이 없다면 --%>
  <c:if test="${ paging.currentPage == 1 }">
    ❮ &nbsp;
  </c:if>

  <%-- 현재 출력할 페이지가 속한 페이지 그룹의 페이지 숫자 출력 : 10개 --%>
  <c:forEach begin="${ paging.startPage }" end="${ paging.endPage }" step="1" var="p">
    <c:if test="${ p eq paging.currentPage }">
      <b class="currentPage">${ p }</b>
    </c:if>
    <c:if test="${ p ne paging.currentPage }">
      <a class="page" href="${ paging.urlMapping }?page=${ p }&${ queryParams }">${ p }</a>
    </c:if>
  </c:forEach>

  <%-- 다음 페이지 그룹으로 이동 --%>
  <%-- 다음 그룹이 있다면 --%>
  <c:if test="${ paging.currentPage != paging.maxPage }">
    <a class="page" href="${ paging.urlMapping }?page=${paging.currentPage + 1}&${ queryParams }">&nbsp;❯</a> &nbsp;
  </c:if>
  <%-- 다음 그룹이 없다면 --%>
  <c:if test="${ paging.currentPage == paging.maxPage}">
    &nbsp;❯ &nbsp;
  </c:if>

  <%-- 마지막 페이지로 이동 --%>
  <c:if test="${ paging.currentPage ge paging.maxPage }"> <%-- ge : greater equal, >= 연산자임 --%>
    ❯❯ &nbsp;
  </c:if>
  <c:if test="${ !(paging.currentPage ge paging.maxPage) }">
    <a class="page" href="${ paging.urlMapping }?page=${ paging.maxPage }&${ queryParams }">❯❯</a> &nbsp;
  </c:if>
</div>
</body>
</html>
