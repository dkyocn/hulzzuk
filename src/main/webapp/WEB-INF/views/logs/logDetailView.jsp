<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ko">
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

  
 <script type="text/javascript">


 // 찜하기 버튼
    document.addEventListener("DOMContentLoaded", function () {
    	window.locId =  "${requestScope.location.locId}";
    	window.locEnum = "${requestScope.locationEnum}";
        const locLoveBtn = document.querySelector(".locLoveBtn");
        const locLoveImg = locLoveBtn.querySelector(".locLoveImg");

        // 기본 상태 설정
        const loveDefaultImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png";
        const loveHoverImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-love-orange.png";

        // 마우스를 올렸을 때
        locLoveBtn.addEventListener("mouseover", function () {
        	if(locLoveBtn.dataset.loved !== 'true') {
        		locLoveImg.src = loveHoverImgSrc;
        	}
        });

        // 마우스가 떠났을 때
        locLoveBtn.addEventListener("mouseout", function () {
        	if(locLoveBtn.dataset.loved !== 'true') {
        		locLoveImg.src = loveDefaultImgSrc;
        	}
        });    
    });   
    
 // 페이지 로딩 시 하트 상태 반영
    document.addEventListener("DOMContentLoaded", function () {
        const buttons = document.querySelectorAll(".locLoveBtn");

        buttons.forEach(button => {
            const logId = button.dataset.logId;
            const img = button.querySelector(".locLoveImg");

            fetch('${pageContext.request.contextPath}/love/logCheck.do?logId=' + encodeURIComponent(logId))
                .then(response => response.json())
                .then(data => {
                    const loved = data.loved === true;
                    button.dataset.loved = loved.toString();
                    img.src = loved
                        ? '${pageContext.request.contextPath}/resources/images/loc/loc-love-filled.png'
                        : '${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png';
                })
                .catch(err => console.error("찜 상태 체크 실패:", err));
        });
    });

    function toggleLove(button) {
        const userId = '${sessionScope.loginUser.userId}';
        
        console.log("userId:", userId);
        
        if (!userId) {
            alert("로그인이 필요합니다.");
            return;
        }
        
        const loved = button.dataset.loved === 'true';
        const logId = button.dataset.logId;
        const img = button.querySelector('.locLoveImg');

        const url = loved
            ? '${pageContext.request.contextPath}/love/logDelete.do?logId=' + encodeURIComponent(logId)
            : '${pageContext.request.contextPath}/love/logCreate.do?logId=' + encodeURIComponent(logId);

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
        })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                if (!loved) {
                    img.src = '${pageContext.request.contextPath}/resources/images/loc/loc-love-filled.png';
                    button.dataset.loved = 'true';
                } else {
                    img.src = '${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png';
                    button.dataset.loved = 'false';
                }
            } else {
                console.warn("찜 요청 실패", data); // 디버그용, UI에는 안 보임
            }
        })
        .catch(err => {
            console.error("통신 오류:", err);
        });
    }
  </script>
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
</div> <!-- div close -->

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
    <button class="locLoveBtn" data-loved="false" data-log-id="${log.logId}" onclick="toggleLove(this)">
    		<img class="locLoveImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png">
 			&nbsp;<fmt:formatNumber value="${loveCount}"/></button>
     <button class="icon-box" > 💬 ${comments.size()} </button>

</div>
<button class="btn-back" onclick="history.back()"> 목록</button>
</div>

<!-- 댓글 전체 영역 -->
<div class="comments-container">

    
<!-- 댓글 리스트 반복 -->
  <c:forEach var="comment" items="${comments}">
    <div class="comment-wrapper mb-4 p-3 border rounded">

      <%-- 댓글 상단 메타: 로그인 유저 정보 기준으로 출력 --%>
<div class="d-flex justify-content-between align-items-center flex-wrap">
  <div class="d-flex align-items-center mb-3">
    <img src="${pageContext.request.contextPath}/resources/images/common/user-temp.jpg" class="profile-icon mr-3">
    
    <strong class="comment-author mr-4">
      <c:choose>
        <c:when test="${not empty loginUser}">
          ${loginUser.userNick}
        </c:when>
        <c:otherwise>
          익명
        </c:otherwise>
      </c:choose>
    </strong>

    <span class="text-muted small">
      <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd HH:mm" />
    </span>
  </div>
