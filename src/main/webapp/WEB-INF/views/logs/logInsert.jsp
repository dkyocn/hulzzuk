<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/logInsert.css">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <!-- Bootstrap -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
  <!-- Summernote -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.js"></script>

  <title>log Insert</title>
</head>

<c:import url="/WEB-INF/views/common/header.jsp" />

<body>
  <div class="log-container">
    <!-- 대표 이미지 및 제목 입력 -->
    <div class="top-frame">
      <div class="left-image">
        <img id="preview" src="${pageContext.request.contextPath}/resources/images/logList/no_image.jpg" alt="대표 이미지" class="main-image">
     <!--   <input type="file" name="logImage" accept="image/*">  잠시주석  -->
        <input type="file" name="logImage" id="logImageInput" accept="image/*">
        
      </div>
      <div class="right-title">
      	<label for="logTitle"></label>
        <input type="text" id="logTitle" name="logTitle" placeholder="여행 로그 제목을 입력하세요">
      </div>
    </div>

    <!-- Day 탭 메뉴 -->
    <ul class="nav nav-tabs" id="dayTabs">
      <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#day1Content">
          Day1 <small>${plan.planStartDate}</small>
        </a>
      </li>
      <c:if test="${hasDay2}">
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#day2Content">
            Day2 <small>${plan.planEndDate}</small>
          </a>
        </li>
      </c:if>
    </ul>

    <!-- Day별 장소 및 후기 작성 영역 -->
    <div class="tab-content">
      <div class="tab-pane fade show active" id="day1Content">
        <c:forEach var="place" items="${day1PlaceList}" varStatus="status">
          <div class="place-item" 
            data-accoid="${place.accoId}" 
            data-restid="${place.restId}" 
            data-attrid="${place.attrId}">

            <div class="circle-number">${status.index + 1}</div>
            <div class="place-info">
              <div class="place-name">${place.placeName}</div>
              <button type="button" class="toggle-review-btn" data-target="#review-${status.index}">▽ 후기작성/보기</button>
              <div class="review-box d-none" id="review-${status.index}">
                <textarea class="summernote" name="review_${place.category}_${place.seq}"></textarea>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

      <c:if test="${hasDay2}">
        <div class="tab-pane fade" id="day2Content">
          <c:forEach var="place" items="${day2PlaceList}" varStatus="status">
            <div class="place-item" 
              data-accoid="${place.accoId}" 
              data-restid="${place.restId}" 
              data-attrid="${place.attrId}">

              <div class="circle-number">${status.index + 1}</div>
              <div class="place-info">
                <div class="place-name">${place.placeName}</div>
                <button type="button" class="toggle-review-btn" data-target="#review2-${status.index}">▽ 후기작성/보기</button>
                <div class="review-box d-none" id="review2-${status.index}">
                  <textarea class="summernote" name="review_${place.category}_${place.seq}"></textarea>
                </div>
              </div>
              <c:if test="${not empty place.distanceToNext}">
                <div class="distance">${place.distanceToNext}km</div>
              </c:if>
            </div>
          </c:forEach>
        </div>
      </c:if>

      <!-- 저장 버튼 -->
<div class="save-button-wrapper text-right mt-4 mb-5">
  <button id="saveAllBtn" class="btn btn-primary" type="button">전체 저장하기</button>
  <c:if test="${not empty logId && not empty imagePath}">
    <script>
        alert("로그가 성공적으로 저장되었습니다!\nLog ID: ${logId}\n이미지 경로: ${imagePath}");
    </script>
  </c:if>
</div>
</div> <!-- 상위 div 닫힘 -->
</div> <!-- 상위 div 닫힘 -->

<!-- 후기 저장용 스크립트 -->
<script>
const contextPath = "${pageContext.request.contextPath}"; // 스크립트 제일 위에 한번만 선
<!-- // ✅ 이미지 업로드 (Ajax처리 대표이미지)   -->
$('#logImageInput').on('change', function () {
	  const formData = new FormData();
	  formData.append("logImage", this.files[0]);

	  $.ajax({
	    url: contextPath + "/log/uploadImage.do",  // <- 컨트롤러 경로 맞춰줌(url: contextPath + "/log/uploadImage.do",)
	    type: "POST",
	    data: formData,
	    contentType: false,
	    processData: false,
	    success: function (imagePath) {
	   // console.log("Ajax로 받은 imagePath:",  contextPath + imagePath);
	      if (imagePath === 'fail') {
	        alert("업로드 실패!");
	        return;
	      }
	      const fullPath = contextPath + imagePath;
	      console.log("이미지 경로:", fullPath);
	      alert("이미지 저장 완료!");
	   	  // 미리보기 이미지 src 변경
          $("#preview").attr("src",  fullPath);
          // 실제 저장할 경로를 data 속성으로 저장
          $("#preview").attr("data-imgpath",  fullPath);
	    },
	    error: function () {
	      alert("이미지 업로드 실패!");
	    }
	  });
	});
	
