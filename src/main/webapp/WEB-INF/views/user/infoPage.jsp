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
	height: 43px;
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

div#btnArea {
	width: 700px;
	display: flex;
	justify-content: space-between;
	margin: 15px auto 30px;
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

// 탈퇴하기 버튼 클릭 시 호출
window.onload = function () {
    const urlParams = new URLSearchParams(window.location.search);
    const fail = urlParams.get("fail");

    if (fail === "Y") {
        alert("로그인 세션이 존재하지 않거나 사용자 정보가 일치하지 않습니다.");
    } else if (fail === "E") {
        alert("회원 탈퇴 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
};
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div id="topBtnArea">
	<form name="moveInfoUpdate" id="moveInfoUpdate" action="${pageContext.request.contextPath}/user/moveInfoUpdate.do">
	    <input type="hidden" name="userId" value="${sessionScope.authUserId}" />
	   	<button type="submit" class="submitId">수정하기</button>
	</form>
</div>
<br>
<form id="info" enctype="multipart/form-data">
	<table id="infoProfile">
		<tr><td>
			<c:if test="${ !empty requestScope.user.userProfile }">
				<div id="myphoto1">
					<img src="${ requestScope.user.userProfile }" id="photo1">
				</div>		
			</c:if>
			
			<c:if test="${ empty requestScope.user.userProfile }">
				<div id="myphoto2">
					<img src="${pageContext.request.contextPath}/resources/images/logo2.png" id="photoDefault">
				</div>
			</c:if>
			${ requestScope.ofile }</td></tr>
		<tr><td id="nickname">
			<input text-align="center" type="text" name="userNick" id="userNick" value="${ requestScope.user.userNick }" readonly></td></tr>
	</table>
</form>
<br>
<div id="infoDetail">
	<table id="infoTable">
		<tr><th>아이디(이메일)</th>
			<td><input type="email" name="userId" class="datailInput"
				value="${ requestScope.user.userId }" readonly></td></tr>
		<tr><th>비밀번호</th><td>****</td></tr>
		<tr><th>성별</th><td>
			<c:if test="${ requestScope.user.gender eq 'M' }">남자</c:if> 
			<c:if test="${ requestScope.user.gender eq 'F' }">여자</c:if>
			</td></tr>
		<tr><th>생년월일</th>
			<td><input type="text" name="userAge" class="datailInput" min="19" max="100" value="${ user.age }"></td></tr>
	</table>
	<div id="btnArea">
	    <form name="deleteConfirm" id="deleteConfirm" action="${pageContext.request.contextPath}/user/deleteGuide.do" method="post">
		    <input type="hidden" name="userId" value="${sessionScope.authUserId}" />
	    	<button type="submit" class="submitId">탈퇴하기</button>
	    </form>
		<form name="logout" id="logout" action="${pageContext.request.contextPath}/user/logout.do" method="post">
	    	<button type="submit" class="submitId">로그아웃</button>
	    </form>
	</div>
</div>

	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>