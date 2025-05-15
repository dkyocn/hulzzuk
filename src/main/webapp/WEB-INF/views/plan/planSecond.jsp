<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="planLocVO" value="${requestScope.planLoc}"/>
<c:set var="planVO" value="${requestScope.plan}"/>
<!DOCTYPE html>
<html>
<head>
    <title>hulzzuk</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/plan/createPlanSecond.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d9ed74efa0b6e99b6f0e99ac22f40a71"></script>
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", async function () {
            window.planId = "${requestScope.plan.planId}";

            const addLocBtn = document.querySelector(".addLocBtn");
            const locImg = addLocBtn.querySelector(".locImg");
            const locText = addLocBtn.querySelector(".locText");

            const checklistBtn = document.querySelector(".checklistBtn");
            const checkListText = checklistBtn.querySelector(".checkListText");
            const checkListImg = checklistBtn.querySelector(".checkListImg");

            const editBtn = document.querySelector(".modifiedBtn");
            const shareBtn = document.querySelector(".shareBtn");

            const contextPath = "${pageContext.request.contextPath}";
            const defaultImgSrc = contextPath + "/resources/images/common/add-button-black.png";
            const hoverImgSrc = contextPath + "/resources/images/common/add-button-orange.png";
            const editImgSrc = "${pageContext.request.contextPath}/resources/images/common/edit.png";
            const editHoverImgSrc = "${pageContext.request.contextPath}/resources/images/common/edit-orange.png";
            const defaultTextColor = "#000000";
            const hoverTextColor = "#E96A18";

            editBtn.innerHTML = `<img class="editImg" src="${pageContext.request.contextPath}/resources/images/common/edit.png" style="width: 32px; height: 32px;">`;
            editBtn.addEventListener("mouseover", function () {
                editBtn.querySelector(".editImg").src = editHoverImgSrc;
            });
            editBtn.addEventListener("mouseout", function () {
                editBtn.querySelector(".editImg").src = editImgSrc;
            });

            addLocBtn.addEventListener("mouseover", function () {
                locImg.src = hoverImgSrc;
                locText.style.color = hoverTextColor;
            });
            addLocBtn.addEventListener("mouseout", function () {
                locImg.src = defaultImgSrc;
                locText.style.color = defaultTextColor;
            });

            checklistBtn.addEventListener("mouseover", function () {
                checkListText.style.color = hoverTextColor;
                checkListImg.src = hoverImgSrc;
            });
            checklistBtn.addEventListener("mouseout", function () {
                checkListText.style.color = defaultTextColor;
                checkListImg.src = defaultImgSrc;
            });

            function toggleEditButton() {
                const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                console.log("Added Locations Count:", addedLocations.length, "isEditMode:", isEditMode);
                if (addedLocations.length > 0) {
                    editBtn.style.display = "block";
                    console.log("Edit button displayed");
                } else {
                    editBtn.style.display = "none";
                    console.log("Edit button hidden");
                    if (isEditMode && addedLocations.length === 0) {
                        console.log("Toggling edit mode due to zero locations");
                        toggleEditMode();
                    }
                }
                updateMapVisibility();
            }

            let map = null;
            let markerMap = new Map();
            let polyline = null;
            let lat = 126.52805852098426;
            let lng = 33.51625415483425;
            let isMapInitialized = false;
            window.selectedDay = "day1";
            window.day1Locations = [];
            window.day2Locations = [];

            // planLocVO를 JavaScript 변수로 선언
            const planLocVO = <c:choose>
                <c:when test="${not empty planLocVO}">
                [
                    <c:forEach var="loc" items="${planLocVO}" varStatus="status">
                    {
                        "planId": ${loc.planId},
                        "accoId": "${fn:escapeXml(loc.accoId != null ? loc.accoId : '')}",
                        "attrId": "${fn:escapeXml(loc.attrId != null ? loc.attrId : '')}",
                        "restId": "${fn:escapeXml(loc.restId != null ? loc.restId : '')}",
                        "seq": ${loc.seq},
                        "planDay": ${loc.planDay}
                    }<c:if test="${!status.last}">,</c:if>
                    </c:forEach>
                ]
            </c:when>
            <c:otherwise>
            null
            </c:otherwise>
            </c:choose>;

            // 초기화 함수
            async function initializeLocations() {
                if (planLocVO && Array.isArray(planLocVO)) {
                    try {
                        // seq 기준으로 정렬
                        planLocVO.sort((a, b) => a.seq - b.seq);

                        // 비동기적으로 각 장소 처리
                        window.day1Locations = [];
                        window.day2Locations = [];
                        for (const loc of planLocVO) {
                            let id = null;
                            let locEnum = null;
                            if (loc.accoId) {
                                id = loc.accoId;
                                locEnum = "ACCO";
                            } else if (loc.attrId) {
                                id = loc.attrId;
                                locEnum = "ATTR";
                            } else if (loc.restId) {
                                id = loc.restId;
                                locEnum = "REST";
                            } else {
                                console.warn("Invalid location: no valid ID found", loc);
                                continue;
                            }

                            let placeName = "Unknown";
                            let category = "Unknown";
                            try {
                                const response = await fetch(contextPath + `/loc/getLocation.do?locId=` + id + `&locationEnum=` + locEnum);
                                const data = await response.json();
                                if (data && data.locationVo && data.locationVo.placeName && data.category) {
                                    placeName = data.locationVo.placeName.trim();
                                    category = data.category.trim();
                                } else {
                                    console.warn("Failed to fetch location details for ID:", id);
                                }
                            } catch (error) {
                                console.error("Error fetching location details:", error);
                            }

                            const locationData = {
                                id: String(id),
                                locEnum: String(locEnum),
                                placeName: placeName,
                                category: category,
                                day: loc.planDay === 1 ? "day1" : "day2"
                            };

                            if (loc.planDay === 1) {
                                day1Locations.push(locationData);
                            } else if (loc.planDay === 2) {
                                day2Locations.push(locationData);
                            } else {
                                console.warn("Invalid planDay value:", loc.planDay);
                            }
                        }

                        console.log("Initialized day1Locations:", day1Locations);
                        console.log("Initialized day2Locations:", day2Locations);

                        // 초기 렌더링
                        renderLocations();
                        await updateNumbersAndDistances();
                        await updateMapVisibility();
                    } catch (error) {
                        console.error("Error processing planLocVO:", error);
                    }
                } else {
                    console.warn("planLocVO is null or not an array:", planLocVO);
                }
            }

            // 업데이트된 장소 데이터를 가져오는 함수
            async function fetchUpdatedLocations() {
                try {
                    const response = await fetch(contextPath + `/plan/getPlLocations.do?planId=` + window.planId, {
                        method: 'GET',
                        headers: { 'Content-Type': 'application/json' }
                    });
                    if (!response.ok) throw new Error('Failed to fetch updated locations');
                    const updatedData = await response.json();
                    return updatedData; // 예상: { planLocVO: [...] }
                } catch (error) {
                    console.error("Error fetching updated locations:", error);
                    return null;
                }
            }

            // 두 배열을 깊이 비교하는 함수
            function isEqual(array1, array2) {
                if (array1.length !== array2.length) return false;

                return array1.every((item1, index) => {
                    const item2 = array2[index];
                    // 각 속성 비교 (planId, accoId, attrId, restId, seq, planDay)
                    return item1.planId === item2.planId &&
                        item1.accoId === item2.accoId &&
                        item1.attrId === item2.attrId &&
                        item1.restId === item2.restId &&
                        item1.seq === item2.seq &&
                        item1.planDay === item2.planDay;
                });
            }

            // 배열을 planDay와 seq 기준으로 정렬하는 함수
            function sortByPlanDayAndSeq(array) {
                return [...array].sort((a, b) => {
                    if (a.planDay !== b.planDay) return a.planDay - b.planDay;
                    return a.seq - b.seq;
                });
            }

            // editMode가 false일 때 3초마다 planLocList 갱신
            setInterval(async () => {
                if (!isEditMode) { // editMode가 false일 때만 실행
                    const updatedData = await fetchUpdatedLocations();
                    console.log("Fetched updatedData.planLoc:", planLocVO);
                    console.log("Fetched updatedData.planLoc:", updatedData.planLoc);

                    if (updatedData && updatedData.planLoc) {
                        // 기존 planLocVO와 새로운 updatedData.planLoc 비교
                        const sortedPlanLocVO = sortByPlanDayAndSeq(planLocVO);
                        const isDataChanged = !isEqual(sortedPlanLocVO, updatedData.planLoc);

                        if (isDataChanged) {
                            planLocVO.length = 0; // 기존 데이터 초기화
                            updatedData.planLoc.forEach(loc => planLocVO.push(loc));
                            await initializeLocations(); // 업데이트된 데이터로 재초기화
                            console.log('UI 갱신');
                            renderLocations(); // UI만 갱신
                            await updateNumbersAndDistances(); // 거리 계산 업데이트
                            await updateMapVisibility(); // 맵 업데이트
                            console.log("planLocList updated:", planLocVO);
                        } else {
                            console.log("No changes detected, skipping update.");
                        }
                    }
                }
            }, 3000); // 3000ms = 3초

            // 초기 실행
            await initializeLocations();

            navigator.geolocation.getCurrentPosition(function(position) {
                lat = position.coords.latitude;
                lng = position.coords.longitude;
                console.log("User location:", lat, lng);
            }, function(error) {
                console.error("Geolocation error:", error);
            });

            function initMap() {
                const container = document.querySelector('.map');
                if (!container || container.style.display === "none" || isMapInitialized) return;
                const options = { center: new kakao.maps.LatLng(lat, lng), level: 3 };
                map = new kakao.maps.Map(container, options);
                console.log("Map initialized at:", lat, lng);
                isMapInitialized = true;
            }

            async function updateMapVisibility() {
                const mapDiv = document.querySelector(".map");
                const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                if (mapDiv) {
                    if (isEditMode) {
                        mapDiv.style.display = "none";
                        console.log("Map hidden due to edit mode");
                        return;
                    }
                    mapDiv.style.display = locations.length > 0 ? "block" : "none";
                    console.log("Map visibility set to:", locations.length > 0 ? "block" : "none", "for", selectedDay);
                    if (locations.length > 0 && !isMapInitialized) {
                        console.log("Map is now visible, initializing map");
                        initMap();
                    }
                    await addMarkersForLocations();
                    if (locations.length > 0) {
                        const firstLocation = locations[0];
                        const response = await fetch(contextPath + `/loc/getLocation.do?locId=` + firstLocation.id + `&locationEnum=` + firstLocation.locEnum);
                        const data = await response.json();
                    } else if (map) {
                        markerMap.forEach(marker => marker.setMap(null));
                        markerMap.clear();
                        if (polyline) polyline.setMap(null);
                        polyline = null;
                        isMapInitialized = false;
                    }
                }
            }

            async function addMarkersForLocations() {
                if (!map) return;
                markerMap.forEach(marker => marker.setMap(null));
                markerMap.clear();
                if (polyline) polyline.setMap(null);
                polyline = null;

                const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                if (locations.length === 0) return;

                const coordinates = [];
                const bounds = new kakao.maps.LatLngBounds(); // 모든 마커를 포함할 경계 객체 생성

                for (let index = 0; index < locations.length; index++) {
                    const location = locations[index];
                    const response = await fetch(contextPath + `/loc/getLocation.do?locId=` + location.id + `&locationEnum=` + location.locEnum);
                    const data = await response.json();
                    if (data && data.locationVo && data.locationVo.x && data.locationVo.y) {
                        const position = new kakao.maps.LatLng(data.locationVo.y, data.locationVo.x);
                        const markerImage = await createNumberedMarkerImage(index + 1, selectedDay);
                        const marker = new kakao.maps.Marker({ map, position, title: data.locationVo.placeName || "장소", image: markerImage });
                        markerMap.set(location.id, marker);
                        coordinates.push(position);
                        bounds.extend(position); // 각 마커의 좌표를 경계에 추가
                    }
                }
                if (coordinates.length >= 2) {
                    polyline = new kakao.maps.Polyline({ path: coordinates, strokeWeight: 2, strokeColor: selectedDay === 'day1' ? '#E96A18' : '#408c3b', strokeOpacity: 0.7, strokeStyle: 'solid' });
                    polyline.setMap(map);
                }

                // 모든 마커가 보이도록 지도 뷰 조정
                if (!bounds.isEmpty()) {
                    map.setBounds(bounds); // 경계를 기반으로 지도 뷰 조정
                } else if (coordinates.length === 1) {
                    // 마커가 하나일 경우 해당 위치로 지도 중심 이동
                    map.setCenter(coordinates[0]);
                }
            }

            async function createNumberedMarkerImage(number, day) {
                return new Promise((resolve) => {
                    console.log(number, day);
                    const defaultMarkerImageUrl = day === 'day1' ? "${pageContext.request.contextPath}/resources/images/common/location.png" : "${pageContext.request.contextPath}/resources/images/common/location-green.png";
                    const img = new Image();
                    img.src = defaultMarkerImageUrl;
                    img.crossOrigin = "Anonymous";
                    img.onload = () => {
                        const canvas = document.createElement("canvas");
                        const ctx = canvas.getContext("2d");
                        canvas.width = 35;
                        canvas.height = 40;
                        ctx.drawImage(img, 0, 0, 35, 40);
                        ctx.font = "bold 14px Noto Sans";
                        ctx.fillStyle = "white";
                        ctx.textAlign = "center";
                        ctx.textBaseline = "middle";
                        ctx.strokeStyle = "black";
                        ctx.lineWidth = 2;
                        ctx.strokeText(number, 17.5, 20);
                        ctx.fillText(number, 17.5, 20);
                        resolve(new kakao.maps.MarkerImage(canvas.toDataURL("image/png"), new kakao.maps.Size(35, 40)));
                    };
                    img.onerror = () => resolve(new kakao.maps.MarkerImage(defaultMarkerImageUrl, new kakao.maps.Size(24, 35)));
                });
            }

            window.addEventListener("message", async function (event) {
                if (event.origin !== window.location.origin) return;
                const { id, locEnum } = event.data;
                const response = await fetch(contextPath + `/loc/getLocation.do?locId=` + id + `&locationEnum=` + locEnum);
                const data = await response.json();
                if (data && data.locationVo && data.locationVo.placeName && data.category) {
                    const locationData = { id: String(id), locEnum: String(locEnum), placeName: data.locationVo.placeName.trim(), category: data.category.trim(), day: selectedDay };
                    (selectedDay === "day1" ? day1Locations : day2Locations).push(locationData);
                    console.log("Added location to", selectedDay, locationData);
                    renderLocations();
                    await updateNumbersAndDistances();
                    await updateMapVisibility();
                }
            });

            function renderLocations() {
                const planLocList = document.querySelector(".planLocList");
                const subDiv = document.querySelector(".subDiv");
                if (!planLocList || !subDiv) {
                    console.error("planLocList or subDiv not found");
                    return;
                }

                Array.from(planLocList.children).forEach(child => {
                    if (child !== subDiv) child.remove();
                });

                const locations = selectedDay === "day1" ? day1Locations : day2Locations;

                locations.forEach((location, index) => {
                    const newDiv = document.createElement("div");
                    newDiv.className = "addedLocation";
                    newDiv.dataset.id = location.id;
                    newDiv.dataset.locEnum = location.locEnum;
                    newDiv.dataset.day = selectedDay;

                    const innerDiv = document.createElement("div");
                    innerDiv.className = "innerDiv";

                    const element = isEditMode ? document.createElement("input") : document.createElement("span");
                    if (isEditMode) {
                        element.type = "checkbox";
                        element.className = "location-checkbox";
                    } else {
                        element.className = "locationNumber";
                        element.textContent = (index + 1) + " ";
                    }

                    const addLocTextDiv = document.createElement("div");
                    addLocTextDiv.className = "addLocTextDiv";

                    const p1 = document.createElement("p");
                    p1.textContent = location.placeName;
                    p1.className = "p1";
                    const p2 = document.createElement("p");
                    p2.textContent = location.category;
                    p2.className = "p2";

                    const button = document.createElement("button");
                    button.className = "removeBtn";
                    button.textContent = "삭제";
                    button.style.display = isEditMode ? "inline-block" : "none";

                    addLocTextDiv.appendChild(p1);
                    addLocTextDiv.appendChild(p2);
                    innerDiv.appendChild(element);
                    innerDiv.appendChild(addLocTextDiv);
                    newDiv.appendChild(innerDiv);
                    newDiv.appendChild(button);
                    planLocList.insertBefore(newDiv, subDiv);

                    if (isEditMode) {
                        newDiv.setAttribute("draggable", "true");
                        newDiv.addEventListener("dragstart", handleDragStart);
                        newDiv.addEventListener("dragover", handleDragOver);
                        newDiv.addEventListener("drop", handleDrop);
                        newDiv.addEventListener("dragend", handleDragEnd);
                    }
                });

                updateMapVisibility();
            }

            function openLocationPopup() {
                window.open(contextPath + '/loc/list.do?locationEnum=ALL', 'locationListPopup', 'width=500,height=800');
            }

            addLocBtn.addEventListener("click", openLocationPopup);

            let isEditMode = false;
            let draggedItem = null;

            async function toggleEditMode() {
                isEditMode = !isEditMode;
                const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                const distanceItems = document.querySelectorAll(".distanceItem");
                console.log("Toggling Edit Mode:", isEditMode, "Locations:", addedLocations.length);

                if (isEditMode) {
                    editBtn.removeEventListener("mouseover", editBtn._mouseover);
                    editBtn.removeEventListener("mouseout", editBtn._mouseout);
                    editBtn.innerHTML = "";
                    editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done.png")`;
                    editBtn.style.backgroundSize = "cover";
                    editBtn.style.width = "32px";
                    editBtn.style.height = "32px";
                    editBtn._mouseover = () => editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done-orange.png")`;
                    editBtn._mouseout = () => editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done.png")`;
                    editBtn.addEventListener("mouseover", editBtn._mouseover);
                    editBtn.addEventListener("mouseout", editBtn._mouseout);

                    shareBtn.classList.remove("shareBtn");
                    shareBtn.classList.add("deleteBtn");
                    shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete.png")`;
                    shareBtn.removeEventListener("mouseover", shareBtn._mouseover);
                    shareBtn.removeEventListener("mouseout", shareBtn._mouseout);
                    shareBtn._mouseover = () => shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete-orange.png")`;
                    shareBtn._mouseout = () => shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete.png")`;
                    shareBtn.addEventListener("mouseover", shareBtn._mouseover);
                    shareBtn.addEventListener("mouseout", shareBtn._mouseout);

                    distanceItems.forEach(item => item.style.display = "none");
                    addLocBtn.style.display = "none";

                    renderLocations();

                    isMapInitialized = false;
                    if (map) {
                        map = null;
                        markerMap.clear();
                        if (polyline) {
                            polyline.setMap(null);
                            polyline = null;
                        }
                    }
                    updateMapVisibility();
                } else {
                    editBtn.removeEventListener("mouseover", editBtn._mouseover);
                    editBtn.removeEventListener("mouseout", editBtn._mouseout);
                    editBtn.innerHTML = "";
                    editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit.png")`;
                    editBtn.style.backgroundSize = "cover";
                    editBtn.style.width = "32px";
                    editBtn.style.height = "32px";
                    editBtn._mouseover = () => editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit-orange.png")`;
                    editBtn._mouseout = () => editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit.png")`;
                    editBtn.addEventListener("mouseover", editBtn._mouseover);
                    editBtn.addEventListener("mouseout", editBtn._mouseout);

                    shareBtn.classList.remove("deleteBtn");
                    shareBtn.classList.add("shareBtn");
                    shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-black.png")`;
                    shareBtn.removeEventListener("mouseover", shareBtn._mouseover);
                    shareBtn.removeEventListener("mouseout", shareBtn._mouseout);
                    shareBtn._mouseover = () => shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-orange.png")`;
                    shareBtn._mouseout = () => shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-black.png")`;
                    shareBtn.addEventListener("mouseover", shareBtn._mouseover);
                    shareBtn.addEventListener("mouseout", shareBtn._mouseout);

                    distanceItems.forEach(item => item.style.display = "block");
                    addLocBtn.style.display = "flex";

                    renderLocations();
                    await updateNumbersAndDistances();

                    // 편집 모드 종료 시 DB 업데이트
                    const updateData = {
                        planId: window.planId,
                        day1Locations: day1Locations,
                        day2Locations: day2Locations
                    };

                    try {
                        const response = await fetch('${pageContext.request.contextPath}/plan/updateLocations.do', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(updateData)
                        });
                        const data = await response.json();
                        if (data.successYN) {
                            console.log("DB 업데이트 성공");
                        } else {
                            console.error("DB 업데이트 실패:", data.message || "알 수 없는 오류");
                            alert("업데이트에 실패했습니다. 다시 시도해주세요.");
                        }
                    } catch (error) {
                        console.error("업데이트 요청 실패:", error);
                        alert("서버와의 연결에 실패했습니다. 다시 시도해주세요.");
                    }
                }
            }

            function handleDragStart(e) {
                draggedItem = e.target;
                e.target.classList.add("dragging");
                e.stopPropagation();
            }

            function handleDragOver(e) {
                e.preventDefault();
                e.stopPropagation();
            }

            function handleDrop(e) {
                e.preventDefault();
                e.stopPropagation();
                const dropTarget = e.target.closest(".addedLocation");
                if (dropTarget && draggedItem !== dropTarget && dropTarget.dataset.day === selectedDay) {
                    const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                    const draggedIndex = locations.findIndex(loc => loc.id === draggedItem.dataset.id);
                    const dropIndex = locations.findIndex(loc => loc.id === dropTarget.dataset.id);
                    const [dragged] = locations.splice(draggedIndex, 1);
                    locations.splice(dropIndex, 0, dragged);
                    renderLocations();
                    addMarkersForLocations();
                    updateNumbersAndDistances();
                }
            }

            function handleDragEnd(e) {
                e.target.classList.remove("dragging");
                draggedItem = null;
                e.stopPropagation();
            }

            document.addEventListener("click", async function (e) {
                if (e.target.classList.contains("removeBtn")) {
                    const addedLocation = e.target.closest(".addedLocation");
                    if (addedLocation) {
                        const id = addedLocation.dataset.id;
                        const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                        const index = locations.findIndex(loc => loc.id === id);
                        if (index !== -1) {
                            // 단일 삭제 요청 (리스트로 감싸서 전송)
                            const deleteRequests = [{
                                planId: window.planId,
                                locId: id,
                                locEnum: addedLocation.dataset.locEnum,
                                planDay: selectedDay === "day1" ? 1 : 2
                            }];

                            const response = await fetch('${pageContext.request.contextPath}/plan/deleteLocations.do', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify(deleteRequests)
                            });
                            const data = await response.json();
                            if (data.successYN) {
                                locations.splice(index, 1);
                                const marker = markerMap.get(id);
                                if (marker) marker.setMap(null);
                                markerMap.delete(id);
                                renderLocations();
                                await updateMapVisibility();
                                await updateNumbersAndDistances();
                            }
                        }
                    }
                } else if (e.target.classList.contains("deleteBtn")) {
                    const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                    const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                    let deletedCount = 0;
                    // 다중 삭제 요청
                    const deleteRequests = Array.from(addedLocations)
                        .filter(location => location.querySelector(".location-checkbox")?.checked)
                        .map(location => ({
                            planId: window.planId,
                            locId: location.dataset.id,
                            locEnum: location.dataset.locEnum,
                            planDay: selectedDay === "day1" ? 1 : 2
                        }));

                    if (deleteRequests.length > 0) {
                        const response = await fetch('${pageContext.request.contextPath}/plan/deleteLocations.do', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify(deleteRequests)
                        });
                        const data = await response.json();
                        if (data.successYN) {
                            deleteRequests.forEach(request => {
                                const index = locations.findIndex(loc => loc.id === request.locId);
                                if (index !== -1) {
                                    locations.splice(index, 1);
                                    const marker = markerMap.get(request.locId);
                                    if (marker) marker.setMap(null);
                                    markerMap.delete(request.locId);
                                    deletedCount++;
                                }
                            });
                            renderLocations();
                            await updateMapVisibility();
                            if (deletedCount > 0 && locations.length === 0) toggleEditMode();
                            await updateNumbersAndDistances();
                        } else {
                            console.error("DB 삭제 실패:", data.message || "알 수 없는 오류");
                            alert("삭제에 실패했습니다. 다시 시도해주세요.");
                        }
                    }
                } else if (e.target.classList.contains("shareBtn")) {
                    window.open(contextPath + '/plan/moveSharePopUp.do?planId=${requestScope.plan.planId}', 'ShareUserPopup', 'width=350,height=160');
                }
            });

            editBtn.addEventListener("click", toggleEditMode);

            async function updateNumbersAndDistances() {
                const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                const planLocList = document.querySelector(".planLocList");
                const subDiv = document.querySelector(".subDiv");

                const existingDistanceItems = document.querySelectorAll(".distanceItem");
                existingDistanceItems.forEach(item => item.remove());

                if (!isEditMode && locations.length > 1) {
                    for (let index = 0; index < locations.length - 1; index++) {
                        const fromLocation = locations[index];
                        const toLocation = locations[index + 1];
                        const distanceDiv = document.createElement("div");
                        distanceDiv.className = "distanceItem";
                        distanceDiv.textContent = "계산 중...";

                        const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                        if (addedLocations[index]) {
                            addedLocations[index].insertAdjacentElement("afterend", distanceDiv);
                        }

                        const locationMap = {
                            [fromLocation.id]: fromLocation.locEnum,
                            [toLocation.id]: toLocation.locEnum
                        };

                        const distance = await calculateDistance(locationMap);
                        distanceDiv.textContent = distance === "N/A" ? "N/A" : distance + "km";
                    }
                }

                toggleEditButton();
                await updateMapVisibility();
            }

            async function calculateDistance(locationMap) {
                try {
                    const response = await fetch(contextPath + '/loc/getDistance.do', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(locationMap)
                    });
                    if (!response.ok) throw new Error(`Failed to fetch distance: ${response.text()}`);
                    const data = JSON.parse(await response.text());
                    return data && 'distance' in data && !isNaN(data.distance) ? parseFloat(data.distance) : "N/A";
                } catch (error) {
                    console.error("Error fetching distance:", error);
                    return "N/A";
                }
            }

            // 탭 전환 및 초기화 함수
            async function initializeDayTab(day) {
                selectedDay = day;
                console.log("Initializing day:", selectedDay);
                renderLocations();
                await updateMapVisibility();
                await updateNumbersAndDistances();
            }

            const dayElements = document.querySelectorAll(".day1, .day2");
            dayElements.forEach(day => {
                day.addEventListener("click", async function () {
                    dayElements.forEach(d => d.classList.remove("active"));
                    this.classList.add("active");
                    const newDay = this.classList.contains("day1") ? "day1" : "day2";
                    await initializeDayTab(newDay);
                });
            });

            // 초기화: day1 탭 활성화 및 렌더링
            if (dayElements.length > 0) {
                dayElements[0].classList.add("active");
                await initializeDayTab("day1");
            } else {
                console.warn("Day elements not found");
                // day1, day2 탭이 없는 경우에도 기본 렌더링
                renderLocations();
                await updateMapVisibility();
                await updateNumbersAndDistances();
            }

            toggleEditButton();
            await updateMapVisibility();
            window.addEventListener("message", async function () {
                toggleEditButton();
                await updateMapVisibility();
            });

            function openChecklist() {
                window.open(contextPath + '/chkList/list.do?planId=${requestScope.plan.planId}', 'checkListPopup', 'width=700,height=500');
            }
            document.getElementById("checklistBtn").addEventListener("click", openChecklist);
        });

        function modifiedPlanName() {}
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
<div class="planNameDiv">
    <p class="planName">${requestScope.plan.planTitle}</p>
    <c:url var="moveFirst" value="/plan/moveUpdate.do">
        <c:param name="planId" value="${requestScope.plan.planId}"/>
    </c:url>
    <button class="planModified" type="button" onclick="location.href='${moveFirst}'">편집</button>
