<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>hulzzuk</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/log/logListView.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
</head>
<body>

<div id="logGallery">
    <div class="galleryGrid">
        <c:forEach var="log" items="${logList}">
            <div class="galleryItem">
                <img src="<c:out value='${log.imagePath}'/>" alt="Log Image" />
                <div class="overlay">
                    <h3><c:out value="${log.logTitle}"/></h3>
                    <p>❤️ <c:out value="${log.loveCount}" /></p>
                    <p><fmt:formatDate value="${log.logStartDate}" pattern="yyyy-MM-dd" /></p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>