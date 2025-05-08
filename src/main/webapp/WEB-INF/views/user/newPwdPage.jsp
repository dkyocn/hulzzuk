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
<style>
div.loginLogo{
text-align: center;
}

div.loginLogo img{
width: 500px;
}

div#pwdForm{
text-align: center;
margin: 0 auto;
}

div#pwdForm input#userPwd{
border: none;
outline: none;
width: 93%; 
height: 100%;
padding: 0;
margin: 0;
font-size: 16px;
border: none;
outline: none;
box-sizing: border-box; /* 패딩 포함해서 width 100% 유지 */
}

div#pwdForm input#pwdConfirm{
border: none;
outline: none;
width: 80%; 
height: 100%;
padding: 0;
margin: 0;
font-size: 16px;
border: none;
outline: none;
box-sizing: border-box; /* 패딩 포함해서 width 100% 유지 */
}

table#rePwd{
border: 1px solid #585858;
border-collapse: collapse;	/* 테이블 선 겹치기 방지 */
margin: 0 auto;	
}

table#rePwd th{
width: 150px;
border: 1px solid #585858;
padding: 10px 25px;
text-align: center;
}

table#rePwd td{
width: 400px;
height: 43px;
border: 1px solid #585858;
padding: 0;
padding-left: 8px;
}

button.pwdButton{
width: 50px;
height: 35px;
font-size: 16px;
cursor: pointer;
}

button.submitId{
width: 440px;
height: 43px;
background-color: lightgray;
border: 1px solid #585858;
cursor: pointer;
font-size: 16px;
}

