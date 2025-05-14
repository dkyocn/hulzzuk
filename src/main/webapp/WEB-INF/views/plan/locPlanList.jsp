<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/plan/locPlanList.css">
    
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
     <script type="text/javascript">
	     function plan(planId, day) {
	    	 
	    	 console.log('플랜데이'+planId+' : '+day)
	    	  const id = opener.locId;
	    	  const locEnum = opener.locEnum;
	    	  
	     	// AJAX로 컨트롤러 호출
	    	 fetch('${pageContext.request.contextPath}/plan/addLocation.do', {
	 			method: 'POST',
	 			headers: { 'Content-Type': 'application/json' },
	 			body: JSON.stringify({
	 				planId: planId,
	 				locId: id,
	 				locEnum: locEnum,
	 				planDay: day
	 			})
	 		})
	 		.then(response => response.json())
	 		.then(data => {
	 			if (data.successYN) {
	 				opener.postMessage({ id: id, locEnum: locEnum }, opener.location.origin);
	 				window.close();
	 			} else {
	 				console.error("위치 저장 실패");
	 			}
	 		})
	 		.catch(error => console.error("오류:", error));
	       }
 	</script>
 </head>
 
<body>
	<div class="planWrapper">
    <div class="planPageTitle">
        	<h1 class="planTitle">여행 일정</h1>
    </div>
    
    <hr class="planTitleHr">

	<c:forEach items="${ planList }" var="planList">
	    <div class="planList">
	        <div class="planContent">
	            <c:choose>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '부산' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/busan.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '강원' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gangwon.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '제주' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/jeju.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '서울' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/seoul.jpg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '충청' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/chungcheong.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '경기' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gyeonggi.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '인천' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/incheon.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '경상' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gyeongsang.jpeg" alt="">
	                </c:when>
	                <c:when test="${ fn:split(planList.planPlace, ',')[0] == '전라' }">
	                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/jeolla.jpeg" alt="">
	                </c:when>
	            </c:choose>
	            <div class="planContentTitle">
				    <a class="planName">${ planList.planTitle }</a> <!-- 제목 -->
				    <p class="planDate">${ planList.planStartDate } ~ ${ planList.planEndDate }</p> <!-- 날짜 -->
				</div>
				<div class="planDays">
				        <c:choose>
				            <c:when test="${ planList.planStartDate == planList.planEndDate }">
				                <button class="planDayBtn" onclick="plan('${planList.planId}', 1)">Day 1</button>
				            </c:when>
				            <c:otherwise>
				                <button class="planDayBtn" onclick="plan('${planList.planId}', 1)">Day 1</button>
				                <button class="planDayBtn" onclick="plan('${planList.planId}', 2)">Day 2</button>
				            </c:otherwise>
				        </c:choose>
				    </div>
	        </div>
	    </div>
	</c:forEach>
</div>
</body>
</html>