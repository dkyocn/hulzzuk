<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
           
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/attrList.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>

<div class="attr-title-section">
    <h1>즐길거리</h1>
    <div class="underline"></div>
</div>

<div id="logGallery">
<div class="location-grid">
    <c:forEach items="${ requestScope.attrList }" var="location">
        <div class="location-card">
	            <div class="location-image">
		            
	            	<a href="${pageContext.request.contextPath}/loc/select.do?locationEnum=ATTR &locId=${location.locId}">
	                	<img src="${ location.imgPath }" alt="검색" style="border:none;">
	                 </a>
	            </div>
            <div class="location-info">
                <h3>${ location.placeName }</h3>
                <p>❤️<fmt:formatNumber value="${location.loveCount}"/></p>
                <p>${ location.addressName }</p>
            </div>
        </div>
    </c:forEach>
</div>
</div>

<%-- <div class="pagination">
  1페이지로 이동
  <c:if test="${ attrPaging.currentPage eq 1 }">
    ❮❮ &nbsp;
  </c:if>
  <c:if test="${ attrPaging.currentPage gt 1 }"> gt : greater than, > 연산자를 의미함
    <a class="page" href="${ attrPaging.urlMapping }?page=1&${ queryParams }">❮❮</a> &nbsp;
  </c:if>

  이전 페이지 그룹으로 이동
  이전 그룹이 있다면
  <c:if test="${ attrPaging.currentPage != 1 }">
    <a class="page" href="${ attrPaging.urlMapping }?page=${attrPaging.currentPage-1}&${ queryParams }">❮</a> &nbsp;
  </c:if>
  이전 그룹이 없다면
  <c:if test="${ attrPaging.currentPage == 1 }">
    ❮ &nbsp;
  </c:if>

  현재 출력할 페이지가 속한 페이지 그룹의 페이지 숫자 출력 : 10개
  <c:forEach begin="${ attrPaging.startPage }" end="${ attrPaging.endPage }" step="1" var="p">
    <c:if test="${ p eq attrPaging.currentPage }">
      <b class="currentPage">${ p }</b>
    </c:if>
    <c:if test="${ p ne attrPaging.currentPage }">
      <a class="page" href="${ attrPaging.urlMapping }?page=${ p }&${ queryParams }">${ p }</a>
    </c:if>
  </c:forEach>

  다음 페이지 그룹으로 이동
  다음 그룹이 있다면
  <c:if test="${ attrPaging.currentPage != attrPaging.maxPage }">
    <a class="page" href="${ attrPaging.urlMapping }?page=${attrPaging.currentPage + 1}&${ queryParams }">&nbsp;❯</a> &nbsp;
  </c:if>
  다음 그룹이 없다면
  <c:if test="${ attrPaging.currentPage == attrPaging.maxPage}">
    &nbsp;❯ &nbsp;
  </c:if>

  마지막 페이지로 이동
  <c:if test="${ attrPaging.currentPage ge attrPaging.maxPage }"> ge : greater equal, >= 연산자임
    ❯❯ &nbsp;
  </c:if>
  <c:if test="${ !(attrPaging.currentPage ge attrPaging.maxPage) }">
    <a class="page" href="${ attrPaging.urlMapping }?page=${ attrPaging.maxPage }&${ queryParams }">❯❯</a> &nbsp;
  </c:if>
</div>
 --%>
</body>
</html>