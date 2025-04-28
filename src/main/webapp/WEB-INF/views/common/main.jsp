<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
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
<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function () {
		console.log("DOMContentLoaded event triggered!");
		const slideWidth = 350;
		const visibleCount = 3;
		const slideState = {};

		function showSlide(trackId, index) {
			const outer = document.getElementById(trackId);
			if (!outer) {
				console.error(`Element with id "${trackId}" not found!`);
				return;
			}
			const track = outer.querySelector(".sliderTrack");
			if (!track) {
				console.error(`Slider track not found for trackId: ${trackId}`);
				return;
			}
			const totalItems = track.querySelectorAll("article").length;

			const maxIndex = totalItems - visibleCount;
			const newIndex = Math.max(0, Math.min(index, maxIndex));

			slideState[trackId] = newIndex;
			const translateX = -newIndex * slideWidth;
			console.log(`Showing slide for trackId: ${trackId}, index: ${newIndex}, translateX: ${translateX}`);
			track.style.transform = `translateX(${translateX}px)`;
		}

		document.querySelectorAll(".carouselWrapper").forEach(wrapper => {
			const trackId = wrapper.dataset.track;
			if (!trackId) {
				console.warn("No trackId found for wrapper:", wrapper);
				return;
			}

			slideState[trackId] = 0;

			const prevBtn = wrapper.querySelector(".prevBtn");
			const nextBtn = wrapper.querySelector(".nextBtn");

			prevBtn.addEventListener("click", () => {
				console.log(`Previous button clicked for trackId: ${trackId}`);
				showSlide(trackId, slideState[trackId] - 1);
			});

			nextBtn.addEventListener("click", () => {
				console.log(`Next button clicked for trackId: ${trackId}`);
				showSlide(trackId, slideState[trackId] + 1);
			});

			showSlide(trackId, 0);
		});
	});
</script>

</head>


<body>
	<c:import url="/WEB-INF/views/common/header.jsp" />
	<div class="inner">
		<img id="logo" src="resources/images/hulzzuk01.png"> <br>
		<form class="search-box" action="" method="get">
			<select class="category" id="category">
				<option value="">전체</option>
				<option value="acco">숙소</option>
				<option value="rest">맛집</option>
				<option value="attr">즐길거리</option>
			</select> 
			<input class="search-txt" type="text" name=""
				placeholder="검색어를 입력하세요.">
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
					<button class="prevBtn">❮</button>
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
						</div>
					</div>
					<button class="nextBtn">❯</button>
				</div>
			</div>
			<br> <br>

			<div id="accoDiv">
				<div class="title">
					<button class="title" >숙소</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="accoTop">
					<button class="prevBtn" onclick="prevSlide()">❮</button>
					<div id="accoTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style4">
								<span class="image"> <img
									src="resources/images/pic04.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Tempus</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style5">
								<span class="image"> <img
									src="resources/images/pic05.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Aliquam</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image"> <img
									src="resources/images/pic06.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Veroeros</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style4">
								<span class="image"> <img
									src="resources/images/pic04.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Tempus</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style5">
								<span class="image"> <img
									src="resources/images/pic05.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Aliquam</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
						</div>
					</div>
					<button class="nextBtn" >❯</button>
				</div>
			</div>
			<br> <br>

			<div id="restDiv">
				<div class="title">
					<button class="title" >식당</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="restTop">
					<button class="prevBtn" onclick="prevSlide()">❮</button>
					<div id="restTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style2">
								<span class="image"> <img
									src="resources/images/pic07.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Ipsum</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style3">
								<span class="image"> <img
									src="resources/images/pic08.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Dolor</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style1">
								<span class="image"> <img
									src="resources/images/pic09.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Nullam</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style2">
								<span class="image"> <img
									src="resources/images/pic07.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Ipsum</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style3">
								<span class="image"> <img
									src="resources/images/pic08.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Dolor</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
						</div>
					</div>
					<button class="nextBtn" >❯</button>
				</div>
			</div>
			<br> <br>

			<div id="attrDiv">
				<div class="title">
					<button class="title" >즐길거리</button>
					<hr class="titlehr">
				</div>
				<div class="carouselWrapper" data-track="attrTop">
					<button class="prevBtn" onclick="prevSlide()">❮</button>
					<div id="attrTop" class="sliderOuter">
						<div class="sliderTrack">
							<article class="style5">
								<span class="image"> <img
									src="resources/images/pic10.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Ultricies</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image"> <img
									src="resources/images/pic11.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Dictum</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style4">
								<span class="image"> <img
									src="resources/images/pic12.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Pretium</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style5">
								<span class="image"> <img
									src="resources/images/pic10.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Ultricies</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image"> <img
									src="resources/images/pic11.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Dictum</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
							<article class="style4">
								<span class="image"> <img
									src="resources/images/pic12.jpg" alt="" />
								</span> <a href="generic.html">
									<h2>Pretium</h2>
									<div class="content">
										<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor
											veroeros et feugiat.</p>
									</div>
								</a>
							</article>
						</div>
					</div>
					<button class="nextBtn">❯</button>
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