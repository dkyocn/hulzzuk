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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/voc/vocList.css">
	<!-- font 적용 -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="voc-container">
    <h2>고객의 소리</h2>
    
    <!-- 검색 영역 -->
    <div class="search-wrapper">
        <form class="search-box" action="page.do" method="get" id="searchForm">
            <select class="vocEnum" id="vocEnum" name="vocEnum" onchange="document.getElementById('searchForm').submit();">
                <option value="ALL" <c:if test="${param.vocEnum == 'ALL'}">selected</c:if>>전체</option>
                <option value="INQRY" <c:if test="${param.vocEnum == 'INQRY'}">selected</c:if>>문의</option>
                <option value="PRAISE" <c:if test="${param.vocEnum == 'PRAISE'}">selected</c:if>>칭찬</option>
                <option value="COMPLAINT" <c:if test="${param.vocEnum == 'COMPLAINT'}">selected</c:if>>불만</option>
                <option value="IMPROVEMENTS" <c:if test="${param.vocEnum == 'IMPROVEMENTS'}">selected</c:if>>개선점</option>
            </select> 
            <input type="hidden"  name="limit" id="limit"  value="10">
            <input class="search-txt" type="text" name="keyword" placeholder="검색어를 입력하세요.">
            <button class="search-btn" type="submit">
                <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색">
            </button>
        </form>
    </div>
    
    <!-- 작성하기 버튼 -->
	<div class="write-button-wrapper">
    <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
            <a href="${pageContext.request.contextPath}/voc/create.do" class="write-button">작성하기</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/login.do" class="write-button">작성하기</a>
        </c:otherwise>
    </c:choose>
</div>

    <!-- 목록 테이블 -->
    <table class="voc-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>카테고리</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="voc" items="${vocList}" varStatus="status">
                <tr>
                    <td>${paging.listCount - ((paging.currentPage - 1) * paging.limit) - status.index}</td>
                    <td>
                    	<a href="${pageContext.request.contextPath}/voc/select.do?vocId=${voc.vocId}">
                    		${voc.title}
                    	</a>
                    </td>
                    <td>${userNicks[voc.userId]}</td>
                    <td><fmt:formatDate value="${voc.createdAt}" pattern="yyyy.MM.dd"/></td>
                    <td>
                        <span class="tag ${voc.category}">${voc.category}</span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
	
<c:import url="/WEB-INF/views/common/pagination.jsp" />

<c:import url="/WEB-INF/views/common/footer.jsp" />	
</body>
</html>