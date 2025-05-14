<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<style type="text/css">
table#infoProfile {
	width: 700px;
	text-align: center;
	border: 1px solid #585858;
	margin-top: 0;
	margin: 0 auto;
}

table#infoProfile img {
	width: 250px;
	height: 250px;
	border: 1px solid #585858;
	border-radius: 50%;
	object-fit: cover;
	margin-top: 20px;
	margin-bottom: 20px;
}

table#infoProfile td#nickname {
	text-align: center;
	margin-bottom: 30px;
}

table#infoProfile td#nickname input {
	font-size: 16px;
	border: none;
	background-color: transparent;
	text-align: center;
	font-weight: bold;
	width: 100%;
	margin-bottom: 30px;
}

div#infoDetail{
	text-align: center;
	margin: 0 auto;
	width: 700px;
}

table#infoTable {
	width: 100%;
	border: 1px solid #585858;
	border-collapse: collapse;
	margin: 0 auto;
}

table#infoTable input.datailInput{
	border: none;
	outline: none;
	width: 93%; 
	height: 100%;
	padding: 0;
	margin: 0;
	font-size: 16px;
	border: none;
	outline: none;
	box-sizing: border-box;
}


table#infoTable th{
	width: 150px;
	border: 1px solid #585858;
	padding: 10px 25px;
	text-align: center;
}

table#infoTable td{
	width: 400px;
	height: 43px;
	border: 1px solid #585858;
	padding: 0;
	padding-left: 8px;
	text-align: left;
}

form#deleteConfirm,
form#logout {
	text-align: center;
	margin-top: 15px;
}

div#topBtnArea {
	width: 700px; /* infoProfile 및 btnArea와 동일 너비 */
	display: flex;
	justify-content: flex-end;
	margin: 20px auto 0;
}

.submitId {
	background: none;
	border: none;
	color: #919296;
	text-decoration: underline;
	cursor: pointer;
	font-size: 14px;
}

.submitId:hover, .subminId:hover {
	color: #6d6d6d;
}
</style>
<script type="text/javascript">
window.onload = function(){
	const photofile = document.getElementById('photofile');

    photofile.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (!file || !file.type.startsWith('image/')) {
            alert('이미지 파일을 선택해주세요.');
            return;
        }

        const reader = new FileReader();
        reader.onload = function (e) {
            const photo1 = document.getElementById('photo1');
            const photoDefault = document.getElementById('photoDefault');
            const targetImg = photo1 || photoDefault;

            if (targetImg) {
                targetImg.setAttribute('src', e.target.result);
                targetImg.setAttribute('data-file', file.name);
            }
        };
        reader.readAsDataURL(file);
    });
};
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div id="topBtnArea">
	<form name="moveInfoUpdate" id="moveInfoUpdate" action="${pageContext.request.contextPath}/user/#">
	    <input type="hidden" name="userId" value="${sessionScope.authUserId}" />
	   	<button type="submit" class="submitId">수정완료</button>
	</form>
</div>
<br>
<form id="info" enctype="multipart/form-data">
	<table id="infoProfile">
		<tr><td>
			<c:if test="${ !empty requestScope.user.userProfile }">
				<div id="myphoto1">
					<img src="${ requestScope.user.userProfile }" id="photo1">
					<button id="profileEditBtn" type="button" onclick="document.getElementById('photoFile').click();">
						<img src="/hulzzuk/resources/images/common/edit.png" alt="변경"></button>
				</div>		
			</c:if>
			
			<c:if test="${ empty requestScope.user.userProfile }">
				<div id="myphoto2">
					<img src="${pageContext.request.contextPath}/resources/images/logo2.png" id="photoDefault">
					<button id="profileEditBtn" type="button" onclick="document.getElementById('photoFile').click();">
					<img src="/hulzzuk/resources/images/common/edit.png" alt="변경"></button>
				</div>
			</c:if>
			${ requestScope.ofile }<br>
			<!-- 숨김 파일 선택 input (공통) -->
			<input type="file" id="photofile" name="photofile" style="display: none;" accept="image/*"></td></tr>
		<tr><td id="nickname">
			<input text-align="center" type="text" name="userNick" id="userNick" value="${ requestScope.user.userNick }"></td></tr>
	</table>
</form>
<br>
<div id="infoDetail">
	<table id="infoTable">
		<tr><th>아이디(이메일)</th>
			<td><input type="email" name="userId" id="userId" class="datailInput"
				value="${ requestScope.user.userId }" readonly></td></tr>
		<tr><th>비밀번호</th><td>&nbsp; 버튼을 누르면 비밀번호 재설정 페이지로 이동합니다. &nbsp;
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/user/moveSendMail.do'">재설정</button></td></tr>
		<tr><th>성별</th>
			<td><c:if test="${ requestScope.user.gender eq 'M' }">
				<input type="radio" name="gender" class="datailInput" value="M" checked> 남자 &nbsp;
			<input type="radio" name="gender" class="datailInput" value="F"> 여자 
		</c:if> <c:if test="${ requestScope.user.gender eq 'F' }">
				<input type="radio" name="gender" class="datailInput" value="M"> 남자 &nbsp;
			<input type="radio" name="gender" class="datailInput" value="F" checked> 여자 
		</c:if></td></tr>
		<tr><th>생년월일</th>
			<td><input type="date" name="userAge" class="datailInput" min="19" max="100" value="${ user.age }"></td></tr>
	</table>
</div>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>