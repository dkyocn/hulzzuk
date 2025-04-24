<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<script type="text/javascript"
	src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap"
	rel="stylesheet">
<style type="text/css">
div#info {
	width: 1000px;
	text-align: center;
	border: 1px solid #585858;
	margin: 0 auto;
}

th{
border: 0;
}

td{
width: 500px;
}
</style>
</head>
<body>
	<c:import url="/WEB-INF/views/common/header.jsp" />
	<div id="info">
	<tr><th text-align="center">사진첨부</th>
		<td>
		<img src="/hulzzuk/resources/images/hulzzuk01.png">
		<%-- <c:if test="${ !empty requestScope.user.photoFileName }">
			<div id="myphoto" style="margin:0; width:150px; height:160px; padding:0; border:1px solid navy;">
				사진 첨부가 없을 경우를 위한 미리보기용 이미지 출력되게 함
				<img src="/first/resources/photoFiles/${ requestScope.user.photoFileName }" id="photo" 
				style="width:150px;height:160px;border:1px solid navy;display:block;margin:0;padding:0;" 
				alt="사진을 드래그 드롭하세요.">
			</div> <br>
			${ requestScope.ofile } <br>
			변경할 사진 선택 : <input type="file" id="photofile" name="photofile">			
		 </c:if>
		 <c:if test="${ empty requestScope.user.photoFileName }">
			<div id="myphoto" style="margin:0; width:150px; height:160px; padding:0; border:1px solid navy;">
				사진 첨부가 없을 경우를 위한 미리보기용 이미지 출력되게 함
				<img src="/first/resources/images/photo1.jpg" id="photo" 
			style="width:150px;height:160px;border:1px solid navy;display:block;margin:0;padding:0;" 
			alt="사진을 드래그 드롭하세요.">
			</div> <br>			
			사진 선택 : <input type="file" id="photofile" name="photofile">			
		 </c:if> --%>
		</td></tr>
		<br>
		<tr><th>닉네임</th>
		<td><input text-align="center" type="text" name="userNick" id="userNick" value="${ requestScope.user.userNick }" readonly></td></tr>
		<br>
	</div>
	<br>
	
	<table id="infoDetail" align="center" border="1px" width="1000" cellspacing="5" cellpadding="0">
	<br>
	<tr><th width="120">*아이디</th>
	<%-- input 태그의 name 속성의 이름은 member.dto.Member 클래스의 property 명과 같게 함 --%>
		<td><input type="text" name="userId" id="userId" value="${ requestScope.user.userId }" readonly>			
		</td></tr>
	
	<tr><th>암호</th>
		<td><input type="password" name="userPwd" id="userPwd" ></td></tr>
	<tr><th>암호확인</th>
		<td><input type="password" id="userPwd2" ></td></tr>
	
	<tr><th>성별</th>
		<td>
			<c:if test="${ requestScope.user.gender eq 'M' }">
				<input type="radio" name="gender" value="M" checked> 남자 &nbsp;
				<input type="radio" name="gender" value="F"> 여자 
			</c:if>
			<c:if test="${ requestScope.user.gender eq 'F' }">
				<input type="radio" name="gender" value="M" > 남자 &nbsp;
				<input type="radio" name="gender" value="F" checked> 여자 
			</c:if>&nbsp;&nbsp;&nbsp;
			<input type="submit" value="수정하기">
		</td></tr>
	<tr><th>나이</th>
		<td><input type="number" name="userAge" min="19" max="100" value="${ user.userAge }" ></td>
		&nbsp;&nbsp;&nbsp;
		<input type="submit" value="수정하기"></tr>
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
	
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>