<%--
  Created by IntelliJ IDEA.
  User: jeongdongju
  Date: 25. 4. 26.
  Time: 오후 5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<head>
    <title>hulzzuk</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/plan/createPlanFirst.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        // 선택된 데이터 전역 변수
        let selectedDates = []; // 선택된 날짜를 저장할 배열
        let selectedLocations = []; // 선택된 장소들을 저장할 배열

        // 한글-영어 클래스 이름 매핑
        const locationClassMap = {
            '서울': '.clickSeoul',
            '경기': '.clickGyeonggi',
            '부산': '.clickBusan',
            '인천': '.clickIncheon',
            '제주': '.clickJeju',
            '강원': '.clickGangwon',
            '전라': '.clickJeolla',
            '경상': '.clickGyeongsang',
            '충청': '.clickChungcheong'
        };

        // 장소 클릭 시 호출되는 함수 (전역 스코프)
        function clickLoc(locName) {
            const className = locationClassMap[locName];
            if (!className) {
                console.error(`매핑되지 않은 장소: ${locName}`);
                return;
            }

            const locElement = document.querySelector(className);

            const isAlreadySelected = selectedLocations.includes(locName);

            if (isAlreadySelected) {
                // 선택 해제
                selectedLocations = selectedLocations.filter(loc => loc !== locName);
                locElement.style.backgroundColor = 'rgb(200 200 200 / 76%)';
            } else {
                // 선택 추가
                selectedLocations.push(locName);
                locElement.style.backgroundColor = 'rgb(72 72 72 / 76%)';
            }

            console.log('선택된 장소:', selectedLocations);
        }

        document.addEventListener('DOMContentLoaded', function () {
            // JSP에서 전달된 PlanVO 데이터
            const planData = {
                planId: "${requestScope.plan.planId}",
                title: "${requestScope.plan.planTitle}",
                planPlace: "${requestScope.plan.planPlace}",
                startDate: "${requestScope.plan.planStartDate}",
                endDate: "${requestScope.plan.planEndDate}"
            };

            // selectedDates에 시작 날짜와 종료 날짜 저장
            if (planData.startDate === planData.endDate) {
                selectedDates.push(planData.startDate);
            } else {
                selectedDates.push(planData.startDate, planData.endDate);
            }

            const locations = planData.planPlace.split(',').map(item => item.trim());
            console.log('locations:', locations);
            locations.forEach(location => {
                selectedLocations.push(location);
            });

            console.log("selectedDates:", selectedDates);
            console.log("selectedLocations:", selectedLocations);

            // selectedLocations 초기 설정 반영
            selectedLocations.forEach(locName => {
                const className = locationClassMap[locName];
                if (className) {
                    const locElement = document.querySelector(className);
                    if (locElement) {
                        locElement.style.backgroundColor = 'rgb(72 72 72 / 76%)'; // 선택된 상태
                    } else {
                        console.error(`요소가 존재하지 않음: ${className}`);
                    }
                } else {
                    console.error(`매핑되지 않은 장소: ${locName}`);
                }
            });

            const calendar1El = document.getElementById('calendar1');
            const calendar2El = document.getElementById('calendar2');
            let calendar1, calendar2;
            let isSyncing = false; // 동기화 중복 방지 플래그

            // 오늘 날짜 기준으로 기본 초기 날짜 설정
            const today = new Date();
            const currentYear = today.getFullYear();
            const currentMonth = today.getMonth(); // 0-based
            let initialDate = currentYear + '-' + String(currentMonth + 1).padStart(2, '0') + '-01'; // 기본값: 현재 달
            let nextMonthInitialDate;

            // selectedDates가 있을 경우 초기 날짜를 selectedDates의 첫 번째 날짜 기준으로 설정
            if (selectedDates.length > 0) {
                selectedDates.sort(); // 날짜 정렬 (가장 빠른 날짜가 첫 번째로 오도록)
                const earliestDate = new Date(selectedDates[0]); // 가장 빠른 날짜
                initialDate = earliestDate.getFullYear() + '-' + String(earliestDate.getMonth() + 1).padStart(2, '0') + '-01'; // 해당 월의 첫 날
                console.log('Initial date set to earliest selected date:', initialDate);
            }

            // calendar2의 초기 날짜 설정 (calendar1의 다음 달)
            const initialDateObj = new Date(initialDate);
            const nextMonthDate = new Date(initialDateObj.getFullYear(), initialDateObj.getMonth() + 1, 1);
            nextMonthInitialDate = nextMonthDate.getFullYear() + '-' + String(nextMonthDate.getMonth() + 1).padStart(2, '0') + '-01';
            console.log('calendar1 initialDate:', initialDate);
            console.log('calendar2 initialDate:', nextMonthInitialDate);

            // 한국어 로케일 커스터마이징: "일" 제거
            FullCalendar.globalLocales.push((function () {
                return {
                    code: 'ko',
                    week: {
                        dow: 0, // 일요일 시작
                        doy: 4
                    },
                    buttonText: {
                        prev: '◄',
                        next: '►',
                        today: '오늘',
                        month: '월',
                        week: '주',
                        day: '일',
                        list: '목록'
                    },
                    weekText: '주',
                    allDayText: '종일',
                    moreLinkText: '개 추가',
                    noEventsText: '일정이 없습니다'
                };
            })());

            // 선택된 날짜를 모든 달력에 재적용하는 함수
            function applySelectedDates() {
                [calendar1, calendar2].forEach(cal => {
                    // 기존 선택된 클래스 제거
                    const allDays = cal.el.querySelectorAll('.fc-day-selected');
                    allDays.forEach(day => day.classList.remove('fc-day-selected'));

                    // 선택된 날짜에 클래스 추가
                    selectedDates.forEach(date => {
                        const dayEl = cal.el.querySelector(`[data-date="`+date+`"]`);
                        if (dayEl) {
                            dayEl.classList.add('fc-day-selected');
                        }
                    });
                });
            }

            // 날짜 선택 로직
            function handleDateClick(info, calendar) {
                const clickedDate = info.dateStr;
                const clickedDateObj = new Date(clickedDate);

                const isAlreadySelected = selectedDates.includes(clickedDate);

                console.log("handleDateClick called, Clicked date:", clickedDate, "Is selected:", isAlreadySelected, "Current selectedDates:", selectedDates);

                if (isAlreadySelected) {
                    // 선택 해제
                    selectedDates = selectedDates.filter(date => date !== clickedDate);
                    info.dayEl.classList.remove('fc-day-selected');
                } else {
                    if (selectedDates.length === 0) {
                        // 첫 번째 날짜 선택
                        selectedDates.push(clickedDate);
                        info.dayEl.classList.add('fc-day-selected');
                    } else if (selectedDates.length === 1) {
                        // 두 번째 날짜 선택: 연속된 날짜만 허용
                        const firstDate = new Date(selectedDates[0]);
                        const diffDays = Math.abs((clickedDateObj - firstDate) / (1000 * 60 * 60 * 24));

                        if (diffDays === 1) { // 연속된 날짜인지 확인
                            selectedDates.push(clickedDate);
                            info.dayEl.classList.add('fc-day-selected');
                        } else {
                            alert('연속된 2일만 선택할 수 있습니다.');
                            return;
                        }
                    } else {
                        alert('최대 2일까지만 선택할 수 있습니다.');
                        return;
                    }
                }

                selectedDates.sort();
                console.log('선택된 날짜:', selectedDates);

                // 모든 달력에 선택 상태 반영
                applySelectedDates();
            }

            // 사용자 정의 버튼 정의
            const customButtons = {
                customPrev: {
                    text: '❮',
                    click: function() {
                        if (isSyncing) return;
                        isSyncing = true;
                        calendar1.prev();
                        calendar2.prev();
                        applySelectedDates(); // 달 이동 후 선택 상태 복원
                        isSyncing = false;
                    }
                },
                customNext: {
                    text: '❯',
                    click: function() {
                        if (isSyncing) return;
                        isSyncing = true;
                        calendar1.next();
                        calendar2.next();
                        applySelectedDates(); // 달 이동 후 선택 상태 복원
                        isSyncing = false;
                    }
                }
            };

            // calendar1: selectedDates를 기준으로 초기화
            calendar1 = new FullCalendar.Calendar(calendar1El, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                headerToolbar: {
                    left: 'customPrev',
                    center: 'title',
                    right: 'customNext'
                },
                customButtons: customButtons,
                initialDate: initialDate, // 동적으로 설정된 초기 날짜
                selectable: true,
                unselectAuto: false,
                showNonCurrentDates: false, // 현재 달이 아닌 날짜 숨기기
                dateClick: function (info) {
                    handleDateClick(info, calendar1);
                },
                dayCellContent: function (info) {
                    // 날짜 숫자만 표시하도록 커스터마이즈
                    return info.dayNumberText.replace('일', '');
                },
                datesSet: function (info) {
                    if (isSyncing) return;
                    isSyncing = true;

                    console.log("datesSet called for calendar1, Current start:", info.view.currentStart, "Selected dates:", selectedDates);

                    // calendar1의 날짜를 기준으로 calendar2 동기화
                    const currentDate = info.view.currentStart;
                    const nextMonth = new Date(currentDate);
                    nextMonth.setMonth(currentDate.getMonth() + 1);
                    calendar2.gotoDate(nextMonth);

                    // 선택된 날짜 재적용
                    applySelectedDates();

                    isSyncing = false;
                }
            });

            // calendar2: calendar1의 다음 달로 초기화
            calendar2 = new FullCalendar.Calendar(calendar2El, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                headerToolbar: {
                    left: 'customPrev',
                    center: 'title',
                    right: 'customNext'
                },
                customButtons: customButtons,
                initialDate: nextMonthInitialDate, // 동적으로 설정된 초기 날짜
                selectable: true,
                unselectAuto: false,
                showNonCurrentDates: false, // 현재 달이 아닌 날짜 숨기기
                dateClick: function (info) {
                    handleDateClick(info, calendar2);
                },
                dayCellContent: function (info) {
                    // 날짜 숫자만 표시하도록 커스터마이즈
                    return info.dayNumberText.replace('일', '');
                },
                datesSet: function (info) {
                    if (isSyncing) return;
                    isSyncing = true;

                    console.log("datesSet called for calendar2, Current start:", info.view.currentStart, "Selected dates:", selectedDates);

                    // calendar2의 날짜를 기준으로 calendar1 동기화
                    const currentDate = info.view.currentStart;
                    const prevMonth = new Date(currentDate);
                    prevMonth.setMonth(currentDate.getMonth() - 1);
                    calendar1.gotoDate(prevMonth);

                    // 선택된 날짜 재적용
                    applySelectedDates();

                    isSyncing = false;
                }
            });

            // 달력 렌더링
            calendar1.render();
            calendar2.render();

            // 초기 선택된 날짜 반영 (렌더링 후 실행)
            applySelectedDates();
        });

        // "다음" 버튼 클릭 시 폼 제출
        function goToNext() {
            const planName = document.querySelector('input[name="planName"]').value;
            if (!planName) {
                alert('일정 제목을 입력해주세요.');
                return;
            }
            if (selectedDates.length === 0) {
                alert('날짜를 선택해주세요.');
                return;
            }
            if (selectedLocations.length === 0) {
                alert('장소를 선택해주세요.');
                return;
            }

            // hidden input에 데이터 설정
            document.getElementById("planId").value = ${requestScope.plan.planId};
            document.getElementById('selectedDates').value = selectedDates.join(',');
            document.getElementById('selectedLocations').value = selectedLocations.join(',');

            console.log(planName, selectedDates.join(','), selectedLocations.join(','));

            // 폼 제출
            document.getElementById('planForm').submit();
        }
    </script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<div>
    <div class="planPageTitle">
        <h1 class="planTitle">여행 일정</h1>
    </div>
    <hr class="planTitleHr">