</div>

        <!-- 수정/삭제  로그인 유저와 작성자가 같을 경우에만  -->
        <c:if test="${loginUser.userId == comment.userId}">
          <div class="comment-actions mb-2">
            <a href="#" class="edit-link text-primary mr-2" onclick="editComment(${comment.commentId})">수정</a>
            <a href="#" class="delete-link text-danger" onclick="deleteComment(${comment.commentId})">삭제</a>
          </div>
        </c:if>
        </div>
</c:forEach>

      <!-- 댓글 본문 -->
      <div class="comment-content mb-3">
        <p class="mb-0">${comment.content}</p>
      </div>

      <!-- 대댓글 목록 -->
      <div class="reply-blocks ml-3">
        <c:forEach var="reply" items="${replyMap[comment.commentId]}">
          <!-- 조건: 로그인한 사용자가 작성자일 때만 출력 -->
       <c:if test="${reply.userId == loginUser.userId}">
          <div class="reply-block mb-2 p-2 bg-light border rounded">
            <div class="d-flex justify-content-between align-items-center flex-wrap">
              <div>
                <strong class="reply-author mr-2">${reply.userNick} </strong>
                <span class="text-muted small">
                  <fmt:formatDate value="${reply.createdAt}" pattern="yyyy-MM-dd HH:mm" />
                </span>
              </div>
            </div>
            <div class="reply-content mt-1 reply-indent">${reply.content}</div>
          </div>
         </c:if>
        </c:forEach>
      </div>
      
	  <!-- 대댓글 수정/삭제 -->
		<c:if test="${sessionScope.loginUser.userId == reply.userId}">
	      <div class="btn-group">
	        <button class="btn btn-sm btn-outline-secondary">수정</button>
	        <button class="btn btn-sm btn-outline-danger">삭제</button>
	      </div>
	    </c:if>
    
	<!-- 대댓글 입력창   -->
	<div class="reply-input mt-3">
	    <input type="hidden" class="parent-comment-id" value="${comment.commentId}">
	    <textarea class="form-control reply-content" rows="2" placeholder="답글을 입력하세요"></textarea>
	    <button type="button" class="btn btn-sm btn-outline-secondary reply-insert-btn" data-comment-id="${comment.commentId}">답글 등록</button>
	</div>

    </div> <!-- .comment-wrapper -->
<%--   </c:forEach> --%>
  

<!-- 댓글 영역 시작 -->
<div class="container mt-5" id="log-comment-section">

    <!-- ✅ 로그인 여부 체크 -->
    <c:choose>
        <c:when test="${not empty loginUser}">
        
        
	<%-- <!-- 댓글 입력 카드 -->
        <div class="card comment-card mb-4">
          <div class="card-body d-flex">
            <img src="${loginUser.userProfile}" class="rounded-circle mr-3" style="width: 50px; height: 50px;" alt="프로필 이미지">
            <div class="flex-grow-1">
              <form id="commentForm" class="mb-0">
                <input type="hidden" name="type" value="LOG" />
                <input type="hidden" name="logId" value="${log.logId}" />
                <div class="form-group mb-2">
                  <textarea id="commentContent" name="content" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
                </div>
                <div class="text-end">
                  <button type="button" id="submitCommentBtn" class="btn btn-sm btn-outline-primary">댓글 등록</button>
                </div>
              </form>
            </div>
          </div>
        </div>
         --%>
        </c:when>

        <c:otherwise>
            <!-- 비로그인 안내 -->
            <div class="alert alert-warning mt-4">
                댓글을 작성하려면 <a href="${pageContext.request.contextPath}/user/login.do">로그인</a>이 필요합니다.
            </div>
        </c:otherwise>
    </c:choose>


