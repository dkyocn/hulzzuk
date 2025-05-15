<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 5. 14.
  Time: 오전 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>hulzzuk</title>
    <!-- css 적용 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notice/noticeCreate.css">
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
<script type="text/javascript">
    function submitNotice() {
        // 폼 데이터 수집
        let noticeData = {
            category: 'NOTICE',
            title: $('#title').val(),
            isPinned: $('#isPinned').is(':checked'), // 체크 여부 (true/false)
            content: $('#summernote').summernote('code') // Summernote 내용
        };

        // 입력 검증
        if (!noticeData.title) {
            alert('제목을 입력하세요.');
            return;
        }
        if (!noticeData.content || noticeData.content === '<p><br></p>') {
            alert('내용을 입력하세요.');
            return;
        }

        // AJAX 요청
        $.ajax({
            url: '${pageContext.request.contextPath}/notice/create.do',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(noticeData),
            dataType: 'json',
            success: function(response) {
                console.log("서버 응답:", response); // 디버깅 로그
                if (response.successYN) {
                    alert('공지사항이 등록되었습니다.');
                    window.location.href = '${pageContext.request.contextPath}/notice/page.do?page=1';
                } else {
                    console.error("공지사항 등록 실패:", response.message || "알 수 없는 오류");
                    alert("공지사항 등록을 실패했습니다: " + (response.message || "알 수 없는 오류"));
                }
            },
            error: function(xhr, status, error) {
                console.error("AJAX 오류:", status, error);
                alert("공지사항 등록 중 오류가 발생했습니다: " + error);
            }
        });
    }
</script>

<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="notice-form">
    <h1>공지 사항</h1>
    <div class="noticeTitle">
        <!-- 제목 -->
        <input type="text" id="title" value="${notice.title }"required>

        <div class="pinned">
            <input type="checkbox" id="isPinned" value="${notice.isPinned}">
            <p>고정 여부</p>
        </div>
    </div>

    <!-- 내용 (Summernote) -->
    <textarea id="summernote" name="content"></textarea>

    <!-- 버튼 -->
    <div class="btn-wrapper">
        <button type="button" class="submit-btn" onclick="submitNotice()">등록</button>
        <button type="button" class="cancel-btn" onclick="history.back();">취소</button>
    </div>
</div>

<script>
    $('#summernote').summernote({
        // 에디터 크기 설정
        height: 300,
        // 에디터 한글 설정
        lang: 'ko-KR',
        placeholder: '내용을 작성하세요',
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
