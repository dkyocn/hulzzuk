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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/review/createReview.css">
	<!-- font 적용 -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
	
	<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.js"></script>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".star");
    const userRevInput = document.getElementById("userRev");
    const black = "${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png";
    const yellow = "${pageContext.request.contextPath}/resources/images/loc/loc-review-yellow.png";
    let selectedRating = 0;

    // 클릭 시 저장
    stars.forEach((star, index) => {
        star.addEventListener("click", () => {
            selectedRating = index + 1;
            userRevInput.value = selectedRating;
            updateStars(selectedRating);
        });

        // hover 시 미리보기
        star.addEventListener("mouseover", () => {
            updateStars(index + 1);
        });

        // 마우스 나가면 기존 선택값 유지
        star.addEventListener("mouseout", () => {
            updateStars(selectedRating);
        });
    });

    function updateStars(rating) {
        stars.forEach((star, idx) => {
            star.src = (idx < rating) ? yellow : black;
        });
    }
});
</script>
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="review-form">
<h2>리뷰 작성하기</h2>
	<form method="post" action="create.do">
		<div class="rating-image">
		    <label for="userRev" style="margin: 0;">별점:</label>
		    <div class="stars">
		        <img src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png" class="star" data-value="1" />
		        <img src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png" class="star" data-value="2" />
		        <img src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png" class="star" data-value="3" />
		        <img src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png" class="star" data-value="4" />
		        <img src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png" class="star" data-value="5" />
		    </div>
		    <!-- 실제 별점 값 -->
		    <input type="hidden" name="userRev" id="userRev" value="0" />
		</div>
  		<textarea id="summernote" name="userReviewText"></textarea>
  		<input Type="hidden" name="locationEnum" value="${locationEnum }"/>
  		<input Type="hidden" name="locId" value="${locId }"/>
  		<div class="btn-wrapper">
            <button type="submit" class="submit-btn">등록</button>
            <button type="button" class="cancel-btn" onclick="history.back();">취소</button>
        </div>
	</form>
</div>
	
	<script>	
		$('#summernote').summernote({
			  // 에디터 크기 설정
			  height: 300,
			  // 에디터 한글 설정
			  lang: 'ko-KR',
			  placeholder: '리뷰를 작성하세요',
			  // 에디터에 커서 이동 (input창의 autofocus라고 생각하시면 됩니다.)
			  toolbar: [
				    // 글자 크기 설정
				    ['fontsize', ['fontsize']],
				    // 글자 [굵게, 기울임, 밑줄, 취소 선, 지우기]
				    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				    // 글자색 설정
				    ['color', ['color']],
				    // 표 만들기
				    ['table', ['table']],
				    // 서식 [글머리 기호, 번호매기기, 문단정렬]
				    ['para', ['ul', 'ol', 'paragraph']],
				    // 줄간격 설정
				    ['height', ['height']],
				    // 이미지 첨부
				    ['insert',['picture']]
				  ],
				  // 추가한 글꼴
				fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
				 // 추가한 폰트사이즈
				fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72','96'],
		        // focus는 작성 페이지 접속시 에디터에 커서를 위치하도록 하려면 설정해주세요.
				focus : true,
		        // callbacks은 이미지 업로드 처리입니다.
				callbacks : {                                                    
					onImageUpload : function(files) {   
		                // 다중 이미지 처리를 위해 for문을 사용했습니다.
						for (var i = 0; i < files.length; i++) {
							imageUploader(files[i], this);
						}
					}
				}
				
		  });
	</script>
	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>