<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="fail" value="${requestScope.mailFalse}" />
<c:if test="${action eq 'back'}"/>
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

div.loginLogo img {
width: 500px;
}


div#joinForm{
text-align: center;
margin: 0 auto;
}

div#joinForm input.withoutBtn{
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

div#joinForm input.withBtn{
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

table#joinUser{
border: 1px solid #585858;
border-collapse: collapse;	/* 테이블 선 겹치기 방지 */
margin: 0 auto;	
}

table#joinUser th{
width: 150px;
border: 1px solid #585858;
padding: 10px 25px;
text-align: center;
}

table#joinUser td{
width: 400px;
height: 43px;
border: 1px solid #585858;
padding: 0;
padding-left: 8px;
}

button.joinButton{
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
</style>
<script type="text/javascript">
// 인증번호 전송 팝업
function openMailPopUp() {
 const userId = document.querySelector('input[name="userId"]').value;
 
 const url = '${pageContext.request.contextPath}/user/sendMail.do'
 		+ '?mode=joinSend'
		+ '&userId=' + encodeURIComponent(userId)
	 	+ '&width=350&height=300';
 
   // 팝업 창 열기 (가로 400px, 세로 300px)
   window.open(url, 'mailPopUp', 'width=350,height=250');
} 

// 실패 시 인증번호 입력 칸에 포커스
window.onload = function() {
    if (window.opener) {
        const input = window.opener.document.querySelector('input[name="inputCode"]');
        if (input) {
            input.focus();
            input.select();
        }
    }
   // window.close();
};

//인증번호 검증 (팝업)
function openVerifyPopUp() {
    document.getElementById("modeField").value = "joinVerify";

    // 팝업 먼저 열고, 그 창을 대상으로 form 전송
    window.open('', 'verifyPopUp', 'width=350,height=250');

    document.getElementById("verifyCodeForm").target = 'verifyPopUp';
    document.getElementById("verifyCodeForm").submit();
}

let isPwdConfirmed = false;  // 비밀번호 확인 여부 저장
	
//비밀번호 유효성 검사
$(document).ready(function () {
  function validatePassword() {
      const pw = $("#userPwd").val();
      let msg = "";
      let color = "red";

      if (pw.length === 0) {
          msg = "비밀번호는 8~16자의 영문, 숫자, 특수문자만 가능합니다. (사용 가능한 특수문자 : ~ ! @ # $)";
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
 
//비밀번호 일치 확인
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
	    return false;
	}

   // 통과 시 플래그 설정
   isPwdConfirmed = true;

   const form = document.getElementById("verifyCodeForm");
   
   const popup = window.open('', 'pwdConfirmPopUp', 'width=350,height=250');
   if (popup) popup.focus();
   
   form.target = 'pwdConfirmPopUp';

   // target 복원 (비동기성 때문에 setTimeout으로 약간 지연)
   setTimeout(() => {
       form.target = ''; // 기본 target (self)로 복원
   }, 100);
   
   return true;	// submit 허용
}
 
// 회원가입 버튼 클릭 시 호출
function validateBeforeUpdate() {
    if (!isPwdConfirmed) {
        alert("비밀번호 확인을 먼저 진행해주세요.");
        return false;
    }
    return true;
}

// 회원가입 버튼 팝업
function userJoinPopUp() {
   // 비밀번호 확인 여부 검사
   if(!validateBeforeUpdate()) return;
   
   // 값 복사
   document.getElementById('hiddenUserId').value = document.querySelector('input[name="userId"]').value;
   document.getElementById('hiddenUserPwd').value = document.getElementById('userPwd').value;
   document.getElementById('hiddenUserAge').value = document.querySelector('input[name="userAge"]').value;

   // 성별: 체크된 값만
   const gender = document.querySelector('input[name="gender"]:checked');
   if (gender) {
       document.getElementById('hiddenGender').value = gender.value;
   }
   console.log("ID:", document.getElementById('hiddenUserId').value);
   console.log("PWD:", document.getElementById('hiddenUserPwd').value);
   console.log("AGE:", document.getElementById('hiddenUserAge').value);
   console.log("GENDER:", document.getElementById('hiddenGender').value);
   // 팝업 창 열기
   const popup = window.open("", 'userJoinPopUp', 'width=350,height=250');
   if (popup) popup.focus();

   const userJoin = document.userJoin;
   userJoin.target = 'userJoinPopUp';
   userJoin.submit();
} 
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div class="loginLogo">
	<br><br><br><br>
	<a href="${pageContext.request.contextPath}/"> 
	<img src="${pageContext.request.contextPath}/resources/images/logo2.png" alt="logo" /></a>
</div><br>

<div id="joinForm">
	<!-- 비밀번호 유효성 검사 -->
	<div id="pwdCondition"></div><br>
	<form name="verifyCodeForm" id="verifyCodeForm" action="${pageContext.request.contextPath}/user/verifyCode.do" method="post" target="verifyPopUp">
		<input type="hidden" name="mode" id="modeField" value="">
		
		<table id="joinUser">
			<tr><th>아이디(이메일)</th>
				<td><input type="email" name="userId" class="withBtn">
				<button type="button" onclick="openMailPopUp()" class="joinButton">전송</button></td></tr>
			<tr><th>인증번호</th>
				<td><input type="password" name="inputCode" class="withBtn">
				<button type="button" onclick="openVerifyPopUp()" class="joinButton">확인</button></td></tr>
			<tr><th>비밀번호</th>
				<td><input type="password" id="userPwd" name="userPwd" class="withoutBtn"></td></tr>
			<tr><th>비밀번호 확인</th>
				<td><input type="password" id="pwdConfirm" name="pwdConfirm" class="withBtn">
				<button type="submit" class="joinButton"
				formaction="${pageContext.request.contextPath}/user/pwdConfirm.do"
				formmethod="post" onclick="return openPwdConfirmPopUp();">확인</button></td></tr>
			<tr><th>생년월일</th>
				<td><input type="date" name="userAge" class="withoutBtn"></td></tr>
			<tr><th>성별</th>
				<td><input type="radio" name="gender" value="M"> 남자 &nbsp;
					<input type="radio" name="gender" value="F"> 여자 </td></tr>
		</table><br><br>
	</form>
	
	<!-- 회원가입 완료 버튼 (DB 추가) -->
    <form name="userJoin" id="userJoin" action="${pageContext.request.contextPath}/user/join.do" 
				method="post" onsubmit="return validateBeforeUpdate();">
		<input type="hidden" name="userId" id="hiddenUserId">
	    <input type="hidden" name="userPwd" id="hiddenUserPwd">
	    <input type="hidden" name="userAge" id="hiddenUserAge">
	    <input type="hidden" name="gender" id="hiddenGender">
        <button type="button" onclick="userJoinPopUp()" class="submitId">회원가입</button>
        <br><br>
    </form>
</div>
</body>
</html>