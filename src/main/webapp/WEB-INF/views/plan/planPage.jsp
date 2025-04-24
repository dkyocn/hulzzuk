<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 4. 24.
  Time: 오후 5:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="nowpage" value="1" />
<c:if test="${ !empty requestScope.paging.currentPage }">
    <c:set var="nowpage" value="${ requestScope.paging.currentPage }" />
</c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>hulzzuk</title>
    <style type="text/css">
        fieldset#ss {
            width: 650px;
            position: relative;
            left: 250px;
        }
        form fieldset {
            width: 600px;
        }
        form.sform {
            background: lightgray;
            width: 650px;
            position: relative;
            left: 250px;
            display: none;  /* 안 보이게 함 */
        }
    </style>
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(function(){
            //input 태그의 name 이 item 인 radio 값이 바뀌면(change) 작동되는 이벤트 핸들러 작성
            //jQuery('태그선택자').실행할메소드(....);  => jQuery 는 줄여서 $ 로 표기함
            $('input[name=item]').on('change', function(){
                //5개의 item 중에 체크표시가 된 radio 찾음 => 반복 처리 : each() 메소드 사용
                $('input[name=item]').each(function(index){
                    //선택된 radio 순번대로 하나씩 checked 인지 확인함 : is() 메소드 사용
                    if($(this).is(':checked')){
                        //체크 표시된 아이템에 대한 폼이 보여지게 함
                        $('form.sform').eq(index).css('display', 'block');
                    }else{
                        //체크 표시 안된 아이템에 대한 폼이 안 보여지게 함
                        $('form.sform').eq(index).css('display', 'none');
                    }
                });
            });
        });
    </script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div style="text-align: center;">
    <button onclick="location.href='${ pageContext.servletContext.contextPath }/blist.do?page=1';">목록</button> &nbsp;
<%--    <c:if test="${ !empty sessionScope.loginUser }">--%>
<%--        <button onclick="location.href='bwform.do';">글쓰기</button>--%>
<%--    </c:if>--%>
    <button onclick="location.href='bwform.do';">글쓰기</button>
</div>
<br>

<%-- 항목별 검색 기능 추가 --%>
<fieldset id="ss">
    <legend>검색할 항목을 선택하세요.</legend>
    <input type="radio" name="item" id="title"> 제목 &nbsp;
    <input type="radio" name="item" id="writer"> 작성자 &nbsp;
    <input type="radio" name="item" id="date"> 등록날짜 &nbsp;
</fieldset>

<%-- 검색 항목별 값 입력 전송용 폼 만들기 --%>
<%-- 제목 검색 폼 --%>
<form action="bsearchTitle.do" id="titleform" class="sform" method="get">
    <input type="hidden" name="action" value="title">
    <fieldset>
        <legend>검색할 제목을 입력하세요.</legend>
        <input type="search" name="keyword" size="50"> &nbsp;
        <input type="submit" value="검색">
    </fieldset>
</form>

<%-- 작성자 검색 폼 --%>
<form action="bsearchWriter.do" id="writerform" class="sform" method="get">
    <input type="hidden" name="action" value="writer">
    <fieldset>
        <legend>검색할 작성자 아이디를 입력하세요.</legend>
        <input type="search" name="keyword" size="50"> &nbsp;
        <input type="submit" value="검색">
    </fieldset>
</form>

<%-- 등록날짜 검색 폼 --%>
<form action="bsearchDate.do" id="dateform" class="sform" method="get">
    <input type="hidden" name="action" value="date">
    <fieldset>
        <legend>검색할 등록날짜 범위를 입력하세요.</legend>
        <input type="date" name="begin"> ~ <input type="date" name="end"> &nbsp;
        <input type="submit" value="검색">
    </fieldset>
</form>


<br><br>
<%-- 조회된 게시글 목록 출력 --%>
<table align="center" width="650" border="1" cellspacing="0" cellpadding="0">
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>장소</th>
        <th>시작날짜</th>
        <th>종료날짜</th>
    </tr>
    <c:forEach items="${ requestScope.list }" var="plan">
        <tr align="center">
            <td>${ plan.planId }</td>
            <td align="left">
                <c:url var="bd" value="plan/select.do">
                    <c:param name="bnum" value="${ plan.planId }" />
                    <c:param name="page" value="${ nowpage }" />
                </c:url>
                <a href="${ bd }">${ plan.planTitle }</a>
            </td>
            <td>${ plan.planPlace }</td>
            <td>${ plan.planStartDate }</td>
            <td>${ plan.planEndDate }</td>
        </tr>
    </c:forEach>
</table>
<c:import url="/WEB-INF/views/common/pagination.jsp" />
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
