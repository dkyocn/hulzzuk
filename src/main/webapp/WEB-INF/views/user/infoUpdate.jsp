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
/* 기존 스타일 유지 + 아래 스타일 추가 */

#myphoto1, #myphoto2 {
    position: relative;
    display: inline-block;
}

#photo1, #photoDefault {
    width: 250px;
    height: 250px;
    border: 1px solid #585858;
    border-radius: 50%;
    object-fit: cover;
}

/* 겹쳐진 버튼 */
#profileEditBtn {
    position: absolute;
    bottom: 10px;
    right: 10px;
    width: 50px;
    height: 50px;
    border: none;
    background: none;
    cursor: pointer;
}

#profileEditBtn img {
    width: 100%;
    height: 100%;
}


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
					<button id="profileEditBtn" type="button" onclick="document.getElementById('photoFile').click();">
						<img src="/hulzzuk/resources/images/common/edit.png" alt="변경"></button>
				</div>		
			</c:if>
			
			<c:if test="${ empty requestScope.user.userProfile }">
				<div id="myphoto2">
					<img src="/hulzzuk/resources/images/logo2.png" id="photoDefault">
					<button id="profileEditBtn" type="button" onclick="document.getElementById('photoFile').click();">
						<img src="/hulzzuk/resources/images/common/edit.png" alt="변경"></button>
				</div>
			</c:if><br>
			${ requestScope.ofile }<br>
			<!-- 숨김 파일 선택 input (공통) -->
			<input type="file" id="photofile" name="photofile" style="display: none;" accept="image/*">
			</td>
		</tr>
		<br><br>
		<tr>
			<td id="nickname"><input text-align="center" type="text"
				name="userNick" id="userNick"
				value="${ requestScope.user.userNick }" readonly>
				<input type="submit" value="수정하기"> &nbsp;</td><br>
		</tr>
	</form>
	<br>

	<table id="infoDetail" align="center" border="1px" width="1000"
		cellspacing="5" cellpadding="0">
		<br>
		<tr>
			<th width="120">아이디(이메일)</th>
			<%-- input 태그의 name 속성의 이름은 member.dto.Member 클래스의 property 명과 같게 함 --%>
			<td><input type="email" name="userId" id="userId" class="datailInput"
				value="${ requestScope.user.userId }" readonly></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>&nbsp; 버튼을 누르면 비밀번호 재설정 페이지로 이동합니다. &nbsp;
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/user/moveSendMail.do'">재설정</button></td>
		</tr>

		<tr>
			<th>성별</th>
			<td><c:if test="${ requestScope.user.gender eq 'M' }">
					<input type="radio" name="gender" class="datailInput" value="M" checked> 남자 &nbsp;
				<input type="radio" name="gender" class="datailInput" value="F"> 여자 
			</c:if> <c:if test="${ requestScope.user.gender eq 'F' }">
					<input type="radio" name="gender" class="datailInput" value="M"> 남자 &nbsp;
				<input type="radio" name="gender" class="datailInput" value="F" checked> 여자 
			</c:if>
				<input type="submit" value="수정"> &nbsp; </td>
		</tr>
		<tr>
			<th>나이</th>
			<td><input type="text" name="userAge" class="datailInput" min="19" max="100"
				value="${ user.age }"><input type="submit" value="수정"> &nbsp; </td>
			
		</tr>
		<%-- <tr><th colspan="2">
		<input type="submit" value="수정하기"> &nbsp; 
		<input type="reset" value="수정취소"> &nbsp;
	</th></tr>	 --%>
	</table>
	

	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>