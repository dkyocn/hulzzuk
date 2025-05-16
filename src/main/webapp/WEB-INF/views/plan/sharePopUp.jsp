<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>ShareUser</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/plan/shareUserPop.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
</head>
<script type="text/javascript">
    // 사용자 고유
    async function shareUserPlan() {
        // 입력값 가져오기
        const userIdInput = document.querySelector('#shareUserInput').value.trim();

        console.log(userIdInput);
        // 유효성 검사
        if (!userIdInput) {
            alert('공유할 사용자 ID를 입력해주세요.');
            return;
        }

        try {
            const response = await $.ajax({
                url: '${pageContext.request.contextPath}/plan/shareUser.do',
                type: 'POST',
                data: {
                    planId: ${requestScope.planId},
                    userId: userIdInput
                },
                dataType: 'json'
            });

            // 서버 응답에서 successYN 확인
            if (response.successYN === "true") {
                console.log("일정 공유 성공");
                alert('공유를 완료하였습니다.');
                window.close();
            } else if(response.successYN === "share"){
                alert("이미 공유된 사용자 입니다. 다시 입력해주세요.");
            } else {
                console.error("일정 공유 실패:", response.message || "알 수 없는 오류");
                alert("가입되지 않은 사용자 입니다. 다시 입력해주세요.");
            }
        } catch (error) {
            console.error('일정 공유 요청 실패:', error);
            alert('서버와의 연결에 실패했습니다. 다시 시도해주세요.');
        }
    }
</script>
<body>
<div>
  <h1>공유할 사용자 ID</h1>
  <input class="shareUserInput" id="shareUserInput" type="text" placeholder="공유할 사용자의 ID를 입력해주세요">
  <button class="shareUserBtn" type="button" onclick="shareUserPlan()">공유</button>
</div>
</body>
</html>
