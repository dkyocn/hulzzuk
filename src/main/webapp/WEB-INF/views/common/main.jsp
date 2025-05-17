<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HulZZuk</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="resources/assets/css/main2.css" />
<noscript>
	<link rel="stylesheet" href="resources/assets/css/noscript.css" />
</noscript>
<script type="text/javascript"
	src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
</head>


<body>
<fmt:setLocale value="ja"/>

	<c:import url="/WEB-INF/views/common/header.jsp" />
	<div class="inner">
		<img id="logo" src="resources/images/hulzzuk01.png"> <br>
		<form class="search-box" action="loc/page.do" method="get" id="searchForm">
			<select class="category" id="category" name="locationEnum" onchange="document.getElementById('searchForm').submit();">
				<option value="ALL">전체</option>
				<option value="ACCO">숙소</option>
				<option value="REST">맛집</option>
				<option value="ATTR">즐길거리</option>
			</select> 
			<input class="search-txt" type="text" name="keyword" placeholder="검색어를 입력하세요.">
			<button class="search-btn" type="submit"><img src="resources/images/search.png" style="border:none;"></button>
		</form>
	</div>

	<br>
	<br>
	<div id="top3">
		<section class="tiles">
			<div id="logDiv">
				<div class="title">
					<button class="title" >Log</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="logTop">
					<div id="logTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style1">
								<span class="image"> <img
									src="resources/images/pic01.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Magna</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style2">
								<span class="image"> <img
									src="resources/images/pic02.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Lorem</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style3">
								<span class="image"> <img
									src="resources/images/pic03.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Feugiat</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
						</div>
					</div>
				</div>
			</div>
			<br> <br>

			<div id="accoDiv">
				<div class="title">
					<button class="title"  onclick="location.href='${pageContext.request.contextPath}/loc/page.do?locationEnum=ATTR&page=1&sortEnum=LOVEDESC'" >즐길거리</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="accoTop">
					<div id="accoTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style4">
								<span class="image"> <img
									src="${attrList[0].imgPath}" alt="" />
								</span> <a href="generic.html">
								<h2>${attrList[0].placeName}</h2>
								<div class="content">
									<p>${attrList[0].addressName}</p>
								</div>
							</a>
							</article>
							<article class="style5">
								<span class="image"> <img
										src="${attrList[1].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${attrList[1].placeName}</h2>
									<div class="content">
										<p>${attrList[1].addressName}</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image"> <img
										src="${attrList[2].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${attrList[2].placeName}</h2>
									<div class="content">
										<p>${attrList[2].addressName}</p>
									</div>
								</a>
							</article>
						</div>
					</div>
				</div>
			</div>
			<br> <br>

			<div id="restDiv">
				<div class="title">
					<button class="title" onclick="location.href='${pageContext.request.contextPath}/loc/page.do?locationEnum=REST&page=1&sortEnum=LOVEDESC'" >맛집</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="restTop">
					<div id="restTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style2">
								<span class="image"> <img
										src="${restList[0].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${restList[0].placeName}</h2>
									<div class="content">
										<p>${restList[0].addressName}</p>
									</div>
								</a>
							</article>
							<article class="style3">
								<span class="image"> <img
										src="${restList[1].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${restList[1].placeName}</h2>
									<div class="content">
										<p>${restList[1].addressName}</p>
									</div>
								</a>
							</article>
							<article class="style1">
								<span class="image"> <img
										src="${restList[2].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${restList[2].placeName}</h2>
									<div class="content">
										<p>${restList[2].addressName}</p>
									</div>
								</a>
							</article>
						</div>
					</div>
				</div>
			</div>
			<br> <br>

			<div id="attrDiv">
				<div class="title">
					<button class="title" onclick="location.href='${pageContext.request.contextPath}/loc/page.do?locationEnum=ACCO&page=1&sortEnum=LOVEDESC'"  >숙소</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="attrTop">
					<div id="attrTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style5">
								<span class="image"> <img
										src="${accoList[0].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${accoList[0].placeName}</h2>
									<div class="content">
										<p>${accoList[0].addressName}</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image"> <img
										src="${accoList[1].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${accoList[1].placeName}</h2>
									<div class="content">
										<p>${accoList[1].addressName}</p>
									</div>
								</a>
							</article>
							<article class="style4">
								<span class="image"> <img
										src="${accoList[2].imgPath}" alt="" />
								</span> <a href="generic.html">
									<h2>${accoList[2].placeName}</h2>
									<div class="content">
										<p>${accoList[2].addressName}</p>
									</div>
								</a>
							</article>
					</div>
				</div>
				</div>
			</div>
			<br> <br>
		</section>
	</div>
	<!-- Scripts -->
	<script src="resources/assets/js/browser.min.js"></script>
	<script src="resources/assets/js/breakpoints.min.js"></script>
	<script src="resources/assets/js/util.js"></script>
	<script src="resources/assets/js/main.js"></script>

	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>