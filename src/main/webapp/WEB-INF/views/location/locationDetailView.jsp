<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/location/locationDetailView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<%-- jQuery js 파일 로드 선언 --%>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<!-- Kakao Maps API (JavaScript 키는 본인 것으로 교체하세요) -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d9ed74efa0b6e99b6f0e99ac22f40a71"></script>
<script type="text/javascript">
// 찜하기 버튼
document.addEventListener("DOMContentLoaded", function () {
	window.locId =  "${requestScope.location.locId}";
	window.locEnum = "${requestScope.locationEnum}";
    const locLoveBtn = document.querySelector(".locLoveBtn");
    const locLoveImg = locLoveBtn.querySelector(".locLoveImg");
    const locLoveText = locLoveBtn.querySelector(".locLoveText");

    // 기본 상태 설정
    const loveDefaultImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png";
    const loveHoverImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-love-orange.png";

    // 마우스를 올렸을 때
    locLoveBtn.addEventListener("mouseover", function () {
    	if(locLoveBtn.dataset.loved !== 'true') {
    		locLoveImg.src = loveHoverImgSrc;
        	locLoveText.style.color = "#E96A18";
    	}
    });

    // 마우스가 떠났을 때
    locLoveBtn.addEventListener("mouseout", function () {
    	if(locLoveBtn.dataset.loved !== 'true') {
    		locLoveImg.src = loveDefaultImgSrc;
        	locLoveText.style.color = "#000000";
    	}
    });

// 일정추가 버튼
    const locPlanBtn = document.querySelector(".locPlanBtn");
    const locPlanImg = locPlanBtn.querySelector(".locPlanImg");
    const locPlanText = locPlanBtn.querySelector(".locPlanText");

    // 기본 상태 설정
    const planDefaultImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-plan-black.png";
    const planHoverImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-plan-orange.png";

    // 마우스를 올렸을 때
    locPlanBtn.addEventListener("mouseover", function () {
    	locPlanImg.src = planHoverImgSrc;
    	locPlanText.style.color = "#E96A18";
    });

    // 마우스가 떠났을 때
    locPlanBtn.addEventListener("mouseout", function () {
    	locPlanImg.src = planDefaultImgSrc;
    	locPlanText.style.color = "#000000";
    });

// 리뷰작성 버튼
    const locReviewBtn = document.querySelector(".locReviewBtn");
    const locReviewImg = locReviewBtn.querySelector(".locReviewImg");
    const locReviewText = locReviewBtn.querySelector(".locReviewText");

    // 기본 상태 설정
    const reviewDefaultImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png";
    const reviewHoverImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-review-orange.png";

    // 마우스를 올렸을 때
    locReviewBtn.addEventListener("mouseover", function () {
    	locReviewImg.src = reviewHoverImgSrc;
    	locReviewText.style.color = "#E96A18";
    });

    // 마우스가 떠났을 때
    locReviewBtn.addEventListener("mouseout", function () {
    	locReviewImg.src = reviewDefaultImgSrc;
    	locReviewText.style.color = "#000000";
    });
    
// 공유하기 버튼
    const locShareBtn = document.querySelector(".locShareBtn");
    const locShareImg = locShareBtn.querySelector(".locShareImg");
    const locShareText = locShareBtn.querySelector(".locShareText");

    // 기본 상태 설정
    const shareDefaultImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-share-black.png";
    const shareHoverImgSrc = "${pageContext.request.contextPath}/resources/images/loc/loc-share-orange.png";

    
    // 마우스를 올렸을 때
    locShareBtn.addEventListener("mouseover", function () {
    	locShareImg.src = shareHoverImgSrc;
    	locShareText.style.color = "#E96A18";
    });

    // 마우스가 떠났을 때
    locShareBtn.addEventListener("mouseout", function () {
    	locShareImg.src = shareDefaultImgSrc;
    	locShareText.style.color = "#000000";
    });
});

navigator.geolocation.getCurrentPosition(function(position) {
    const lat = ${location.y};
    const lng = ${location.x};

    const latElem = document.getElementById('lat');
    const lngElem = document.getElementById('lng');
    
    if (latElem) latElem.textContent = lat;
    if (lngElem) lngElem.textContent = lng;
    
    const container = document.getElementById('map');
    const options = {
        center: new kakao.maps.LatLng(lat, lng),
        level: 3
    };

    const map = new kakao.maps.Map(container, options);

    const markerPosition = new kakao.maps.LatLng(lat, lng);
    const marker = new kakao.maps.Marker({
        position: markerPosition
    });
    
    marker.setDraggable(true)

    marker.setMap(map);
});


