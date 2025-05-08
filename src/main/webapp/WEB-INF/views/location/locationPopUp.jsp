<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Location List</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationPopUp.css">

  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
  <script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
      // 기존 마우스 호버 이벤트
      const addBtn = document.querySelector(".addBtn");
      const addImg = addBtn.querySelector(".addImg");
      const add = addBtn.querySelector(".add");

      const defaultImgSrc = "${pageContext.request.contextPath}/resources/images/common/add-button-black.png";
      const hoverImgSrc = "${pageContext.request.contextPath}/resources/images/common/add-button-orange.png";
      const defaultTextColor = "#000000";
      const hoverTextColor = "#E96A18";

      addBtn.addEventListener("mouseover", function () {
        addImg.src = hoverImgSrc;
        add.style.color = hoverTextColor;
      });
      addBtn.addEventListener("mouseout", function () {
        addImg.src = defaultImgSrc;
        add.style.color = defaultTextColor;
      });

      // 검색 폼 AJAX 처리
      const searchForm = document.querySelector("#searchForm");
      searchForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const formData = new FormData(searchForm);
        const queryString = new URLSearchParams(formData).toString();
        fetch('${pageContext.request.contextPath}/loc/list.do?' + queryString)
                .then(response => response.text())
                .then(html => {
                  document.body.innerHTML = html; // 새로고침 대신 내용 교체
                  initializeEvents(); // 이벤트 다시 바인딩
                })
                .catch(error => console.error("Error fetching locations:", error));
      });

      // 모든 "추가" 버튼에 이벤트 추가
      function initializeEvents() {
        document.querySelectorAll(".addBtn").forEach(btn => {
          btn.addEventListener("click", function (e) {
            e.preventDefault();
            const parentDiv = btn.closest(".listDiv");
            // listDiv에서 data-id 속성으로 id 추출
            const id = parentDiv.getAttribute("data-id");
            // 검색 폼에서 선택된 locationEnum 값 사용
            const locEnum = document.querySelector("#category").value;

            // 부모 창에 id 전달
            window.opener.postMessage({ id: id, locEnum: locEnum }, window.opener.location.origin);
            window.close(); // 팝업 닫기
          });
        });
      }

      // 초기 이벤트 바인딩
      initializeEvents();
    });
  </script>
</head>
<body>

<div class="inner">
  <form class="search-box" action="list.do" method="get" id="searchForm">
    <select class="category" id="category" name="locationEnum" onchange="document.getElementById('searchForm').submit();">
      <option value="ALL">전체</option>
      <option value="ACCO" <c:if test="${param.locationEnum == 'ACCO'}">selected</c:if>>숙소</option>
      <option value="REST" <c:if test="${param.locationEnum == 'REST'}">selected</c:if>>맛집</option>
      <option value="ATTR" <c:if test="${param.locationEnum == 'ATTR'}">selected</c:if>>즐길거리</option>
    </select>
    <input class="search-txt" type="text" name="keyword" placeholder="검색어를 입력하세요.">
    <button class="search-btn" type="submit">
      <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="검색" style="border:none;">
    </button>
  </form>
</div>

<c:choose>
  <c:when test="${ locationEnum == 'ACCO' }">
    <h1 class="custom-title">
      <a href="${pageContext.request.contextPath}/loc/list.do?locationEnum=ACCO">숙소</a>
    </h1>
  </c:when>
  <c:when test="${ locationEnum == 'REST' }">
    <h1 class="custom-title">
      <a href="${pageContext.request.contextPath}/loc/list.do?locationEnum=REST">맛집</a>
    </h1>
  </c:when>
  <c:when test="${ locationEnum == 'ATTR' }">
    <h1 class="custom-title">
      <a href="${pageContext.request.contextPath}/loc/list.do?locationEnum=ATTR">즐길거리</a>
    </h1>
  </c:when>
  <c:when test="${ locationEnum == 'ALL' }">
    <h1 class="custom-title">
      <a href="${pageContext.request.contextPath}/loc/list.do?locationEnum=ALL">전체</a>
    </h1>
  </c:when>
</c:choose>

<div class="sort-box">
  <form method="get" action="list.do">
    <input type="hidden" name="locationEnum" value="${param.locationEnum}" />
    <input type="hidden" name="keyword" value="${param.keyword}" />
  </form>
</div>

<div id="logGallery">
  <c:forEach items="${ requestScope.locationList }" var="location">
    <div class="listDiv" data-id="${location.locId}">
      <div class="imgName">
        <img src="${location.imgPath}">
        <div class="planName">
          <p class="name">${location.placeName}</p>
          <p class="category">즐길거리</p>
        </div>
      </div>
      <button class="addBtn"><img class="addImg" src="${pageContext.request.contextPath}/resources/images/common/add-button-black.png"><span class="add">추가</span></button>
    </div>
  </c:forEach>
</div>
</body>
</html>