</div>
<form id="planForm" action="${pageContext.request.contextPath}/plan/update.do" method="POST">
    <div class="nameLocation">
        <div class="name">
            <input placeholder="일정 제목을 입력하세요." type="text" name="planName" size="200" value="${requestScope.plan.planTitle}">
            <input type="hidden" name="planId" id="planId">
            <input type="hidden" name="selectedDates" id="selectedDates">
            <input type="hidden" name="selectedLocations" id="selectedLocations">
            <div class="location">
                <p>여행 지역 선택</p>
                <div class="planLocation">
                    <div class="seoul" onclick="clickLoc('서울')"><div class="clickSeoul"></div><p class="locName">서울</p></div>
                    <div class="gyeonggi" onclick="clickLoc('경기')"><div class="clickGyeonggi"></div><p class="locName">경기</p></div>
                    <div class="busan" onclick="clickLoc('부산')"><div class="clickBusan"></div><p class="locName">부산</p></div>
                    <div class="incheon" onclick="clickLoc('인천')"><div class="clickIncheon"></div><p class="locName">인천</p></div>
                    <div class="jeju" onclick="clickLoc('제주')"><div class="clickJeju"></div><p class="locName">제주</p></div>
                    <div class="gangwon" onclick="clickLoc('강원')"><div class="clickGangwon"></div><p class="locName">강원</p></div>
                    <div class="jeolla" onclick="clickLoc('전라')"><div class="clickJeolla"></div><p class="locName">전라</p></div>
                    <div class="gyeongsang" onclick="clickLoc('경상')"><div class="clickGyeongsang"></div><p class="locName">경상</p></div>
                    <div class="chungcheong" onclick="clickLoc('충청')"><div class="clickChungcheong"></div><p class="locName">충청</p></div>
                </div>
            </div>
        </div>
        <div class="calendar-container">
            <div id="calendar1"></div>
            <div id="calendar2"></div>
        </div>
    </div>
    <div class="btnDiv">
        <button type="button" class="nextBtn" onclick="goToNext()">다음</button>
    </div>
</form>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>