function planPopup(){
	window.open('${ pageContext.servletContext.contextPath }/plan/LocDetailMovePlan.do','planList', 'width=700,height=700');
}

function clip() {
    const url = window.location.href;  // 실제 현재 URL 가져오기
    const textarea = document.createElement("textarea");
    textarea.value = url;
    document.body.appendChild(textarea);
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    alert("링크가 복사되었습니다. 필요하신 곳에 붙여넣기 하세요!");
}    

function toggleLove(button) {
    const userId = '${sessionScope.loginUser.userId}';
    
    console.log("userId:", userId);
    
    if (!userId) {
        alert("로그인이 필요합니다.");
        return;
    }
    
    const loved = button.dataset.loved;
    const img = button.querySelector('.locLoveImg');

    const url = loved
        ? '${pageContext.request.contextPath}/love/delete.do'
        : '${pageContext.request.contextPath}/love/create.do?locId=' + ${requestScope.location.locId} + '&locationEnum=${requestScope.locationEnum}';
	/* fetch(contextPath + `/loc/getLocation.do?locId=` + ${requestScope.location.locId} + `&locationEnum=` + ${requestScope.locationEnum}); */
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
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="container">

    <!-- 제목 + 별점 + 찜 수 -->
    <div class="title-section">
        <h1>${location.placeName}</h1>
        <div class="rating">
    		★ <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> / 5 &nbsp;&nbsp; ❤️ 20
		</div>
    </div>

    <!-- 메인 이미지 -->
    <div class="main-image">
        <img src="${ location.imgPath }" alt="메인 이미지" style="border:none;">
    </div>

    <!-- 버튼 그룹 -->
    <div class="button-group">
        <button class="locLoveBtn" data-loved="false" onclick="toggleLove(this)">
    		<img class="locLoveImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png">
 			<span class="locLoveText">찜하기</span></button>
        <button class="locPlanBtn" onclick="planPopup()">
			<img class="locPlanImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-plan-black.png">
            <span class="locPlanText">일정추가</span> </button>
        <button class="locReviewBtn" onclick="location.href='${ pageContext.servletContext.contextPath }/review/moveCreate.do?locationEnum=${locationEnum }&locId=${location.locId }';">
			<img class="locReviewImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-review-black.png">
            <span class="locReviewText">리뷰작성</span> </button>
        <button class="locShareBtn" onclick="clip()">
			<img class="locShareImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-share-black.png">
		    <span class="locShareText">공유하기</span> 
		</button>
    </div>

    <!-- 기본 정보 + 예약 버튼 -->
   <h2 class="section-title">기본 정보</h2>
    <div class="basic-info">
    <div id="map" style="width:600px;height:500px;"></div>
	<!-- <p>위도: <span id="lat"></span>, 경도: <span id="lng"></span></p> -->
        <div class="info-text">
            <p>주소: ${location.addressName}</p>
            <p>전화번호: ${location.phone}</p>
            <c:choose>
				    <c:when test="${ locationEnum == 'ACCO' }">
				      <button class="reserve-btn" onclick="window.location.href='${location.placeUrl}'">예약하기</button>
				    </c:when>
				    <c:when test="${locationEnum eq 'REST'}">
				      <button class="reserve-btn" onclick="window.location.href='${location.restMenu}'">메뉴보기</button>
				    </c:when>
				  </c:choose>
        </div>
    </div>



    <!-- 리뷰 영역 -->
    <div class="review-section">
        <h2>리뷰 (${ reviewCount })</h2>

        <div class="review-container">
        
		<c:import url="/WEB-INF/views/review/reviewListView.jsp" />

        <!-- 더보기 버튼 -->
        <%-- <div class="more-button-container">
            <a href="reviewList?locId=${location.locId}" class="more-button">더보기</a>
        </div> --%>
    </div>

    <!-- 로그 영역 -->
    <div class="log-section">
        <h2>로그 (${ logCount })</h2>
        
       <c:import url="/WEB-INF/views/logs/logListView.jsp" />
    </div>

</div>
</div>
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>