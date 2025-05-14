<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>hulzzuk</title>
    <!-- css 적용 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/faq/faqList.css">
    <!-- font 적용 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

    <!-- include libraries(jQuery, bootstrap) -->
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="faq-container">
    <h2>FAQ</h2>

    <!-- 작성하기 버튼 -->
    <div class="write-button-wrapper">
        <c:if test="${sessionScope.loginUser.userId eq 'admin@gmail.com'}">
            <a href="${pageContext.request.contextPath}/faq/moveCreate.do" class="write-button">작성하기</a>
        </c:if>
    </div>

    <!-- FAQ 카드 목록 -->
    <div class="panel-group" id="faqAccordion" role="tablist" aria-multiselectable="true">
        <c:forEach var="faq" items="${faqList}" varStatus="status">
            <div class="faq-card">
                <div class="faq-header" role="tab" id="heading${faq.noticeId}">
                    <span>
                        Q. ${faq.title}
                    </span>
                    <div class="faqContent">
                        <c:if test="${sessionScope.loginUser.userId eq 'admin@gmail.com'}">
                            <span class="admin-buttons">
                                <a href="${pageContext.request.contextPath}/faq/moveUpdate.do?faqId=${faq.noticeId}" class="btn btn-sm btn-primary">수정</a>
                                <a href="${pageContext.request.contextPath}/faq/delete.do?faqId=${faq.noticeId}" class="btn btn-sm btn-danger" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                            </span>
                        </c:if>
                        <a data-toggle="collapse" data-parent="#faqAccordion" href="#collapse${faq.noticeId}" aria-expanded="true" aria-controls="collapse${faq.noticeId}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </div>

                </div>
                <div id="collapse${faq.noticeId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${faq.noticeId}">
                    <div class="faq-Answer">
                        A. ${faq.content}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%--<c:import url="/WEB-INF/views/common/pagination.jsp" />--%>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
