<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="fail" value="${requestScope.fail}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<script>
  window.onload = function() {
    document.getElementById('userid').focus();
  };
</script>
<style>
div.loginLogo{
text-align: center;
}

div.loginLogo img{
width: 500px;
}

div#loginForm{
text-align: center;
margin: 0 auto;
}

div#loginForm input.pos{
border: none;
outline: none;
width: 90%; 
height: 100%;
padding: 0;
margin: 0;
font-size: 16px;
border: none;
outline: none;
box-sizing: border-box; /* 패딩 포함해서 width 100% 유지 */
}

table#loginText{
border: 1px solid #585858;
border-collapse: collapse;	/* 테이블 선 겹치기 방지 */
margin: 0 auto;	
}

table#loginText th{
border: 1px solid #585858;	
padding: 10px 25px;
text-align: center;
}

table#loginText td{
width: 400px;
height: 43px;
border: 1px solid #585858;	
padding: 0;
text-align: center;
}

input#submitId{
width: 440px;
height: 43px;
background-color: lightgray;
border: 1px solid #585858;
cursor: pointer;
font-size: 16px;
}

div#loginOptions table{
text-align: center;
margin: 0 auto;
border: 1px solid #585858;
width: 440px;
height: 43px;
}

div#loginOptions table a{
padding: 10px;
cursor: pointer;
text-align: center;
font-size: 16px;
text-decoration-line: none;
color: inherit;
line-height: 43px; /* 세로 가운데 정렬 */
}

div#loginOptions table td.divider {
  width: 1px;
}

div#loginOptions table td.divider div {
  width: 1px;
  height: 35px; /* 수직선 길이 조정 */
  margin: auto;
  background-color: #585858;
}

div.errorMsg{
color: red;
font-size: 13px;
margin-top: 10px;
margin-bottom: 10px;
text-align: center;
line-height: 1.5;
}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="loginLogo">
	<br><br><br><br>
	<a href="${pageContext.request.contextPath}/"> <img
		src="${pageContext.request.contextPath}/resources/images/logo2.png"
		alt="logo" />
	</a>
</div>
<br>
<div id="loginForm">
	<form action="${pageContext.request.contextPath}/user/login.do" method="post">
		<table id="loginText">
			<tr><th>아이디(이메일)</th>
				<td><input type="text" id="userid" name="userId" class="pos"></td></tr>
			<tr><th>암&nbsp;        호</th>
				<td><input type="password" id="userpwd" name="userPwd" class="pos"></td></tr>
		</table>
		<br>
		
		<!-- 오류 메세지 표시 -->
		<c:if test="${not empty fail and fail eq 'N'}">
			<div class="errorMsg">
				아이디 또는 비밀번호가 잘못 되었습니다. <br>
				아이디와 비밀번호를 정확히 입력해 주세요.
			</div>
		</c:if>
		<br>
		
		<input type="submit" value="로그인" id="submitId">
		<br><br>
		<div id="loginOptions">
		<table><tr>
			<td><a href="${pageContext.request.contextPath}/user/moveSendMail.do">비밀번호 재설정</a></td>
			 <td class="divider">
      				 <div></div>
    			 </td>
			<td><a href="${pageContext.request.contextPath}/user/moveJoin.do">이메일로 회원가입</a></td>
		</tr></table>
		</div>
	</form>
</div>
</body>
</html>