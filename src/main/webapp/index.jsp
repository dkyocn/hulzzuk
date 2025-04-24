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


</head>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>

<script type="text/javascript">
function movePage(){
	//자바스크립트로 페이지 연결 이동 또는 서블릿 컨트롤러 연결 요청시에는
	//location 내장객체의 href 속성을 사용함 : 상대경로, 절대경로 둘 다 사용 가능함
	location.href = 'test.do';  //서버측으로 로그인 페이지 내보내기 요청 보냄
}

function sendMail(){
	//자바스크립트로 페이지 연결 이동 또는 서블릿 컨트롤러 연결 요청시에는
	//location 내장객체의 href 속성을 사용함 : 상대경로, 절대경로 둘 다 사용 가능함
	location.href = 'mail.do';  //서버측으로 로그인 페이지 내보내기 요청 보냄
}

</script> --%>

</head>

<body>

<c:import url="/WEB-INF/views/common/header.jsp"/>

 <jsp:forward page="main.do" />
<c:import url="/WEB-INF/views/common/footer.jsp" />

</body>
</html>