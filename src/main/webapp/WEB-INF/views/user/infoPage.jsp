<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>

<script type="text/javascript"
	src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap"
	rel="stylesheet">
<style type="text/css">
form#info {
	width: 1000px;
	text-align: center;
	border: 1px solid #585858;
	margin: 0 auto;
}

form#info img {
	width: 250px;
	height: 250px;
	border: 1px solid #585858;
	border-radius: 50%;
}

th {
	border: 0;
}

td {
	width: 500px;
}

form #info>td #nickname {
	border-width: none;
	outline: none;
	text-align: center;
}

table #infoDetail>th {
	border: none;
}
</style>
<script type="text/javascript">
	function validate() {
		//암호와 암호확인이 일치하는지 확인
		var pwdValue = $('#userPwd').val();
		var pwdValue2 = document.getElementById('userPwd2').value;
		console.log(pwdValue + ', ' + pwdValue2);

		if (pwdValue !== pwdValue2) {
			alert('암호와 암호확인이 일치하지 않습니다. 다시 입력하세요.');
			document.getElementById('userPwd').value = ''; //기록된 값 지우기
			$('#userPwd2').val(''); //기록된 값 지우기
			document.getElementById('userPwd').focus(); //입력커서 지정함
			return false; //전송 취소함
		}
		return true; //전송 보냄
	}
</script>
</head>
<body>
	<c:import url="/WEB-INF/views/common/header.jsp" />
	<form id="info" enctype="multipart/form-data">
		<tr>
			<th text-align="center"></th>
			<td><%-- <img src="${ requestScope.user.userProfile }"><input type="submit" value="사진변경">  --%>
			<c:if test="${ !empty requestScope.user.userProfile }">
			<div id="myphoto1">
				<img src="${ requestScope.user.userProfile }" id="photo1">
			</div> <br>
			${ requestScope.ofile } <br>
			변경할 사진 선택 : <input type="file" id="photofile" name="photofile">			
		 </c:if>
		 <c:if test="${ empty requestScope.user.userProfile }">
			<div id="myphoto2">
				<img src="${ requestScope.user.userProfile }" id="photo2">
			</div> <br>
			<input type="file" id="photofile" name="photofile">			
		 </c:if></td>
		</tr>
		<br>
		<br>
		<tr>
			<td id="nickname"><input text-align="center" type="text"
				name="userNick" id="userNick"
				value="${ requestScope.user.userNick }" readonly>
				<input type="submit" value="수정하기"> &nbsp;</td>
			<br>
		</tr>
	</form>
	<br>

	<table id="infoDetail" align="center" border="1px" width="1000"
		cellspacing="5" cellpadding="0">
		<br>
		<tr>
			<th width="120">아이디</th>
			<%-- input 태그의 name 속성의 이름은 member.dto.Member 클래스의 property 명과 같게 함 --%>
			<td><input type="text" name="userId" id="userId"
				value="${ requestScope.user.userId }" readonly></td>
		</tr>
		<tr>
			<th>암호</th>
			<td><input type="password" name="userPwd" id="userPwd"></td>
		</tr>
		<tr>
			<th>암호확인</th>
			<td><input type="password" id="userPwd2"><input type="submit" value="수정하기"> &nbsp; </td>
			
		</tr>

		<tr>
			<th>성별</th>
			<td><c:if test="${ requestScope.user.gender eq 'M' }">
					<input type="radio" name="gender" value="M" checked> 남자 &nbsp;
				<input type="radio" name="gender" value="F"> 여자 
			</c:if> <c:if test="${ requestScope.user.gender eq 'F' }">
					<input type="radio" name="gender" value="M"> 남자 &nbsp;
				<input type="radio" name="gender" value="F" checked> 여자 
			</c:if>
				<input type="submit" value="수정하기"> &nbsp; </td>
		</tr>
		<tr>
			<th>나이</th>
			<td><input type="text" name="userAge" min="19" max="100"
				value="${ user.age }"><input type="submit" value="수정하기"> &nbsp; </td>
			
		</tr>
		<%-- <tr><th colspan="2">
		<input type="submit" value="수정하기"> &nbsp; 
		<input type="reset" value="수정취소"> &nbsp;
		<a href="mdelete.do?userId=${ requestScope.member.userId }">탈퇴하기</a>
	</th></tr>	 --%>
	</table>
	<c:url var="mdel" value="mdelete.do">
		<c:param name="userId" value="${ requestScope.user.userId }"></c:param>
	</c:url>
	<a href="${ mdel }" style="left: 55%">탈퇴하기</a>
	<a href="${pageContext.request.contextPath}/user/logout.do" style="right: 55%">로그아웃</a>

	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>