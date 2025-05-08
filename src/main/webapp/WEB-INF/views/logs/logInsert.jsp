<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!DOCTYPE html>
<html lang="ko">

<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
 	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/logInsert.css">
   <%--  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>  --%>
  
<!--  include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> 

<!-- 기존 Bootstrap 3 유지 시 -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- Summernote for Bootstrap 3 -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<!-- include summernote-ko-KR -->
<script src="/resources/js/summernote-ko-KR.js"></script>     

    <title>log Insert</title> 
 
</head>

<c:import url="/WEB-INF/views/common/header.jsp" />


<body>
<div class="log-container container">
    <div class="top-frame grid-frame mb-4">
        <div class="left-image">
            <img src="${pageContext.request.contextPath}/resources/images/logList/no_image.jpg" alt="대표 이미지" class="main-image">
        </div>
        <div class="right-title">
            <input type="text" class="form-control log-title" name="logTitle" placeholder="여행 로그 제목을 입력하세요">
        </div>
    </div>

    <ul class="nav nav-tabs" id="dayTabs">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="tab" href="#day1Content">Day1 <small>(${plan.planStartDate})</small></a>
        </li>
        <c:if test="${plan.planStartDate != plan.planEndDate}">
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#day2Content">Day2 <small>(${plan.planEndDate})</small></a>
            </li>
        </c:if>
    </ul>

    <div class="tab-content">
    
    
   <!-- Day1 탭 내용 -->
        <div class="tab-pane fade active" id="day1Content">
            <c:forEach var="place" items="${day1PlaceList}" varStatus="status">
                <div class="place-item">
                    <div class="circle-number">${status.index + 1}</div>
                    <div class="place-info">
                        <div class="place-name">${place.placeName}</div>
                        <button class="toggle-review-btn btn btn-sm btn-light" data-target="#review-${status.index}">
                            ▽ 후기작성/보기
                        </button>
                        <div class="review-box d-none" id="review-${status.index}">
                            <textarea class="summernote" name="review_${place.locEnum}_${place.id}"></textarea>
                        </div>
                    </div>
                    <c:if test="${not empty place.distanceToNext}">
                        <div class="distance">${place.distanceToNext}km</div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

        <!-- Day2 탭 내용 -->
        <div class="tab-pane fade" id="day2Content">
            <c:forEach var="place" items="${day2PlaceList}" varStatus="status">
                <div class="place-item">
                    <div class="circle-number">${status.index + 1}</div>
                    <div class="place-info">
                        <div class="place-name">${place.placeName}</div>
                        <button class="toggle-review-btn btn btn-sm btn-light" data-target="#review2-${status.index}">
                            ▽ 후기작성/보기
                        </button>
                        <div class="review-box d-none" id="review2-${status.index}">
                            <textarea class="summernote" name="review_${place.locEnum}_${place.id}"></textarea>
                        </div>
                    </div>
                    <c:if test="${not empty place.distanceToNext}">
                        <div class="distance">${place.distanceToNext}km</div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
  
</div>

<script>
$(function() {
    $('.summernote').summernote({
        height: 150,
        lang: 'ko-KR',
        placeholder: "후기를 입력하세요..."
    });

    $('.toggle-review-btn').click(function() {
        const target = $(this).data('target');
        $(target).toggleClass('d-none');
    });
});
</script>

<c:import url="/WEB-INF/views/common/footer.jsp" />

</body>
</html>