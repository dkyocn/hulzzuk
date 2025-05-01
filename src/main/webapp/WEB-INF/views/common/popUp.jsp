
<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 4. 26.
  Time: 오후 5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>popup</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/popUp.css">
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
    <script>
        function confirmAction() {
            fetch('${actionUrl}', {
                method: 'GET'
            })
                .then(response => response.text())
                .then(data => {
                    if (data === 'success') {
                        if (window.opener) window.opener.location.reload();
                        window.close();
                        alert('삭제가 완료되었습니다.')
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('작업 중 오류가 발생했습니다.');
                });
        }

        function cancelAction() {
            window.close();
        }

        window.onload = function() {
            window.resizeTo(${width}, ${height});
            window.moveTo(
                (screen.width - ${width}) / 2,
                (screen.height - ${height}) / 2
            );
        };
    </script>
</head>
<body>
<div class="popup-overlay">
    <div class="popup">
        <div class="popup-icon">!</div>
        <div class="popup-message">
            ${message}
        </div>
        <div class="popup-buttons">
            <button class="btn-confirm" onclick="confirmAction()">확인</button>
            <!-- <button class="btn-cancel" onclick="cancelAction()">취소</button> -->
        </div>
    </div>
</div>
</body>
</html>
