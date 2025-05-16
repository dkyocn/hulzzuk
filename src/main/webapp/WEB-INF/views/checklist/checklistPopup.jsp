<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>checklist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/checklist/checklist.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
    <script  type="text/javascript">
        let checklistVO = []; // 체크리스트 데이터를 저장할 배열
        let isEditMode = false; // 편집 모드 상태

        // 체크리스트를 초기화하는 함수
        async function initializeChecklist() {
            try {
                const response = await $.ajax({
                    url: '${pageContext.request.contextPath}/chkList/voList.do',
                    type: 'GET',
                    data: {planId:  ${requestScope.planId}},
                    dataType: 'json'
                });
                checklistVO = response.checklistList || [];
                renderChecklist();
            } catch (error) {
                console.error('체크리스트 초기화 실패:', error);
                alert('체크리스트를 불러오는 데 실패했습니다.');
            }
        }

        // 체크리스트를 렌더링하는 함수
        function renderChecklist() {
            ['submenu1', 'submenu2', 'submenu3'].forEach(submenuId => {
                const submenu = document.getElementById(submenuId);
                if (submenu) {
                    submenu.querySelectorAll('.item-row').forEach(row => row.remove());
                    const list = checklistVO.filter(item => item.checkTitle === submenuId.slice(-1));
                    list.forEach(item => {
                        const itemRow = document.createElement('div');
                        itemRow.className = 'item-row';
                        itemRow.setAttribute('data-id', item.checkId);
                        const checkedAttr = item.checkYN ? 'checked' : '';
                        itemRow.innerHTML = `<div><input type="checkbox"`+checkedAttr+` onchange="updateCheckYN(`+item.checkId+`, this.checked)">`+item.checkContent+`</div><button onclick="deleteItem(this, `+item.checkId+`)">삭제</button>`;
                        submenu.insertBefore(itemRow, submenu.lastElementChild);
                    });
                }
            });
            attachCheckboxListeners();
        }

        // 배열을 check_title과 check_id 기준으로 정렬하는 함수
        function sortByCheckTitleAndCheckId(array) {
            return [...array].sort((a, b) => {
                const titleA = a.checkTitle || '';
                const titleB = b.checkTitle || '';
                if (titleA !== titleB) return titleA.localeCompare(titleB);
                const idA = Number(a.checkId) || 0;
                const idB = Number(b.checkId) || 0;
                return idA - idB;
            });
        }

        // 두 배열이 동일한지 비교하는 함수 (Deep Equal)
        function isEqual(arr1, arr2) {
            if (arr1.length !== arr2.length) return false;
            return arr1.every((item, index) =>
                item.checkId === arr2[index].checkId &&
                item.checkContent === arr2[index].checkContent &&
                item.checkYN === arr2[index].checkYN &&
                item.checkTitle === arr2[index].checkTitle
            );
        }

        // 최신 체크리스트 데이터 가져오기
        async function fetchUpdatedChecklist() {
            try {
                const response = await $.ajax({
                    url: '${pageContext.request.contextPath}/chkList/voList.do',
                    type: 'GET',
                    data: {planId:  ${requestScope.planId}},
                    dataType: 'json'
                });
                return { checklistList: response.checklistList || [] };
            } catch (error) {
                console.error('체크리스트 갱신 실패:', error);
                return { checklistList: [] };
            }
        }

        document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('.menu-item').forEach(item => {
                item.addEventListener('click', () => {
                    const submenuId = item.getAttribute('data-sub');
                    const submenu = document.getElementById(submenuId);
                    const isOpen = submenu.classList.contains('open');
                    document.querySelectorAll('.submenu').forEach(s => s.classList.remove('open'));
                    document.querySelectorAll('.menu-item').forEach(i => i.classList.remove('open'));
                    if (!isOpen) {
                        submenu.classList.add('open');
                        item.classList.add('open');
                    }
                });
            });

            initializeChecklist();
        });

        function attachCheckboxListeners() {
            document.querySelectorAll('.item-row input[type="checkbox"]').forEach(checkbox => {
                checkbox.removeEventListener('change', handleCheckboxChange);
                checkbox.addEventListener('change', handleCheckboxChange);
            });
        }

        function handleCheckboxChange() {
            const checkId = this.parentElement.parentElement.getAttribute('data-id');
            const checkYN = this.checked;
            updateCheckYN(checkId, checkYN);
        }

        // 전역 스코프에 addItem 함수 정의
        function addItem(submenuId) {
            const submenu = document.getElementById('submenu'+submenuId);
            const input = document.getElementById('addInput' + ('submenu'+submenuId).slice(-1));
            const itemText = input.value.trim();
            if (itemText) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/chkList/create.do',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ // 데이터를 JSON 문자열로 변환
                        checkTitle: submenuId,
                        checkContent: itemText,
                        planId: ${requestScope.planId},
                        checkYN: false // 기본값 설정
                    }),
                    dataType: 'json',
                    success: function(response) {
                        const itemRow = document.createElement('div');
                        itemRow.className = 'item-row';
                        itemRow.setAttribute('data-id', response.checkId);
                        itemRow.innerHTML = `<div><input type="checkbox" >`+ response.checkContent+`</div><button onclick="deleteItem(this,`+ response.checkId+`)">삭제</button>`;
                        submenu.insertBefore(itemRow, submenu.lastElementChild);
                        input.value = '';
                        attachCheckboxListeners();
                    },
                    error: function(xhr, status, error) {
                        console.error('항목 추가 실패:', error);
                        alert('항목 추가에 실패했습니다.');
                    }
                });
            }
        }

        function deleteItem(button, itemId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/chkList/delete.do',
                type: 'POST',
                data: { checkId: itemId },
                dataType: 'json',
                success: function(response) {
                    if (response.status === "success") {
                        button.parentElement.remove();
                    } else {
                        alert('항목 삭제에 실패했습니다.');
                    }
                },
                error: function(xhr, status, error) {
                    console.error('삭제 실패:', status, error, xhr.responseText);
                    alert('항목 삭제에 실패했습니다. 오류: ' + xhr.responseText);
                }
            });
        }

        function updateCheckYN(checkId, checkYN) {
            $.ajax({
                url: '${pageContext.request.contextPath}/chkList/update.do',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    checkId: checkId,
                    checkYN: checkYN
                }),
                dataType: 'json',
                success: function(response) {
                    if (response.status !== "success") {
                        alert('상태 업데이트에 실패했습니다.');
                    }
                },
                error: function(xhr, status, error) {
                    console.error('상태 업데이트 실패:', status, error, xhr.responseText);
                    alert('상태 업데이트에 실패했습니다. 오류: ' + xhr.responseText);
                }
            });
        }

        // 3초마다 체크리스트 갱신
        setInterval(async () => {
            if (!isEditMode) { // 편집 모드가 아닌 경우에만 실행
                const updatedData = await fetchUpdatedChecklist();
                console.log("Fetched updatedData.checklistList:", updatedData.checklistList);

                if (updatedData && updatedData.checklistList) {
                    const sortedChecklistVO = sortByCheckTitleAndCheckId(checklistVO);
                    const isDataChanged = !isEqual(sortedChecklistVO, updatedData.checklistList);

                    if (isDataChanged) {
                        checklistVO.length = 0; // 기존 데이터 초기화
                        updatedData.checklistList.forEach(item => checklistVO.push(item));
                        await initializeChecklist(); // 업데이트된 데이터로 재초기화
                        console.log('UI 갱신');
                        renderChecklist(); // UI만 갱신
                        console.log("checklistVO updated:", checklistVO);
                    } else {
                        console.log("No changes detected, skipping update.");
                    }
                }
            }
        }, 3000); // 3000ms = 3초
    </script>
