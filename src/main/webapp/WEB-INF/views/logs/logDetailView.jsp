<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="kr">
<head>
  <meta charset="UTF-8">
  <title>여행 로그 상세보기</title>

  <!-- 반응형 뷰포트 설정 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Google Fonts - Noto Sans -->
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

  <!-- 사용자 정의 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/logDetailView.css">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

  <!-- Bootstrap 4 -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

  <!-- Summernote (Bootstrap 4용) -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>

  <!-- Summernote 초기화 -->
  <script>
    $(document).ready(function () {
      $('.summernote').summernote({
        placeholder: '여행 후기를 입력해주세요.',
        lang: 'ko-KR',
        height: 50,
        toolbar: [
          ['style', ['bold', 'italic', 'underline', 'clear']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['insert', ['link', 'picture']],
          ['view', ['codeview']]
        ]
      });
    });
  </script>
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp"/>

<div class="container main-panel">

<!-- 로그 제목 및 대표 이미지 영역 -->
<div class="container log-header-panel">
  <div class="row align-items-center">
  
    <!-- 대표 이미지 -->
    <div class="col-md-6">
      <c:choose>
        <c:when test="${not empty log.imagePath && !log.imagePath.endsWith('/resources/images/logList/no_image.jpg')}">
          <img id="preview" class="img-fluid rounded" src="${log.imagePath}" alt="대표 이미지" />
        </c:when>
        <c:otherwise>
          <div class="no-image-box d-flex justify-content-center align-items-center">
            <span>대표 이미지 없음</span>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
    
    <!-- 제목과 날짜 -->
    <div class="col-md-6">
      <h2 class="log-title">${log.logTitle}</h2>
      <p class="log-dates">
        <fmt:formatDate value="${log.logStartDate}" pattern="yyyy-MM-dd"/> ~
        <fmt:formatDate value="${log.logEndDate}" pattern="yyyy-MM-dd"/>
      </p>
    </div>
    
  </div>
</div>

  <!-- Day별 일정 및 후기 -->
  <div class="log-reviews mt-4">
    <c:forEach var="review" items="${reviews}">
   	 <c:if test="${not empty review.logContent}">
      <div class="review-block">
        <h5>Day ${review.planDay}</h5>
        <p>${review.logContent}</p>
      </div>
      </c:if>
    </c:forEach>
  </div>
  
<!-- 네모 아이콘 + 좋아요 수 + 댓글 수 -->
<div class="log-meta">
    <span class="lov-count">❤️ ${log.loveCount}</span>
    <span class="icon-box"></span> ${comments.size()} 댓글
</div>

  <!-- 댓글 섹션 -->
  <div class="log-comments mt-5">
    <h4>댓글 <span class="badge badge-secondary">${fn:length(comments)}</span></h4>
    <c:forEach var="comment" items="${comments}">
      <div class="comment-block">
        <strong>${comment.userId}</strong> <small>${comment.createdAt}</small>
        <p>${comment.content}</p>

        <!-- 대댓글 -->
        <c:forEach var="reply" items="${replies}">
          <c:if test="${reply.parentId == comment.commentId}">
            <div class="reply-block ml-4">
              <strong>${reply.userId}</strong> <small>${reply.createdAt}</small>
              <p>${reply.content}</p>
            </div>
          </c:if>
        </c:forEach>
      </div>
    </c:forEach>
  </div>

</div>
    <!-- 목록으로 돌아가기 버튼 -->
    <div class="btn-back">
        <button onclick="window.history.back();">목록</button>
    </div>
    
<c:import url="/WEB-INF/views/common/footer.jsp" />


</body>
</html>