</div>
<div class="map"></div>
<div class="planDay">
    <div class="planDateTitle">
        <div class="day1"><p class="dayTitle">Day1</p>
            <p class="planDate">
                <fmt:formatDate value="${planVO.planStartDate}" pattern="M.d" var="startDate"/>
                (${startDate})
            </p></div>
        <c:if test="${planVO.planStartDate != planVO.planEndDate}">
            <div class="day2"><p class="dayTitle">Day2</p><p class="planDate">
                <fmt:formatDate value="${planVO.planEndDate}" pattern="M.d" var="endDate"/>
                (${endDate})</p></div>
        </c:if>
        <button class="checklistBtn" id="checklistBtn"><span class="checkListText">체크리스트</span>
            <img class="checkListImg" src="${pageContext.request.contextPath}/resources/images/common/add-button-black.png" alt="">
        </button>
    </div>
    <div class="btnDiv">
        <button class="modifiedBtn" style="display: none;"></button>
        <button class="shareBtn"></button>
    </div>
</div>
<div class="planLocList">
    <div class="subDiv">
        <button class="addLocBtn"><img class="locImg" src="${pageContext.request.contextPath}/resources/images/common/add-button-black.png" alt="" ><span class="locText">장소 추가</span></button>
    </div>
</div>
<form id="saveLocationsForm" action="${pageContext.request.contextPath}/plan/page.do?page=1">
    <div class="saveDiv">
        <button class="saveBtn" type="submit" id="saveBtn">완료</button>
    </div>
</form>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>