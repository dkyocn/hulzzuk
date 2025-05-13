<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
	<!-- css 적용 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/voc/vocDetailView.css">
	<!-- font 적용 -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

	<div class="voc-detail-container">

    <!-- 제목 -->
    <div class="first-voc-title">
    	 <h2>고객의 소리</h2>
    </div>
    
    <div class="voc-title">
        <h3>${vocVO.title}</h3>
    </div>
    
    <div class="voc-info-bar">
    	<div class="voc-meta">
            	작성자: ${vocVO.userId} | 작성일: <fmt:formatDate value="${vocVO.createdAt}" pattern="yyyy.MM.dd"/>
        </div>
        <c:if test="${loginUserId == vocVO.userId || loginUserId == 'admin@gmail.com'}">
	        <div class="voc-actions">
			        <a href="${pageContext.request.contextPath}/voc/update.do?vocId=${vocVO.vocId}" class="action-btn edit">수정</a>
			        <a href="${pageContext.request.contextPath}/voc/delete.do?vocId=${vocVO.vocId}" class="action-btn delete">삭제</a>
	    		</div>
	    </c:if>
    </div>
    
    <!-- 내용 -->
    <div class="voc-content-box">
        <p>${vocVO.content}</p>
        <div class="voc-category-tag ${vocVO.category}">
            <c:choose>
                <c:when test="${vocVO.category == 'INQRY'}">문의</c:when>
                <c:when test="${vocVO.category == 'PRAISE'}">칭찬</c:when>
                <c:when test="${vocVO.category == 'COMPLAINT'}">불만</c:when>
                <c:when test="${vocVO.category == 'IMPROVEMENTS'}">개선점</c:when>
                <c:otherwise>기타</c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 댓글 입력 -->
    <div class="comment-write-box">
        <textarea placeholder="댓글을 작성해 주세요"></textarea>
        <button type="button" class="comment-submit-btn">등록</button>
    </div>

    <!-- 댓글 리스트 (예시용, 반복문 필요) -->
    <div class="comment-list">
        <div class="comment-item admin">
            <div class="comment-header">
                관리자 <span class="comment-date">2025.03.31</span>
                <span class="comment-actions">
                    <a href="#">수정</a> | <a href="#">삭제</a>
                </span>
            </div>
            <div class="comment-body">먼저 불편을 드려 죄송합니다. 처리해드리겠습니다.</div>
        </div>

        <div class="comment-item user">
            <div class="comment-header">
                ${voc.userId} <span class="comment-date">2025.03.31</span>
                <span class="comment-actions">
                    <a href="#">수정</a> | <a href="#">삭제</a>
                </span>
            </div>
            <div class="comment-body">네</div>
        </div>
    </div>

    <!-- 목록 버튼 -->
    <div class="back-to-list">
        <a href="${pageContext.request.contextPath}/voc/page.do?vocEnum=${vocVO.category}&page=1" class="list-btn">목록</a>
    </div>

</div>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>