//저장버튼 클릭시 전체 로그 저장시작  (logId → reviews 수집 → 후기 저장)
// ✅ 1. 저장 버튼 눌렀을 때 저장 시작
  $('#saveAllBtn').on('click', function () {
    const $btn = $(this); 
    // ✅ 1-1. 사용자 확인
    if (!confirm('저장된 내용으로 전체 로그를 저장하시겠습니까?')) return;
    // ✅ 중복저장방지 
    $btn.prop('disabled', true);
    console.log("저장 버튼 눌림");
    // ✅ 대표 이미지 경로 확인
    const imagePath = $('#preview').attr('data-imgpath');
    console.log("최종 저장 전 이미지 경로:", imagePath);
   // if (!imagePath || imagePath === "" || imagePath.includes("no_image.jpg")) {
   //   alert("대표 이미지를 업로드해주세요!");
   //   $btn.prop('disabled', false); // 실패 시 다시 활성화
    //  return;
    
    
 	// ✅ 2. 로그 메타 정보 준비
    const logMeta = {
      logTitle: $('#logTitle').val(),
      logStartDate: $('#logStartDate').val(),
      logEndDate: $('#logEndDate').val(),
      planDay: 1,  // 예시로 Day1 지정. 필요 시 동적으로 수정
      userId: $('#userIdHolder').val(),
      imagePath: imagePath  // 위에서 미리 추출한 imagePath 사용  // 미리보기 이미지 경로 또는 업로드된 경로 : 이게 null이면 안 됨!
    };
    
 // ✅ 1. 로그 저장 (중복 저장 방지: saveMeta.do 한 번만 호출)
    $.ajax({
      url: contextPath + "/log/saveMeta.do", 
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(logMeta),
      xhrFields: { withCredentials: true }, // 🔥 쿠키 포함 필수 (세션유지)
      success: function (logId) {
        console.log("받은 logId:", logId);

        // ✅ 3-1. logId 유효성 확인
        if (!logId || logId === 0) {
          // ✅ 3-2. logId가 비어있을 경우 최근 logId조회해서 보완 (예외 대비)
          $.ajax({
            url: contextPath + "/log/getRecentLogId.do",
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
              userId: $('#userIdHolder').val(),
              logTitle: $('#logTitle').val()
            }),
            success: function (recentLogId) {
              if (recentLogId && recentLogId > 0) {
              //  logId = recentLogId;  // 대체
                continueReviewInsert(logId);  // ✅ 후기 저장 함수 호출
                console.log(logID);
              } else {
                alert('logId 조회 실패');
                $btn.prop('disabled', false);
              }
            },
            error: function () {
              alert('logId 조회 ajax 실패');
              $btn.prop('disabled', false);
            }
          });
        } else {
          // ✅ 3-3. logId 정상 수신 시 후기 저장 진행
          continueReviewInsert(logId);  // ✅ 후기 저장 함수 호출
        }
      }
      //error: function () {
      //  alert('로그 메타 정보 저장 실패! 로그인 세션이 만료되었을 수 있습니다.');
     //   $btn.prop('disabled', false);
     // }
    });
  
    	 // ✅ 4-1. 후기저장로직함수. 리스트시
    function continueReviewInsert(logId) {
        const reviews = [];
        $('.summernote').each(function () {
          const name = $(this).attr('name');
          const content = $(this).summernote('code');
	     	// ✅ 4-1-1. name 유효성 검사
	      if (!name || !name.includes('_')) return;
	     	
	     	// ✅ 4-1-2. name에서 category, seq 추출
	      const [_, category, seq] = name.split('_');
	   		// ✅ 4-1-3. Day1 / Day2 구분
	      const planDay = $(this).closest('.tab-pane').attr('id') === 'day1Content' ? 1 : 2;
	   		// ✅ 4-1-4. 해당 장소의 고유 ID 정보 추출
	      const placeItem = $(this).closest('.place-item');
	   		
	      const accoId = placeItem.data('accoid') || null;
	      const restId = placeItem.data('restid') || null;
	      const attrId = placeItem.data('attrid') || null;

	  		// ✅ 4-1-5. Summernote HTML → 텍스트로 비어있으면 저장 제외
	      const tempDiv = document.createElement("div");
	      tempDiv.innerHTML = content;
	      const cleaned = tempDiv.textContent || tempDiv.innerText || "";
	      const trimmed = cleaned.replace(/\u00A0/g, '').trim();
		  if (trimmed.length === 0) return;
		  
	  	  // ✅ 4-1-6. 리뷰 배열에 저장
	      reviews.push({
	        accoId, restId, attrId, 
	        logContent: content,
	        planDay, category, seq, logId // 🔥 logId 포함 필수
	      });
	    });
        // ✅ 4-2.후기 없는 경우 
      if (reviews.length === 0) {
        alert('저장할 후기 내용이 없습니다.');
        $btn.prop('disabled', false);
        return;
      }

 		// ✅ 5-1. 후기 저장 요청 보내기
        $.ajax({
          url: contextPath + "/log/reviewInsertAll.do",
          type: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(reviews),   
          success: function (response) {
        	// ✅ 5-2. 성공 시 이동
        	  if (response === 'success') {
                  alert('전체 저장 완료!');
                  location.href = contextPath + '/log/detail.do?logId=${logId}';
                } else {
                  alert('후기 저장 실패!');
                  $btn.prop('disabled', false);
                }
              },
              error: function () {
                  alert('후기 저장 중 서버 오류 발생!');
                  $btn.prop('disabled', false);
                }
              });
  			 } //end of continueReviewInsert
        });  // end of saveAllBtn click
</script>

  <!-- 이미지 미리보기 -->
  <script>
    $('input[name="logImage"]').on('change', function (e) {
      const reader = new FileReader();
      reader.onload = function (e) {
        $('.main-image').attr('src', e.target.result);
      };
      reader.readAsDataURL(e.target.files[0]);
    });
  </script>

  <!-- 후기 toggle 및 summernote 초기화 -->
  <script>
    $('.toggle-review-btn').on('click', function () {
      const target = $(this).data('target');
      $(target).toggleClass('d-none');
      const $textarea = $(target).find('.summernote');
      if (!$textarea.hasClass('note-editor')) {
        $textarea.summernote({
          height: 200,
          placeholder: '후기를 입력하세요...',
          tabsize: 2,
          lang: 'ko-KR',
          disableDragAndDrop: true  // 이 옵션도 안정성에 도움돼
        });
      }
    });
  </script>

  <c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
