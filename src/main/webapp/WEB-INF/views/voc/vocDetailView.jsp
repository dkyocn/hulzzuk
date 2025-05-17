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
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
	
	function comcreate(vocId, content){
		 var content = $("textarea[name='content']").val();
		    if(content.trim() === "") {
		        alert("댓글 내용을 입력해주세요.");
		        return;
		    }
		    
		 $.ajax({
             url: '${pageContext.request.contextPath}/comment/create.do?type=VOC',
             type: 'POST',
             data: { id: vocId, content: content },
             dataType: 'json',
             success: function(response) {
                 if (response.status === "success") {
                	 location.reload();
                 }
             },
             error: function(xhr, status, error) {
                 console.error('삭제 실패:', status, error, xhr.responseText);
                 alert('항목 삭제에 실패했습니다. 오류: ' + xhr.responseText);
             }
         });
	}
			
	function comdelete(commentId){
		$.ajax({
            url: '${pageContext.request.contextPath}/comment/delete.do',
            type: 'POST',
            data: { id: commentId },
            dataType: 'json',
            success: function(response) {
                if (response.status === "success") {
               	 location.reload();
                }
            },
            error: function(xhr, status, error) {
                console.error('삭제 실패:', status, error, xhr.responseText);
                alert('항목 삭제에 실패했습니다. 오류: ' + xhr.responseText);
            }
        });
	}
	</script>
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

    <c:choose>
    <c:when test="${empty loginUserId}">
        <div class="comment-write-box not-logged-in">
            <p>댓글을 작성하려면 <a href="${pageContext.request.contextPath}/user/loginSelect.do">로그인</a>이 필요합니다.</p>
        </div>
    </c:when>
    <c:otherwise>
            <div class="comment-write-box">
                <textarea name="content" placeholder="댓글을 작성해 주세요"></textarea>
                <button type="button" class="comment-submit-btn" onclick="comcreate(${vocVO.vocId}, $('textarea[name=content]').val())">등록</button>
            </div>
    </c:otherwise>
</c:choose>

    <!-- 댓글 리스트 (예시용, 반복문 필요) -->
    <div class="comment-list">
       <c:forEach var="comment" items="${requestScope.commentList}">
		    <div class="comment-item">
		        <div class="comment-header">
		            ${userNicks[comment.userId]}
		            <span class="comment-date">
		                <fmt:formatDate value="${comment.createdAt}" pattern="yyyy.MM.dd" />
		            </span>
		            <span class="comment-actions">
		                <button type="button" onclick="comdelete(${comment.commentId})">삭제</button>
		            </span>
		        </div>
		        <div class="comment-body">${comment.content}</div>
		         <!-- 대댓글을 이 위치로 이동 -->
		        <c:if test="${not empty recommentMap[comment.commentId]}">
		            <div class="recomment-list">
		                <c:forEach var="recomment" items="${recommentMap[comment.commentId]}">
		                    <div class="recomment-item">
		                        <div class="comment-header">
		                            ${recouserNicks[recomment.userId]}
		                            <span class="comment-date">
		                                <fmt:formatDate value="${recomment.createdAt}" pattern="yyyy.MM.dd" />
		                            </span>
		                            <span class="comment-actions">
		                                <a href="#">삭제</a>
		                            </span>
		                        </div>
		                        <div class="comment-body">${recomment.content}</div>
		                    </div>
		                </c:forEach>
		            </div>
		        </c:if>
		        	<div class="comment-submit-wrapper">
					    <button type="submit" class="comment-submit-gray-btn">댓글 등록</button>
					</div>
		    </div>
		</c:forEach>
</div>

    <!-- 목록 버튼 -->
    <div class="back-to-list">
        <a href="${pageContext.request.contextPath}/voc/page.do?vocEnum=ALL&page=1" class="list-btn">목록</a>
    </div>

</div>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>