<%--     <!-- ✅ 댓글 리스트 출력 -->
    <div class="log-comment-list mt-4">
        <c:forEach var="comment" items="${comments}">
            <div class="comment-item border p-3 mb-3 rounded">
                <div class="comment-meta mb-1">
                    <strong>${comment.userNick}</strong>
                    <span class="text-muted small ms-2">${comment.createdAt}</span>
                </div>
                <div class="comment-text">${comment.content}</div>
            </div>
        </c:forEach>
    </div> --%>

</div>
<!-- 댓글 영역 끝 -->
</div>




<script type="text/javascript">

<!-- 댓글 작성 Ajax -->

$('.btn-submit-comment').click(function () {
	  const content = $('#commentContent').val();
	  const logId = $('input[name="logId"]').val();
	  
	  if (!content.trim()) return alert('댓글을 입력하세요');

	  
	  $.ajax({
		    type: 'POST',
		    url: '/hulzzuk/log/commentInsert.do',
		    data: JSON.stringify({ content: content, logId: logId }),
		    contentType: 'application/json',
		    success: function (res) {
		      if (res.success) {
		        // ① card comment 를 직접 append 하지 말고
		        // ② comment-wrapper 에 새 댓글 HTML 삽입
		        $('#commentContent').val(''); // 입력창 초기
		        alert("등록되었습니다!");
		      }else if (res.redirect) {
	                window.location.href = res.redirect;
		    }
		  });
		});

//댓글 등록 버튼
function submitReply(commentId) {
    const content = document.getElementById("replyInput-" + commentId).value;

    fetch('/log/comment/replyInsert.do', {
        method: 'POST',
        headers: {'Content-Type': 'application/json' },
        body: JSON.stringify({
            parentId: commentId,
            logId: yourLogId,  //  변수 설정
            content: content
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(data.message);
         //   location.reload(); // 혹은 새로 대댓글만 append
        } else {
            if (data.redirect) {
                location.href = data.redirect;
            } else {
                alert(data.message);
            }
        }
    });
}
//작성댓글불러오기
 function loadComments(logId) {
    fetch(`/log/getComments.do?logId=${logId}`)
        .then(res => res.text())
        .then(html => {
            document.getElementById("commentListWrapper").innerHTML = html;
        })
        .catch(err => console.error("댓글 불러오기 실패", err));
} 

//대댓글입력 
$(document).on("click", ".reply-insert-btn", function () {
    const commentId = $(this).data("comment-id");
    const replyContent = $(this).siblings(".reply-content").val();

    if (!replyContent.trim()) {
        alert("답글을 입력하세요.");
        return;
    }

    $.ajax({
        url: "/hulzzuk/log/comment/replyInsert.do",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            commentId: commentId,
            content: replyContent
        }),
        success: function (res) {
            if (res.success) {
                alert("답글이 등록되었습니다.");
                location.reload();
            } else {
                alert(res.message);
                if (res.redirect) {
                    window.location.href = res.redirect;
                }
            }
        },
        error: function () {
            alert("답글 등록 중 오류가 발생했습니다.");
        }
    });
});
//댓글 삭제
$(document).on('click', '.delete-comment-btn', function () {
  const commentId = $(this).data('id');
  if (confirm('댓글을 삭제하시겠습니까?')) {
    $.ajax({
      type: 'POST',
      url: '/hulzzuk/log/comment/delete.do',
      data: JSON.stringify({ commentId: commentId }),
      contentType: 'application/json',
      success: function (res) {
        if (res.success) {
          alert(res.message);
          location.reload();
        }
      }
    });
  }
});

// 댓글 수정 - 간단한 프롬프트 방식
$(document).on('click', '.edit-comment-btn', function () {
  const commentId = $(this).data('id');
  const currentContent = $(this).closest('.comment-box').find('.comment-content p').text();
  const updatedContent = prompt('수정할 내용을 입력하세요', currentContent);
  if (updatedContent && updatedContent !== currentContent) {
    $.ajax({
      type: 'POST',
      url: '/hulzzuk/log/comment/update.do',
      data: JSON.stringify({ commentId: commentId, content: updatedContent }),
      contentType: 'application/json',
      success: function (res) {
        if (res.success) {
          alert(res.message);
          location.reload();
        }
      }
    });
  }
});


</script>


<c:import url="/WEB-INF/views/common/footer.jsp" />



</body>
</html>