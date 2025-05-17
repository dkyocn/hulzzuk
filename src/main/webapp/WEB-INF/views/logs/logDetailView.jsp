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
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp"/>

 

<div class="container main-panel">

<!-- 로그 제목 및 대표 이미지 영역 -->
<div class="container log-header-panel">
  <div class="row align-items-center">
  
    <!-- 대표 이미지 -->
    <!--w-100 :가로전체.h-auto:비율유지,img-fluid: 부트스트랩 반응형이미지 클래스  -->
    <%-- <img class="img-fluid w-50 h-auto rounded log-image" src="${log.imagePath}" alt="대표 이미지" /> --%>
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
        <h5>Day ${review.planDay} </h5>
        <p>${review.logContent}</p>
      </div>
      </c:if>
    </c:forEach>
  </div>

 
<!-- 네모 아이콘 + 좋아요 수 + 댓글 수 -->
<div class="log-meta-wrapper">
<div class="log-meta">
    <span class="lov-count">❤️ ${log.loveCount}</span>
    <span class="icon-box">💬 ${comments.size()} </span>
</div>
<button class="btn-back" onclick="history.back()"> 목록</button>
</div>

<!-- 댓글 전체 영역 -->
<div class="comments-container">

<!-- 댓글 리스트 반복 -->
  <c:forEach var="comment" items="${comments}">
    <div class="comment-wrapper mb-4 p-3 border rounded">

      <!-- 댓글 상단 메타 -->
      <div class="d-flex justify-content-between align-items-center flex-wrap">
        <div class="d-flex align-items-center mb-3">
          <img src="${pageContext.request.contextPath}/resources/images/common/user-temp.jpg" class="profile-icon mr-3" >
          <strong class="comment-author mr-4">${comment.userNick}</strong>
          <span class="text-muted small">
            <fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm" />
          </span>
        </div>

        <!-- 수정/삭제 -->
        <c:if test="${loginUser.userId == comment.userId}">
          <div class="comment-actions mb-2">
            <a href="#" class="edit-link text-primary mr-2" onclick="editComment(${comment.commentId})">수정</a>
            <a href="#" class="delete-link text-danger" onclick="deleteComment(${comment.commentId})">삭제</a>
          </div>
        </c:if>
      </div>

      <!-- 댓글 본문 -->
      <div class="comment-content mb-3">
        <p class="mb-0">${comment.content}</p>
      </div>

      <!-- 대댓글 목록 -->
      <div class="reply-blocks ml-3">
        <c:forEach var="reply" items="${comment.replies}">
          <div class="reply-block mb-2 p-2 bg-light border rounded">
            <div class="d-flex justify-content-between align-items-center flex-wrap">
              <div>
                <strong class="reply-author mr-2">${reply.userNick}</strong>
                <span class="text-muted small">
                  <fmt:formatDate value="${reply.createdAt}" pattern="yyyy-MM-dd HH:mm" />
                </span>
              </div>
            </div>
            <div class="reply-content mt-1 reply-indent">${reply.content}</div>
          </div>
        </c:forEach>
      </div>

	<!-- 대댓글 입력창 (댓글 id따라 다르게 생성되는걸로 ) -->
	<div class="reply-input mt-3">
        <form method="post" action="/hulzzuk/log/comment/replyInsert.do">
         <input type="hidden" name="commentId" value="${comment.commentId}" class="parent-comment-id">
          <input type="hidden" name="logId" value="${log.logId}" />
          <div class="form-group">
            <textarea name="content" class="form-control" rows="2" placeholder="답글을 입력하세요"></textarea>
          </div>
          <button type="submit" class="btn btn-sm btn-outline-secondary">답글 등록</button>
        </form>
      </div>

    </div> <!-- .comment-wrapper -->
  </c:forEach>
  

 <!-- ️ 댓글 입력창 (항상 표시) --> 
  <!-- 댓글 입력창 (AJAX 처리용) -->
 <c:choose>
<c:when test="${not empty loginUser}">
  <!-- 댓글 작성 영역 -->
<div id="log-comment-box" class="container mt-5">
  <div class="row">
    <div class="col-12 col-md-12">
      <div class="comment-card">
        <div class="comment-header">
          <h5 class="comment-title">댓글 작성</h5>
        </div>
        <div class="comment-body">
          <form id="commentForm">
            <!-- Hidden Inputs -->
            <input type="hidden" name="type" value="LOG" />
            <input type="hidden" name="id" value="${log.logId}" />

            <!-- 댓글 입력창 -->
            <div class="form-group mb-3">
              <label for="commentContent" class="form-label">내용</label>
              <textarea id="commentContent" name="content" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
            </div>

            <!-- 등록 버튼 -->
            <div class="text-end">
              <button type="button" id="submitCommentBtn" class="btn btn-submit-comment">댓글 등록</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</c:when>

  <c:otherwise>
    <%-- 비로그인 사용자에게 안내 --%>
    <div class="alert alert-warning mt-4">
      댓글을 작성하려면 <a href="${pageContext.request.contextPath}/user/login.do">로그인</a>이 필요합니다.
    </div>
  </c:otherwise>
</c:choose>

	 <%-- 로그댓글조회 CommentVO 연결.0517--%>
	<c:forEach var="comment" items="${comments}">
		<div class="comment">
		<strong>${comment.userId}</strong> - ${comment.content}
        <span class="timestamp">${comment.createdAt}</span>
    </div>
	</c:forEach>
	
  
</div>
</div>

<script type="text/javascript">

<!-- 댓글 작성 Ajax -->

$('#submitCommentBtn').click(function () {
	  const content = $('#commentContent').val();
	  const logId = $('input[name="id"]').val();

	  if (!content.trim()) {
	    alert("댓글 내용을 입력해주세요.");
	    return;
	  }

	  $.ajax({
	    type: 'POST',
	    url: '/hulzzuk/comment/create.do',
	    data: JSON.stringify({
	      type: 'LOG',
	      id: logId,
	      content: content
	    }),
	    contentType: 'application/json; charset=utf-8',
	    success: function (response) {
	      if (response.success) {
	        alert('댓글이 등록되었습니다.');
	        location.reload();
	      } else {
	        alert(response.message || '댓글 등록 실패');
	      }
	    },
	    error: function () {
	      alert('서버 오류로 댓글 등록에 실패했습니다.');
	    }
	  });
	});



</script>






<c:import url="/WEB-INF/views/common/footer.jsp" />



</body>
</html>