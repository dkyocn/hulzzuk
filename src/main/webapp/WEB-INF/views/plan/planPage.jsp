<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 4. 24.
  Time: 오후 5:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="nowpage" value="1" />
<c:if test="${ !empty requestScope.paging.currentPage }">
    <c:set var="nowpage" value="${ requestScope.paging.currentPage }" />
</c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>hulzzuk</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/plan/planPage.css">
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function () {
            const planCreateBtn = document.querySelector(".planCreateBtn");
            const planCreateImg = planCreateBtn.querySelector(".planCreateImg");
            const planCreateText = planCreateBtn.querySelector(".planCreateText");

            // 기본 상태 설정
            const defaultImgSrc = "${pageContext.request.contextPath}/resources/images/plan/add-button-black.png";
            const hoverImgSrc = "${pageContext.request.contextPath}/resources/images/plan/add-button-orange.png";
            const defaultTextColor = "#000000";
            const hoverTextColor = "#E96A18";

            // 마우스를 올렸을 때
            planCreateBtn.addEventListener("mouseover", function () {
                planCreateImg.src = hoverImgSrc;
                planCreateText.style.color = hoverTextColor;
            });

            // 마우스가 떠났을 때
            planCreateBtn.addEventListener("mouseout", function () {
                planCreateImg.src = defaultImgSrc;
                planCreateText.style.color = defaultTextColor;
            });
        });
    </script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div>
    <div class="planPageTitle">
        <h1 class="planTitle">여행 일정</h1>
        <button class="planCreateBtn" onclick="location.href='${ pageContext.servletContext.contextPath }/blist.do?page=1';">
            <img class="planCreateImg" src="${pageContext.request.contextPath}/resources/images/plan/add-button-black.png">
            <span class="planCreateText">새로운 일정</span> </button>
    </div>
    <hr class="planTitleHr">
</div>
<c:forEach items="${ requestScope.list }" var="plan">
    <div class="planList">
        <div class="planContent">
            <img class="planImg" src="${pageContext.request.contextPath}/resources/images/pic03.jpg" alt="">
            <div class="planContentTitle">
                <c:url var="bd" value="select.do">
                    <c:param name="bnum" value="${ plan.planId }" />
                    <c:param name="page" value="${ nowpage }" />
                </c:url>
                <a class="planName" href="${ bd }">${ plan.planTitle }</a>
                <p class="planDate">${ plan.planStartDate } ~ ${ plan.planEndDate }</p>
            </div>
        </div>
        <img class="planMenu" src="${pageContext.request.contextPath}/resources/images/plan/menu.png">
    </div>
</c:forEach>
<%-- 조회된 게시글 목록 출력 --%>
<%--<table align="center" width="650" border="1" cellspacing="0" cellpadding="0">--%>
<%--    <tr>--%>
<%--        <th>번호</th>--%>
<%--        <th>제목</th>--%>
<%--        <th>장소</th>--%>
<%--        <th>시작날짜</th>--%>
<%--        <th>종료날짜</th>--%>
<%--    </tr>--%>
<%--    <c:forEach items="${ requestScope.list }" var="plan">--%>
<%--        <tr align="center">--%>
<%--            <td>${ plan.planId }</td>--%>
<%--            <td align="left">--%>
<%--                <c:url var="bd" value="select.do">--%>
<%--                    <c:param name="bnum" value="${ plan.planId }" />--%>
<%--                    <c:param name="page" value="${ nowpage }" />--%>
<%--                </c:url>--%>
<%--                <a href="${ bd }">${ plan.planTitle }</a>--%>
<%--            </td>--%>
<%--            <td>${ plan.planPlace }</td>--%>
<%--            <td>${ plan.planStartDate }</td>--%>
<%--            <td>${ plan.planEndDate }</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>
<c:import url="/WEB-INF/views/common/pagination.jsp" />
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