</head>
<body>
<div>
    <div class="checklistPageTitle">
        <h1 class="checklistTitle">체크리스트</h1>
    </div>
    <hr class="checklistTitleHr">
</div>
<div class="menu-item" data-sub="submenu1">
    필수 준비물 <img src="${pageContext.request.contextPath}/resources/images/common/down-arrow.png" alt="arrow">
</div>
<div class="submenu" id="submenu1">
    <c:if test="${not empty requestScope.checklistList1}">
        <c:forEach var="item" items="${requestScope.checklistList1}">
            <div class="item-row" data-id="${item.checkId}">
                <div>
                    <input type="checkbox" ${item.checkYN ? 'checked' : ''} onchange="updateCheckYN(${item.checkId}, this.checked)"> ${item.checkContent}
                </div>
                 <button onclick="deleteItem(this, ${item.checkId})">삭제</button>
            </div>
        </c:forEach>
    </c:if>
    <div class="add-item"><input type="text" id="addInput1" placeholder="항목 추가"><button onclick="addItem('1')">추가</button></div>
</div>
<div class="menu-item" data-sub="submenu2">
    기본 준비물 <img src="${pageContext.request.contextPath}/resources/images/common/down-arrow.png" alt="arrow">
</div>
<div class="submenu" id="submenu2">
    <c:if test="${not empty requestScope.checklistList2}">
        <c:forEach var="item" items="${requestScope.checklistList2}">
            <div class="item-row" data-id="${item.checkId}">
                <div>
                    <input type="checkbox" ${item.checkYN ? 'checked' : ''} onchange="updateCheckYN(${item.checkId}, this.checked)"> ${item.checkContent}
                </div>
                <button onclick="deleteItem(this, ${item.checkId})">삭제</button>
            </div>
        </c:forEach>
    </c:if>
    <div class="add-item"><input type="text" id="addInput2" placeholder="항목 추가"><button onclick="addItem('2')">추가</button></div>
</div>
<div class="menu-item" data-sub="submenu3">
    즐길거리 준비물 <img src="${pageContext.request.contextPath}/resources/images/common/down-arrow.png" alt="arrow">
</div>
<div class="submenu" id="submenu3">
    <c:if test="${not empty requestScope.checklistList3}">
        <c:forEach var="item" items="${requestScope.checklistList3}">
            <div class="item-row" data-id="${item.checkId}">
                <div>
                    <input type="checkbox" ${item.checkYN ? 'checked' : ''} onchange="updateCheckYN(${item.checkId}, this.checked)"> ${item.checkContent}
                </div>
                <button onclick="deleteItem(this, ${item.checkId})">삭제</button>
            </div>
        </c:forEach>
    </c:if>
    <div class="add-item"><input type="text" id="addInput3" placeholder="항목 추가"><button onclick="addItem('3')">추가</button></div>
</div>
</body>
</html>
