<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<style type="text/css">
/* 중앙 컨테이너 정렬 */
#deleteGuidePopUp {
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 60px 0;
}

/* 내부 내용 박스 스타일 */
#deleteGuideInfo {
	border: 1px solid black;
	padding: 30px 40px;
	width: 100%;
	max-width: 800px;
	background-color: white;
	box-sizing: border-box;
	font-size: 16px;
	line-height: 1.6;
	text-align: left;
}

/* 제목 스타일 */
#deleteGuideInfo h4 {
	margin-top: 20px;
	margin-bottom: 10px;
	font-weight: bold;
}

/* 텍스트박스 */
#deleteGuideInfo textarea {
	width: 100%;
	height: 100px;
	resize: none;
	box-sizing: border-box;
	padding: 10px;
	font-family: inherit;
	font-size: 14px;
	border: 1px solid #ccc;
	margin-top: 10px;
	margin-bottom: 20px;
}

/* 공통 정렬 기준: li와 라디오항목 모두 왼쪽 기준선 맞춤 */
#deleteGuideInfo ul,
#deleteGuideInfo .radio-group {
	margin-left: 20px; /* 들여쓰기 기준을 동일하게 */
	padding-left: 0;
}

/* li 스타일 통일 */
#deleteGuideInfo ul li {
	margin-bottom: 8px;  /* li 항목 간 간격 */
	list-style-type: disc;
}

/* 라디오 그룹 */
#deleteGuideInfo .radio-group label {
	display: block;
	margin-bottom: 8px; /* 라디오 항목 간 간격 */
	cursor: pointer;
}

#deleteGuideInfo input[type="checkbox"] {
	margin-top: 10px;
	margin-bottom: 20px;
}

/* 체크박스 라벨 여백 */
#deleteGuideInfo input[type="checkbox"] {
	margin-top: 15px;
}

/* 버튼 위치 정렬 */
div #deleteBtn {
	text-align: center;
	margin: 0 auto;
}

/* 제출 버튼 스타일 */
button.submitId {
	width: 440px;
	height: 43px;
	background-color: lightgray;
	border: 1px solid #585858;
	cursor: pointer;
	font-size: 16px;
}
</style>
<script type="text/javascript">
//탈퇴 재확인 팝업
function deleteGuidePopUp(){

    const form = document.forms["deleteGuidePopUp"];
    const agree = form.querySelector('input[name="agree"]:checked');
    const reason = form.querySelector('input[name="reason"]:checked');

    if (!agree) {
        alert("탈퇴 약관에 동의해주세요.");
        return;
    }
    if (!reason) {
        alert("탈퇴 사유를 선택해주세요.");
        return;
    }
    const popup = window.open('', 'deleteGuidePopUp', 'width=350,height=250');

    // 기존 폼의 target을 팝업으로 설정하여 제출
    form.target = 'deleteGuidePopUp';
    form.method = 'post';
    form.submit();
};
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<form name ="deleteGuide" id="deleteGuidePopUp" action="deleteGuidePopUp.do" method="post">
	<div id="deleteGuideInfo">
		<h4>1. 현재 사용 중인 계정 정보가 삭제되며 복구할 수 없습니다.</h4>
		<h4>2. 작성한 이용 후기(리뷰)는 삭제되지 않습니다.</h4>
			<ul>
				<li>탈퇴 전 삭제해야 하는 이용후기가 있는지 확인해 주세요.</li>
				<li>탈퇴 시 계정 정보가 바로 삭제됩니다. 따라서 추후 이용 후기 삭제를 요청하시더라도 고객님께서 <br>해당 이용 후기의 작성자인지 확인할 수 없어 삭제해 드릴 수 없습니다.</li>
			</ul>
		<h4>3. 탈퇴 사유를 알려주세요. (필수)</h4>
			<input type="radio" name="reason" value="site">사이트를 이용하기 불편해요.<br>
			<input type="radio" name="reason" value="voc">고객의 소리 이용이 불만족스러워요.<br>
			<input type="radio" name="reason" value="security">개인정보 유출 등 보안이 걱정돼요.<br>
			<input type="radio" name="reason" value="differ">다른 계정을 이용하려고 해요.<br>
			<input type="radio" name="reason" value="other">기타<br>
			<textarea placeholder="(선택) 탈퇴사유 직접 입력"></textarea><br><br>
		
		<input type="checkbox" name="agree" value="agree">위 내용을 확인하였으며 동의합니다.<br><br><br>
		
		<div id="deleteBtn"><input type="hidden" name="userId" value="${sessionScope.authUserId}">
		<button type="button" onclick="deleteGuidePopUp()" class="submitId">탈퇴하기</button></div>
	</div>
</form>	 	

   	



<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>












