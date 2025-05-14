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
div.planPageTitle {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    padding-left: 4%;
    padding-right: 3%;
}

h1.planTitle {
    margin-bottom: 0;
}

hr.planTitleHr {
    border-width: 2px 0 0 0;
    border-color: black;
    margin-left: 2%;
    margin-right: 2%;
    margin-bottom: 1.5%;
}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div>
    <div class="lovePageTitle">
        <h1 class="loveTitle">My 찜</h1>
    </div>
    <hr class="loveTitleHr">
</div>
<div class="love">
    <div class="loveList">
        <div class="trip"><p class="dayTitle">여행지</p>
            <p class="planDate">
            <!-- logId가 null인 것 -->
                <fmt:formatDate value="${loveVO.accoId}" pattern="M.d" var="tripLove"/>
                (${startDate})
            </p></div>
        <c:if test="${planVO.planStartDate != planVO.planEndDate}">
            <div class="tripLog"><p class="dayTitle">Day2</p><p class="planDate">
                <fmt:formatDate value="${loveVO.logId}" pattern="M.d" var="logLove"/>
                (${endDate})</p></div>
        </c:if>
    </div>
</div>
<div class="planLocList">
    <div class="subDiv">
        <button class="addLocBtn"><img class="locImg" src="${pageContext.request.contextPath}/resources/images/common/add-button-black.png" alt="" ><span class="locText">장소 추가</span></button>
    </div>
</div>
<form id="saveLocationsForm" action="${pageContext.request.contextPath}/plan/page.do?page=1">
    <div class="saveDiv">
        <button class="saveBtn" type="submit" id="saveBtn">완료</button>
    </div>
</form>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>