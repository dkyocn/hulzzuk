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

        function openDeletePopup(url) {
            // 팝업 창 열기 (가로 400px, 세로 300px)
            window.open(url, 'deletePopup', 'width=350,height=250');
        }
    </script>
    <style>
        .delete {
            background-image: url("${pageContext.request.contextPath}/resources/images/common/delete.png");
        }

        .edit {
            background-image: url("${pageContext.request.contextPath}/resources/images/common/edit.png");
        }

        .delete:hover {
            background-image: url("${pageContext.request.contextPath}/resources/images/common/delete-orange.png");
        }

        .edit:hover {
            background-image: url("${pageContext.request.contextPath}/resources/images/common/edit-orange.png");
        }

    </style>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div>
    <div class="planPageTitle">
        <h1 class="planTitle">여행 일정</h1>
        <button class="planCreateBtn" onclick="location.href='${ pageContext.servletContext.contextPath }/plan/moveCreate.do';">
            <img class="planCreateImg" src="${pageContext.request.contextPath}/resources/images/plan/add-button-black.png">
            <span class="planCreateText">새로운 일정</span> </button>
    </div>
    <hr class="planTitleHr">
</div>
<c:forEach items="${ requestScope.list }" var="plan">
    <div class="planList">
        <div class="planContent">
            <c:choose>
                <c:when test="${ plan.planPlace == '부산' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/busan.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '강원' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gangwon.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '제주' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/jeju.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '서울' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/seoul.jpg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '충청' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/chungcheong.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '경기' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gyeonggi.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '인천' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/incheon.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '경상' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/gyeongsang.jpeg" alt="">
                </c:when>
                <c:when test="${ plan.planPlace == '전라' }">
                    <img class="planImg" src="${pageContext.request.contextPath}/resources/images/plan/jeolla.jpeg" alt="">
                </c:when>
            </c:choose>
            <div class="planContentTitle">
                <c:url var="bd" value="select.do">
                    <c:param name="planId" value="${ plan.planId }" />
                    <c:param name="page" value="${ nowpage }" />
                </c:url>
                <a class="planName" href="${ bd }">${ plan.planTitle }</a>
                <p class="planDate">${ plan.planStartDate } ~ ${ plan.planEndDate }</p>
            </div>
        </div>
        <div class="deleteEdit">
            <c:url var="delete" value="moveDelete.do">
                <c:param name="planId" value="${plan.planId}" />
                <c:param name="message" value="삭제에 동의한 장소 및 체크리스트가<br> 삭제됩니다.<br> 정말 삭제하시겠습니까?" />
                <c:param name="actionUrl" value="${pageContext.servletContext.contextPath}/plan/delete.do?planId=${plan.planId}" />
                <!-- 크기 파라미터 추가 -->
                <c:param name="width" value="350" />
                <c:param name="height" value="300" />
            </c:url>
            <div class="delete" onclick="openDeletePopup('${delete}')"></div>
            <c:url var="edit" value="/plan/moveUpdate.do">
                <c:param name="planId" value="${ plan.planId }" />
                <c:param name="page" value="${ nowpage }" />
            </c:url>
            <div class="edit" onclick="window.location.href='${edit}'"></div>
        </div>
    </div>
</c:forEach>
<c:import url="/WEB-INF/views/common/pagination.jsp" />
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
