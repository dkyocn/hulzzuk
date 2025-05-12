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
form#info {
	width: 800px;
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

.datailInput {
	border-width: none;
	outline: none;
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
	<form id="info" enctype="multipart/form-data">
		<tr>
			<th text-align="center"></th><td>
			<c:if test="${ !empty requestScope.user.userProfile }">
				<div id="myphoto1">
					<img src="${ requestScope.user.userProfile }" id="photo1">
				</div>		
			</c:if>
			
			<c:if test="${ empty requestScope.user.userProfile }">
				<div id="myphoto2">
					<img src="${pageContext.request.contextPath}/resources/images/logo2.png" id="photoDefault">
				</div>
			</c:if><br>
			${ requestScope.ofile }<br>
			</td>
		</tr>
		<br><br>
		<tr>
			<td id="nickname"><input text-align="center" type="text"
				name="userNick" id="userNick"
				value="${ requestScope.user.userNick }" readonly></td><br>
		</tr>
	</form>
	<br>

	<table id="infoDetail" align="center" border="1px" width="1000"
		cellspacing="5" cellpadding="0">
		<br>
		<tr><th width="120">아이디(이메일)</th>
		<%-- input 태그의 name 속성의 이름은 member.dto.Member 클래스의 property 명과 같게 함 --%>
			<td><input type="email" name="userId" id="userId" class="datailInput"
				value="${ requestScope.user.userId }" readonly></td></tr>
		<tr><th>비밀번호</th>
			<td>****</td></tr>
		<tr><th>성별</th>
			<td><c:if test="${ requestScope.user.gender eq 'M' }">
					<input type="radio" name="gender" class="datailInput" value="M" checked> 남자 &nbsp;
				<input type="radio" name="gender" class="datailInput" value="F"> 여자 
			</c:if> <c:if test="${ requestScope.user.gender eq 'F' }">
					<input type="radio" name="gender" class="datailInput" value="M"> 남자 &nbsp;
				<input type="radio" name="gender" class="datailInput" value="F" checked> 여자 
			</c:if></td></tr>
		<tr><th>나이</th>
			<td><input type="text" name="userAge" class="datailInput" min="19" max="100" value="${ user.age }"></td></tr>
		<%-- <tr><th colspan="2">
		<input type="submit" value="수정하기"> &nbsp; 
		<input type="reset" value="수정취소"> &nbsp;
	</th></tr>	 --%>
	</table>
	
    <form name="deleteConfirm" id="deleteConfirm" action="${pageContext.request.contextPath}/user/deleteGuide.do" method="post">
	    <input type="hidden" name="userId" value="${sessionScope.authUserId}" />
    	<button type="submit" class="submitId">탈퇴하기</button>
    </form>
	<form name="logout" id="logout" action="${pageContext.request.contextPath}/user/logout.do" method="post">
    	<button type="submit" class="subminId">로그아웃</button>
    </form>

	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>