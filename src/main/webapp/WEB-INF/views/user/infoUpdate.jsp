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
#infoProfile {
	display: flex;
	text-align: center;
	margin: 0 auto;
	width: 700px;
	border: 1px solid;
	flex-direction: column;
	align-items: center;
}

.profileImg {
	width: 250px;
	height: 250px;
	border: 1px solid #585858;
	border-radius: 50%;
	object-fit: cover;
	margin-top: 20px;
	margin-bottom: 20px;
	position: relative;
	background-size: 100% 100%;
	background-image: url("${pageContext.request.contextPath}/resources/images/logo2.png");
}

#profileEditBtn {
	left: 77%;
	top: 83%;
	background: none;
	position: absolute;
	border: none;
	cursor: pointer;
}
.modifiedImg {
	width: 32px;
	height: 32px;
}

#userNick {
	border: 1px solid;
	text-align: center;
	margin-bottom: 10px;
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

table#infoTable input.detailInput{
	border: none;
	outline: none;
	padding: 0;
	margin: 0;
	font-size: 16px;
	border: none;
	outline: none;
	box-sizing: border-box;
}

input.userId {
	border: none;
	outline: none;
	width: 100%;
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
		let uploadedFilePath = null; // 업로드된 파일 경로 저장

		window.onload = function() {
			const photofile = document.getElementById('photofile');

			photofile.addEventListener('change', function(event) {
				const file = event.target.files[0];
				if (!file || !file.type.startsWith('image/')) {
					alert('이미지 파일을 선택해주세요.');
					return;
				}

				// 파일을 서버로 업로드
				const formData = new FormData();
				formData.append('mfile', file); // Controller의 @RequestParam("mfile")와 일치
				formData.append('userId', document.getElementById('userId').value);

				fetch('${pageContext.request.contextPath}/user/profileUpload.do', {
					method: 'POST',
					body: formData
				})
						.then(response => {
							if (!response.ok) {
								throw new Error('Network response was not ok: ' + response.statusText);
							}
							return response.json();
						})
						.then(data => {
							console.log('Server response:', data);
							if (data.successYN) {
								uploadedFilePath = data.filePath; // 웹 경로 사용
								console.log('Uploaded file path:', uploadedFilePath);

								const photo1 = document.getElementById('photo1');
								const photoDefault = document.getElementById('photoDefault');

								if (photo1) {
									photo1.setAttribute('src', ""+uploadedFilePath);
									photo1.setAttribute('data-file', file.name);
								} else if (photoDefault) {
									photoDefault.style.backgroundImage = `url('`+uploadedFilePath+`')`;
									photoDefault.setAttribute('data-file', file.name);
									console.log('Updated background-image:', photoDefault.style.backgroundImage);
								}
							} else {
								alert('이미지 업로드에 실패했습니다: ' + (data.message || ''));
							}
						})
						.catch(error => {
							console.error('Error:', error);
							alert('이미지 업로드 중 오류가 발생했습니다. 콘솔을 확인하세요.');
						});
			});

			document.getElementById('moveInfoUpdate').addEventListener('submit', function(event) {
				event.preventDefault();

				const data = {
					userProfile: uploadedFilePath || '',
					userNick: document.getElementById('userNick').value,
					userId: document.getElementById('userId').value,
					gender: document.querySelector('input[name="gender"]:checked')?.value || '',
					userAge: document.querySelector('input[name="userAge"]').value
				};

				fetch('${pageContext.request.contextPath}/user/updateProfile.do', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify(data)
				})
						.then(response => response.json())
						.then(data => {
							if (data.success) {
								alert('프로필이 성공적으로 업데이트되었습니다.');
								window.location.reload();
							} else {
								alert('업데이트에 실패했습니다.');
							}
						})
						.catch(error => {
							console.error('Error:', error);
							alert('오류가 발생했습니다.');
						});
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
	<div id="infoProfile">
		<c:if test="${ !empty requestScope.user.userProfile }">
			<div id="myphoto1">
				<img src="${ requestScope.user.userProfile }" id="photo1" class="profileImg">
				<button id="profileEditBtn" type="button" onclick="document.getElementById('photofile').click();">
					<img src="${pageContext.request.contextPath}/resources/images/common/edit.png" alt="변경"></button>
			</div>
		</c:if>

		<c:if test="${ empty requestScope.user.userProfile }">
			<div class="profileImg" id="photoDefault">
				<button id="profileEditBtn" type="button" onclick="document.getElementById('photofile').click();">
					<img class="modifiedImg" src="${pageContext.request.contextPath}/resources/images/common/edit.png" alt="변경"></button>
			</div>
		</c:if>
		<div>
			<!-- 숨김 파일 선택 input (공통) -->
			<input type="file" id="photofile" name="photofile" style="display: none;" accept="image/*">
			<input text-align="center" type="text" name="userNick" id="userNick" value="${ requestScope.user.userNick }">
		</div>
	</div>
</form>
<br>
<div id="infoDetail">
	<table id="infoTable">
		<tr><th>아이디(이메일)</th>
			<td><input type="email" name="userId" id="userId" class="userId"
					   value="${ requestScope.user.userId }" readonly></td></tr>
		<tr><th>비밀번호</th><td> 버튼을 누르면 비밀번호 재설정 페이지로 이동합니다.
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/user/moveSendMail.do'">재설정</button></td></tr>
		<tr><th>성별</th>
			<td>
				<input type="radio" name="gender" class="detailInput" value="M" ${requestScope.user.gender eq 'M' ? 'checked' : ''}> 남자
				<input type="radio" name="gender" class="detailInput" value="F" ${requestScope.user.gender eq 'F' ? 'checked' : ''}> 여자
			</td></tr>
		<tr><th>생년월일</th>
			<td><input type="date" name="userAge" class="detailInput" value="${ user.userAge }"></td></tr>
	</table>
</div>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>