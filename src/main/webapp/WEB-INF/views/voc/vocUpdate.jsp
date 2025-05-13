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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/voc/vocUpdate.css">
	<!-- font 적용 -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
	
	<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.js"></script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

	<div class="voc-form">
	<h1>고객의 소리 수정</h1>
	<form action="${pageContext.request.contextPath}/voc/update.do" method="post">
        <input type="hidden" name="vocId" value="${vocVO.vocId}"/>
        
        <!-- 카테고리 -->
        <select name="category" required>
            <option value="">카테고리를 선택하세요</option>
            <option value="INQRY" <c:if test="${vocVO.category eq 'INQRY'}">selected</c:if>>문의</option>
            <option value="PRAISE" <c:if test="${vocVO.category eq 'PRAISE'}">selected</c:if>>칭찬</option>
            <option value="COMPLAINT" <c:if test="${vocVO.category eq 'COMPLAINT'}">selected</c:if>>불만</option>
            <option value="IMPROVEMENTS" <c:if test="${vocVO.category eq 'IMPROVEMENTS'}">selected</c:if>>개선점</option>
        </select>

        <!-- 제목 -->
        <input type="text" name="title" value="${vocVO.title }"required>

        <!-- 내용 (Summernote) -->
        <textarea id="summernote" name="content" >${vocVO.content }</textarea>

        <!-- 버튼 -->
        <div class="btn-wrapper">
            <button type="submit" class="submit-btn">수정</button>
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