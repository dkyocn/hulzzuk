<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행일정선택 </title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/SelectPlanId.css">
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />


<h2>리뷰를 작성할 여행을 선택하세요</h2>

<form action="${pageContext.request.contextPath}/log/selectPID.do" method="get">
	<div class="scroll-box">
	        <c:forEach var="plan" items="${planList}">
	            <div class="plan-item">
	                <label>
	                    <input type="radio" name="planId" value="${plan.planId}" required />
	                    ${plan.planTitle} 
	                </label>
	            </div>
	        </c:forEach>
	    </div>
	    <br/>
    <button type="submit">선택</button>
    
    	<br/><br/><br/>
</form>

<%-- <p>planList가 비었나? → ${empty planList}</p>
<p>planList 사이즈 확인: ${fn:length(planList)}</p>
 --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />

</body>
</html>