<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Hulzzuk Footer</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/footer.css">

  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>

<footer class="footer">
  <div class="footer-top">
 
   	<a href="${ pageContext.servletContext.contextPath }/loc/page.do?locationEnum=ACCO&page=1&sortEnum=LOVEDESC">숙소</a>
    <a href="${ pageContext.servletContext.contextPath }/loc/page.do?locationEnum=REST&page=1&sortEnum=LOVEDESC">맛집</a>
    <a href="${ pageContext.servletContext.contextPath }/loc/page.do?locationEnum=ATTR&page=1&sortEnum=LOVEDESC">즐길거리</a>
    <a href="${ pageContext.servletContext.contextPath }/log/page.do?page=1">여행로그</a>
    <a href="${ pageContext.servletContext.contextPath }/notice/page.do?page=1">공지사항</a>
    <a href="${ pageContext.servletContext.contextPath }/faq/page.do?page=1">FAQ</a>
    <a href="${ pageContext.servletContext.contextPath }/voc/page.do?vocEnum=ALL&page=1">고객의 소리</a>
    <c:if test="${ empty sessionScope.loginUser }">
	    <a href="${pageContext.request.contextPath}/user/loginSelect.do">My 여행로그</a>
	    <a href="${pageContext.request.contextPath}/user/loginSelect.do">My 일정</a>
	    <a href="${pageContext.request.contextPath}/user/loginSelect.do">My 찜</a>
	    <a href="${pageContext.request.contextPath}/user/loginSelect.do">My 리뷰</a>
    </c:if>
    <c:if test="${ !empty sessionScope.loginUser }">
	    <a href="${ pageContext.servletContext.contextPath }/log/myTripLog.do?">My 여행로그</a>
        <a href="${ pageContext.servletContext.contextPath }/plan/page.do?page=1">My 일정</a>
	    <a href="${pageContext.request.contextPath}/love/page.do">My 찜</a>
	    <a href="${ pageContext.servletContext.contextPath }/review/select.do?userId=${loginUser.userId}">My 리뷰</a>
    </c:if>
  </div>

  <div class="footer-divider"></div>

  <div class="footer-bottom">
    <div class="footer-section">
      <!-- strong 태그 : 굵은 글씨 나타냄 + 의미강조 / b 태그 : 그냥 굵게 표시함 -->
      <strong>(주)Hulzzuk</strong><br>
      주소: 서울특별시 서초구 서초대로77길 41, 4층 (서초동, 대동Ⅱ)<br>
      사업자등록번호: 421-25-25122 <br>
      통신판매업신고: 2024-성남수정-0912<br>
      관광사업 등록번호: 제2024-000024호<br>
      호스팅서비스 제공자: (주)Hulzzuk | 대표이사: grit
    </div>
    
  </div>
</footer>

</body>
</html>