div.pwdCondition{
color: #585858;
font-size: 15px;
margin-top: 10px;
margin-bottom: 10px;
text-align: center;
line-height: 1.5;
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
<script type="text/javascript">
let isPwdConfirmed = false;  // 비밀번호 확인 여부 저장
	
// 비밀번호 유효성 검사
$(document).ready(function () {
    function validatePassword() {
        const pw = $("#userPwd").val();
        let msg = "";
        let color = "red";

        if (pw.length === 0) {
            msg = "8~16자의 영문, 숫자, 특수문자만 가능합니다. (사용 가능한 특수문자 : ~ ! @ # $)";
            color = "gray";
        }

        if (pw.length > 0 && pw.length < 8 || pw.length > 16) {
            msg = "! 8~16자 사이여야 합니다.";
        }
       /*  if (/\s/.test(pw)) {
            msg = "! 공백은 사용할 수 없습니다.";
        } */
        if (
            pw.length > 0 &&
            (pw.search(/[0-9]/g) < 0 || pw.search(/[a-z]/ig) < 0 || pw.search(/[~!@#$]/g) < 0)
        ) {
            msg = "! 영문, 숫자, 특수문자(~!@#$)를 모두 포함해야 합니다.";
        }
        if (/[^A-Za-z0-9~!@#$]/.test(pw)) {
            msg = "! 허용되지 않은 특수문자가 포함되었습니다.";
        }

        if (
            pw.length >= 8 && pw.length <= 16 &&
            !/\s/.test(pw) &&
            !/[^A-Za-z0-9~!@#$]/.test(pw) &&
            pw.search(/[0-9]/g) >= 0 &&
            pw.search(/[a-z]/ig) >= 0 &&
            pw.search(/[~!@#$]/g) >= 0
        ) {
            msg = "사용 가능한 비밀번호입니다.";
            color = "green";
        }

        $("#pwdCondition").text(msg).css("color", color);
    }

    // 입력 시 실시간 검사
    $("#userPwd").on("input", validatePassword);

    // 페이지 로드 직후 한 번 실행
    validatePassword();
});


// 비밀번호 일치 확인
function validate(){
	var pwdValue = $('#userPwd').val();
	var pwdValueConfirm = $('#pwdConfirm').val();
	
	if(!pwdValue || !pwdValueConfirm || pwdValue !== pwdValueConfirm){
		$('#userPwd').val('');
	    $('#pwdConfirm').val('');  // 이 부분도 통일
	    $('#pwdConfirm').focus();  //입력커서 지정함
		return false;  //전송 취소함
	}
	return true;
}

// 비밀번호 일치 확인 팝업
function openPwdConfirmPopUp() {
	const pwd = document.getElementById("userPwd").value;
	const confirm = document.getElementById("pwdConfirm").value;

	// 비어있거나 불일치한 경우 처리
	if (!pwd || !confirm) {
	    alert("비밀번호와 확인란을 모두 입력해주세요.");
	    isPwdConfirmed = false;
	    return;
	}

   // 통과 시 플래그 설정
   isPwdConfirmed = true;
	 
   // 팝업 창 열기 (가로 400px, 세로 300px)
   window.open("", 'pwdConfirmPopUp', 'width=350,height=250');
   
   const pwdChange = document.pwdChange;

   // 팝업창의 이름을 target으로 설정
   pwdChange.target = 'pwdConfirmPopUp';

   // 서버 요청 URL 설정
   pwdChange.action = '${pageContext.request.contextPath}/user/pwdConfirm.do';
   
   pwdChange.submit();  
}

// 재설정 버튼 클릭 시 호출
function validateBeforeUpdate() {
    if (!isPwdConfirmed) {
        alert("비밀번호 확인을 먼저 진행해주세요.");
        return false;
    }
    return true;
}

// 비밀번호 재설정 팝업
function openPwdUpdatePopUp() {
   // 비밀번호 확인 여부 검사
   if(!validateBeforeUpdate()){
		return;
   }
   
   // 팝업 창 열기 (가로 400px, 세로 300px)
   window.open("", 'pwdUpdatePopUp', 'width=350,height=250');
   
   // 사용자 입력값을 hidden 필드에 복사
   const typedPassword = document.getElementById('userPwd').value;
   document.getElementById('hiddenUserPwd').value = typedPassword;
	   
   const pwdChangeSave = document.pwdChangeSave;
   pwdChangeSave.target = 'pwdUpdatePopUp';
   pwdChangeSave.action = '${pageContext.request.contextPath}/user/pwdUpdate.do';
   pwdChangeSave.submit();  
}
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="loginLogo">
	<br><br><br><br>
	<a href="${pageContext.request.contextPath}/"> 
	<img src="${pageContext.request.contextPath}/resources/images/logo2.png"
		alt="logo" />
	</a>
</div>
<div id="pwdForm">
	<div id="pwdCondition"></div><br>
	<form name="pwdChange" id="pwdChange" action="${pageContext.request.contextPath}/user/pwdConfirm.do" method="post" onsubmit="return validate();">
		<!-- 세션에서 받아온 userId를 hidden으로 유지 -->
        <input type="hidden" name="userId" value="${userId}" />
        
		<table id="rePwd">
			<tr><th>비밀번호</th>
				<td>
					<input type="password" id="userPwd" name="userPwd" class="pos">
					<span style="color:${color}">${msg}</span>
				</td></tr>
			<tr><th>비밀번호 확인</th>
				<td><!-- 비밀번호 확인 용도(페이지) 구분용 -->
				<input type="hidden" name="mode" value="reset">
				<input type="password" id="pwdConfirm" name="pwdConfirm" class="pos">
				<button type="button" onclick="openPwdConfirmPopUp()" class="pwdButton">확인</button></td></tr>
		</table>
		<br>
	</form>	
	
	<!-- 비밀번호 재설정 버튼 (DB 업데이트) -->
    <form name="pwdChangeSave" id="pwdChangeSave" action="${pageContext.request.contextPath}/user/pwdUpdate.do" 
				method="post" onsubmit="return validateBeforeUpdate();">
        <input type="hidden" name="userId" value="${userId}" />
        <input type="hidden" name="userPwd" id="hiddenUserPwd" />
        <button type="button" onclick="openPwdUpdatePopUp()" class="submitId">재설정</button>
        <br><br>
    </form>
</div>
	
	
</body>
</html>