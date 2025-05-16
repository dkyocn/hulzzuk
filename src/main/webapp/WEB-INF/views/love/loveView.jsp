<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<style type="text/css">
/* 공통 제목 스타일 */
.lovePageTitle {
    position: relative;
    text-align: left;
    margin-top: 40px;
    margin-left: 500px;
    z-index: 10;
}

.lovePageTitle h1 {
    font-size: 36px;
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
}

.underline hr {
    width: 150px;
    height: 4px;
    background-color: orange;
    border: 0;
    margin: 0;
    border-radius: 2px;
}

.loveContentWrapper {
    width: 100%;
    max-width: 650px;
    margin: 0 auto;
    padding: 0 15px;
}

.loveCard {
    display: flex;
    align-items: center;
    justify-content: space-between;
    border: 1px solid #ddd;
    padding: 10px 15px;
    margin: 12px 0;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

.loveCard-left img {
    width: 120px;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
}

.loveCard-middle {
    flex: 1;
    text-align: left;
    padding-left: 16px;
}

.loveTitle {
    font-size: 15px;
    font-weight: bold;
    margin: 0;
    color: #222;
}

.loveCard-right {
    display: flex;
    align-items: center;
    padding-left: 10px;
}

.locLoveBtn, .logLoveBtn {
    background-color: transparent;
    border: none;
    cursor: pointer;
    padding: 5px;
}

.locLoveImg, .logLoveImg {
    width: 24px;
    height: 24px;
}

.moreBtnWrapper {
    text-align: center;
    margin-top: 20px;
}

#loadMoreBtn {
    padding: 6px 18px;
    font-size: 14px;
    background-color: white;
    border: 1px solid #222;
    border-radius: 8px;
    cursor: pointer;
}
</style>
<script type="text/javascript">
// 페이지 로딩 시 하트 상태 반영
document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".locLoveBtn");

    buttons.forEach(button => {
        const locId = button.dataset.locId;
        const locationEnum = button.dataset.locationEnum;
        const img = button.querySelector(".locLoveImg");

        fetch('${pageContext.request.contextPath}/love/check.do?locId=' + encodeURIComponent(locId) + '&locationEnum=' + encodeURIComponent(locationEnum))
            .then(response => response.json())
            .then(data => {
                const loved = data.loved === true;
                button.dataset.loved = loved;
                img.src = loved
                    ? '${pageContext.request.contextPath}/resources/images/loc/loc-love-filled.png'
                    : '${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png';
            })
            .catch(err => console.error("찜 상태 체크 실패:", err));
    });
});

// 버튼 클릭 시 작동 (create.do & delete.do)
function toggleLove(button) {
    const userId = '${sessionScope.loginUser.userId}';
    if (!userId) {
        alert("로그인이 필요합니다.");
        return;
    }

    const loved = button.dataset.loved === 'true';
    const locId = button.dataset.locId;
    const locationEnum = button.dataset.locationEnum;
    const img = button.querySelector('.locLoveImg');

    const url = loved
        ? '${pageContext.request.contextPath}/love/delete.do?locId=' + encodeURIComponent(locId) + '&locationEnum=' + encodeURIComponent(locationEnum)
        : '${pageContext.request.contextPath}/love/create.do?locId=' + encodeURIComponent(locId) + '&locationEnum=' + encodeURIComponent(locationEnum);

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
            console.warn("찜 요청 실패", data);
        }
    })
    .catch(err => {
        console.error("통신 오류:", err);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const loveCards = document.querySelectorAll(".loveCard");
    const loadMoreBtn = document.getElementById("loadMoreBtn");

    const batchSize = 10;
    let currentIndex = 10;

    loadMoreBtn.addEventListener("click", function () {
        for (let i = currentIndex; i < currentIndex + batchSize && i < loveCards.length; i++) {
            loveCards[i].style.display = "flex";
        }
        currentIndex += batchSize;

        if (currentIndex >= loveCards.length) {
            loadMoreBtn.style.display = "none";
        }
    });

    // 처음부터 loveCards가 10개 이하일 경우 버튼 숨김
    if (loveCards.length <= 10) {
        loadMoreBtn.style.display = "none";
    }
});
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="lovePageTitle">
    <h1 class="loveTitle">My 찜</h1>
    <div class="underline"><hr></div>
</div><br>

<div class="loveContentWrapper">
    <div id="loveList" class="planLocList">
      <!-- 여행지 출력 (ACCO / REST / ATTR 구분) -->
    <c:if test="${not empty location}">
     <c:forEach var="item" items="${location}" varStatus="status">
        <div class="loveCard" style="display: ${status.index < 10 ? 'flex' : 'none'};">
          <div class="loveCard-left">
            	<img src="${item.imgPath}" style="border:none; width:100px; height:100px;">
          </div>
          <div class="loveCard-middle">
            <a href="${pageContext.request.contextPath}/loc/select.do?locationEnum=${item.locationEnum }&locId=${item.locId}">
            	<p class="loveTitle">${item.placeName}</p>
            </a>
          </div>
          <div class="loveCard-right">
            <button class="locLoveBtn"
                    data-loved="false"
                    data-loc-id="${item.locId}"
                    data-location-enum="${item.locationEnum}"
                    onclick="toggleLove(this)">
              <img class="locLoveImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png">
            </button>
          </div>
        </div>
      </c:forEach>
    </c:if>
        
      <c:if test="${not empty log}">
         <c:forEach var="item" items="${log}" varStatus="status">
            <div class="loveCard">
	            <div class="loveCard-left">
	                <img src="${item.imagePath}" style="border:none; width:100px; height:100px;">
	            </div>
	            <div class="loveCard-middle">
	                <p class="loveTitle">${item.logTitle}</p>
	            </div>
	            <div class="loveCard-right">
		            <button class="logLoveBtn"  data-loved="false" data-loc-id="${item.logId}"
        						data-location-enum="LOG" onclick="toggleLove(this)">
		    			<img class="logLoveImg" src="${pageContext.request.contextPath}/resources/images/loc/loc-love-black.png">
		 			</button>
	            </div>
        	</div>
         </c:forEach>
  	   </c:if> 

	   <c:if test="${empty location && empty log}">
	   		<div class="loveCard">
	        	<p>찜한 항목이 없습니다.</p>
	        </div>
	   </c:if>
    </div>
</div>


<!-- 더보기 버튼 -->
<div class="moreBtnWrapper">
    <button id="loadMoreBtn">더보기</button>
</div>


<br>


<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>