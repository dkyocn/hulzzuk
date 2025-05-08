<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/review/myReview.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

<%-- 요청 url 을 따로 지정 --%>
<%-- <c:url var="reviewDel" value="delete.do">
	<c:param name="no" value="${ requestScope.review.reviewId }" />
</c:url> --%>

<script type="text/javascript">
const reviewManager = {
    selectReviewIds: [],
    
    addReview(reviewId) {
        if (!this.selectReviewIds.includes(reviewId)) {
            this.selectReviewIds.push(reviewId);
        }
        console.log('선택된 리뷰', this.selectReviewIds);
    },
    
    removeReview(reviewId) {
        this.selectReviewIds = this.selectReviewIds.filter(review => review !== reviewId);
        console.log('선택된 리뷰', this.selectReviewIds);
    },
    
    getReviewIds() {
        return this.selectReviewIds;
    }
};


function openDeletePopup(url) {
    window.open(url, 'deletePopup', 'width=350,height=250');
}

function clickReview(reviewId) {
    const isAlreadySelected = reviewManager.selectReviewIds.includes(reviewId);
    if (isAlreadySelected) {
        reviewManager.removeReview(reviewId);
    } else {
        reviewManager.addReview(reviewId);
    }
}

function goToDelete() {
    if (reviewManager.getReviewIds().length === 0) {
        alert('삭제할 항목을 추가해주세요.');
        return;
    }
    
    document.getElementById('selectReviewIds').value = reviewManager.getReviewIds().join(',');
    document.getElementById('message').value = "삭제에 동의한 리뷰가<br> 삭제됩니다.<br> 정말 삭제하시겠습니까?";
    document.getElementById('width').value = 350;
    document.getElementById('height').value = 300;
    
    console.log(reviewManager.getReviewIds().join(','));
    
    const form = document.getElementById('reviewForm');
    const url = form.action;
    openDeletePopup(url + '?' + new URLSearchParams(new FormData(form)).toString());
}


</script>
</head>

<body>
<c:import url="/WEB-INF/views/common/header.jsp" />

<div class="review-container">
    <div class="review-header">
        <h2>My 리뷰</h2>
        <hr><hr>
        <button type="button" class="select-all-btn" id="selectAllBtn">전체선택</button>
    </div>
<form id="reviewForm" action="${pageContext.request.contextPath}/review/moveDelete.do" method="get">
<input type="hidden" name="reviewIds"  id="selectReviewIds">
<input type="hidden" name="message" id="message">
<input type="hidden" name="height" id="height">
<input type="hidden"  name="width" id="width"> 
    <c:forEach var="review" items="${reviewList}">
    <div class="review-item">
        <input type="checkbox"  name="selectedReviews" value="${review.reviewId}" class="review-checkbox"  onclick="clickReview(${review.reviewId})">
        <div class="review-info">
            <span>작성자: <c:out value="${user.userNick}" /></span>
            <span class="review-date">작성일: <c:out value="${review.createAt}" /></span>
        </div>
        <div class="review-content">
            <p><c:out value="${review.userReviewText}" /></p>
        </div>
    </div>
</c:forEach>
<button type="button" class="delete-btn" onclick="goToDelete()">삭제</button>
</form>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const selectAllBtn = document.getElementById("selectAllBtn");
    if (!selectAllBtn) {
        console.error("'selectAllBtn' ID를 가진 요소를 찾을 수 없습니다.");
        return;
    }

    selectAllBtn.addEventListener("click", function() {
        var checkboxes = document.querySelectorAll("input[name='selectedReviews']");
        var isChecked = this.innerText === "전체선택";
        
        checkboxes.forEach(checkbox => {
            checkbox.checked = isChecked;
            const reviewId = parseInt(checkbox.value);
            if (isChecked) {
                reviewManager.addReview(reviewId); // 선택 시 추가
            } else {
                reviewManager.removeReview(reviewId); // 해제 시 제거
            }
        });

        console.log('선택된 리뷰', reviewManager.getReviewIds());
        this.innerText = isChecked ? "선택 해제" : "전체선택";
    });
});
</script>
    
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>