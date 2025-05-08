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
        	const action = '${action}';
            const moveUrl = '${moveUrl}';
            //console.log("action:", action, "moveUrl:", moveUrl);

            if (action === 'redirect' && moveUrl) {
                if (window.opener) {
                    window.opener.location.href = moveUrl;
                }
            }
            window.close();
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
         <!--    <button class="btn-cancel" onclick="cancelAction()">취소</button> -->
        </div>
    </div>
</div>
</body>
</html>
