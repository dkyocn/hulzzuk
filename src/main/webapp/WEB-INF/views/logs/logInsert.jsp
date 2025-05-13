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
  
  <!-- 폰트 및 사용자 정의 CSS -->
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/logInsert.css">


  <!-- jQuery만 사용 (Bootstrap 제거됨) -->
  <!-- <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> -->
  
  <!-- Bootstrap 4.x -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

  <title>log Insert</title>
</head>

<!-- 공통 헤더 -->
<c:import url="/WEB-INF/views/common/header.jsp" />

<body>

  <div class="log-container">
    
    <!-- 상단 대표이미지 + 제목 입력창 (2열 구조) -->
    <div class="top-frame">
      <!-- 좌측: 대표 이미지 + 업로드 버튼 -->
      <div class="left-image">
        <img src="${pageContext.request.contextPath}/resources/images/logList/no_image.jpg" alt="대표 이미지" class="main-image">
        <input type="file" name="logImage" accept="image/*">
      </div>

      <!-- 우측: 제목 입력창 -->
      <div class="right-title">
        <input type="text" name="logTitle" placeholder="여행 로그 제목을 입력하세요">
      </div>
    </div>

    <!-- 탭 메뉴 -->
    <ul class="nav nav-tabs" id="dayTabs">
      <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#day1Content">
          Day1 <small>${plan.planStartDate}</small>
        </a>
      </li>
      <c:if test="${plan.planStartDate != plan.planEndDate}">
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#day2Content">
            Day2 <small>${plan.planEndDate}</small>
          </a>
        </li>
      </c:if>
    </ul>

    <!-- 탭 내용 -->
    <div class="tab-content">
      
      <!-- Day1 내용 -->
      <div class="tab-pane fade show active" id="day1Content">
        <c:forEach var="place" items="${day1PlaceList}" varStatus="status">
          <div class="place-item">
            <div class="circle-number">${status.index + 1}</div>
            <div class="place-info">
              <div class="place-name">${place.placeName}</div>
              <button class="toggle-review-btn" data-target="#review-${status.index}">
                ▽ 후기작성/보기
              </button>
              <div class="review-box d-none" id="review-${status.index}">
                <textarea class="summernote" name="review_${place.category}_${place.seq}"></textarea>

              </div>
            </div>
           <%-- 거리 표시 영역은 추후 거리계산 로직 구현 후 사용 예정 --%>
				<%-- 
				<c:if test="${not empty place.distanceToNext}">
				  <div class="distance">${place.distanceToNext}km</div>
				</c:if>
				--%>
          </div>
        </c:forEach>
      </div>

      <!-- Day2 내용 -->
      <div class="tab-pane fade" id="day2Content">
        <c:forEach var="place" items="${day2PlaceList}" varStatus="status">
          <div class="place-item">
            <div class="circle-number">${status.index + 1}</div>
            <div class="place-info">
              <div class="place-name">${place.placeName}</div>
              <button class="toggle-review-btn" data-target="#review2-${status.index}">
                ▽ 후기작성/보기
              </button>
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

    </div>
  </div>

  <!-- 공통 푸터 -->
  <c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
