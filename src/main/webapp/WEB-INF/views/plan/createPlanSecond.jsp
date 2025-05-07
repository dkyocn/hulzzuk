<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            const addLocBtn = document.querySelector(".addLocBtn");
            const locImg = addLocBtn.querySelector(".locImg");
            const locText = addLocBtn.querySelector(".locText");

            const checklistBtn = document.querySelector(".checklistBtn");
            const checkListText = checklistBtn.querySelector(".checkListText");
            const checkListImg = checklistBtn.querySelector(".checkListImg");

            const editBtn = document.querySelector(".modifiedBtn");
            const shareBtn = document.querySelector(".shareBtn");

            // JSP에서 미리 렌더링된 경로 사용
            const contextPath = "${pageContext.request.contextPath}";
            const defaultImgSrc = contextPath + "/resources/images/common/add-button-black.png";
            const hoverImgSrc = contextPath + "/resources/images/common/add-button-orange.png";
            const editImgSrc = "${pageContext.request.contextPath}/resources/images/common/edit.png";
            const editHoverImgSrc = "${pageContext.request.contextPath}/resources/images/common/edit-orange.png";
            const defaultTextColor = "#000000";
            const hoverTextColor = "#E96A18";

            // 초기 수정 버튼 상태 설정
            editBtn.innerHTML = `<img class="editImg" src="${pageContext.request.contextPath}/resources/images/common/edit.png" style="width: 32px; height: 32px;">`;

            editBtn.addEventListener("mouseover", function () {
                editBtn.querySelector(".editImg").src = editHoverImgSrc;
            });

            editBtn.addEventListener("mouseout", function () {
                editBtn.querySelector(".editImg").src = editImgSrc;
            })

            // 마우스 호버 이벤트
            addLocBtn.addEventListener("mouseover", function () {
                locImg.src = hoverImgSrc;
                locText.style.color = hoverTextColor;
            });
            addLocBtn.addEventListener("mouseout", function () {
                locImg.src = defaultImgSrc;
                locText.style.color = defaultTextColor;
            });

            // 체크리스트 호버 이벤트
            checklistBtn.addEventListener("mouseover", function () {
                checkListText.style.color = hoverTextColor;
                checkListImg.src = hoverImgSrc;
            });

            checklistBtn.addEventListener("mouseout", function () {
                checkListText.style.color = defaultTextColor;
                checkListImg.src = defaultImgSrc;
            });

            // 수정 버튼 표시/숨김 함수
            function toggleEditButton() {
                const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                console.log("Added Locations Count:", addedLocations.length);
                if (addedLocations.length > 0) {
                    editBtn.style.display = "block";
                    console.log("Edit button displayed");
                } else {
                    editBtn.style.display = "none";
                    console.log("Edit button hidden");
                    // 장소가 모두 삭제되었을 때 수정 모드 해제 및 장소 추가 버튼 표시
                    if (isEditMode) {
                        toggleEditMode();
                    }
                }
                updateMapVisibility();
            }

            let map = null; // 지도 객체를 전역으로 관리
            let markerMap = new Map(); // 마커 객체를 전역으로 관리
            let polyline = null;
            let lat = 126.52805852098426;
            let lng = 33.51625415483425;
            let isMapInitialized = false; // 지도 초기화 여부 추적
            let selectedDay = "day1";
            let day1Locations = []; // day1 장소 배열
            let day2Locations = []; // day2 장소 배열

            // 위치 정보 미리 가져오기
            navigator.geolocation.getCurrentPosition(function(position) {
                lat = position.coords.latitude;
                lng = position.coords.longitude;
                console.log("User location:", lat, lng);
                // initMap은 updateMapVisibility에서 호출
            }, function(error) {
                console.error("Geolocation error:", error);
                // 기본 좌표 사용
            });

            function initMap() {
                const container = document.querySelector('.map');
                if (!container) {
                    console.error("Map container not found");
                    return;
                }

                if (container.style.display === "none") {
                    console.log("Map container is hidden, skipping initialization");
                    return;
                }

                if (isMapInitialized) {
                    console.log("Map already initialized, skipping re-initialization");
                    return; // 이미 초기화된 경우 중심 업데이트 불필요
                }

                const options = {
                    center: new kakao.maps.LatLng(lat, lng), // 초기 중심 좌표
                    level: 3
                };

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

                    const shouldDisplay = locations.length > 0;
                    mapDiv.style.display = shouldDisplay ? "block" : "none";
                    console.log("Map visibility set to:", shouldDisplay ? "block" : "none", "for", selectedDay);

                    if (shouldDisplay) {
                        if (!isMapInitialized) {
                            console.log("Map is now visible, initializing map");
                            initMap();
                            isMapInitialized = true;
                        }
                        // 지도 중심과 범위를 강제로 갱신
                        await addMarkersForLocations();
                        if (locations.length > 0) {
                            const firstLocation = locations[0];
                            const response = await fetch(contextPath + `/loc/getLocation.do?locId=`+String(firstLocation.id)+`&locationEnum=`+String(firstLocation.locEnum));
                            const data = await response.json();
                            if (data && data.locationVo && data.locationVo.x && data.locationVo.y) {
                                const center = new kakao.maps.LatLng(data.locationVo.y, data.locationVo.x);
                                map.setCenter(center); // 초기 중심을 첫 번째 장소로 설정
                            }
                        }
                    } else {
                        console.log("Map is hidden or no locations, clearing markers and polyline");
                        if (map) {
                            markerMap.forEach(marker => {
                                if (marker && typeof marker.setMap === "function") {
                                    marker.setMap(null);
                                }
                            });
                            markerMap.clear();
                            if (polyline) {
                                polyline.setMap(null);
                                polyline = null;
                            }
                            // 지도 초기화 상태 리셋
                            isMapInitialized = false;
                        }
                    }
                } else {
                    console.error("Map div not found");
                }
            }

            async function addMarkersForLocations() {
                if (!map) return;

                // 기존 마커와 폴리라인 제거
                markerMap.forEach(marker => {
                    if (marker && typeof marker.setMap === "function") {
                        marker.setMap(null);
                    }
                });
                markerMap.clear();
                if (polyline) {
                    polyline.setMap(null);
                    polyline = null;
                }

                const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                console.log(`Adding markers for ${selectedDay}:`, locations);
                if (locations.length === 0) {
                    console.log("No locations to add markers for", selectedDay);
                    return;
                }

                const coordinates = [];

                // 기본 마커 이미지 로드 (Kakao Maps 기본 이미지)
                const defaultMarkerImageUrl = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
                const imageSize = new kakao.maps.Size(24, 35);

                // 각 장소에 대해 순서대로 마커 생성
                for (let index = 0; index < locations.length; index++) {
                    const location = locations[index];
                    const locId = String(location.id);
                    const locEnum = String(location.locEnum);

                    const response = await fetch(contextPath + `/loc/getLocation.do?locId=`+locId+`&locationEnum=`+locEnum);
                    const data = await response.json();
                    if (data && data.locationVo && data.locationVo.x && data.locationVo.y) {
                        const position = new kakao.maps.LatLng(data.locationVo.y, data.locationVo.x);
                        const title = data.locationVo.placeName || "장소";

                        // 숫자가 포함된 커스텀 마커 이미지 생성
                        const markerImage = await createNumberedMarkerImage(index + 1);

                        const marker = new kakao.maps.Marker({
                            map: map,
                            position: position,
                            title: title,
                            image: markerImage
                        });
                        markerMap.set(locId, marker);
                        coordinates.push(position);

                        if (markerMap.size === 1) {
                            map.setCenter(position);
                        }
                    } else {
                        console.error("Invalid location data for marker:", data, "for location:", location);
                    }
                }

                // 좌표가 2개 이상일 경우 선 그리기
                if (coordinates.length >= 2) {
                    const bounds = new kakao.maps.LatLngBounds();
                    coordinates.forEach(coord => bounds.extend(coord));
                    map.setBounds(bounds);

                    polyline = new kakao.maps.Polyline({
                        path: coordinates,
                        strokeWeight: 2,
                        strokeColor: '#E96A18',
                        strokeOpacity: 0.7,
                        strokeStyle: 'solid'
                    });
                    polyline.setMap(map);
                    console.log("Polyline added connecting", coordinates.length, "locations for", selectedDay);
                } else {
                    console.log("Not enough coordinates to draw a polyline (need at least 2 locations) for", selectedDay);
                }
            }

            // 숫자가 포함된 마커 이미지를 생성하는 함수
            async function createNumberedMarkerImage(number) {
                return new Promise((resolve) => {
                    // 기본 마커 이미지 로드
                    const defaultMarkerImageUrl = "${pageContext.request.contextPath}/resources/images/common/point.png";
                    const img = new Image();
                    img.src = defaultMarkerImageUrl;
                    img.crossOrigin = "Anonymous"; // CORS 문제 방지

                    img.onload = () => {
                        // Canvas 생성
                        const canvas = document.createElement("canvas");
                        const ctx = canvas.getContext("2d");

                        // 마커 이미지 크기 설정
                        const width = 35;
                        const height = 40;
                        canvas.width = width;
                        canvas.height = height;

                        // 기본 마커 이미지 그리기
                        ctx.drawImage(img, 0, 0, width, height);

                        // 숫자 스타일 설정
                        ctx.font = "bold 14px Noto Sans";
                        ctx.fillStyle = "white";
                        ctx.textAlign = "center";
                        ctx.textBaseline = "middle";

                        // 숫자 위치 조정 (마커 중앙에 표시)
                        const textX = width / 2;
                        const textY = height / 2; // 약간 위로 조정

                        // 숫자에 검은색 테두리 추가 (가독성 향상)
                        ctx.strokeStyle = "black";
                        ctx.lineWidth = 2;
                        ctx.strokeText(number, textX, textY);

                        // 숫자 채우기
                        ctx.fillText(number, textX, textY);

                        // Canvas 이미지를 URL로 변환
                        const imageUrl = canvas.toDataURL("image/png");

                        // Kakao Maps MarkerImage 생성
                        const markerImage = new kakao.maps.MarkerImage(
                            imageUrl,
                            new kakao.maps.Size(width, height)
                        );

                        resolve(markerImage);
                    };

                    img.onerror = () => {
                        console.error("Failed to load marker image");
                        // 오류 시 기본 마커 이미지 반환
                        resolve(new kakao.maps.MarkerImage(
                            defaultMarkerImageUrl,
                            new kakao.maps.Size(24, 35)
                        ));
                    };
                });
            }

            // 팝업에서 id 수신 및 장소 추가
            window.addEventListener("message", async function (event) {
                if (event.origin !== window.location.origin) return;
                const { id, locEnum } = event.data;

                fetch(contextPath + '/loc/getLocation.do?locId=' + id + "&locationEnum=" + locEnum)
                    .then(response => response.json())
                    .then(data => {
                        if (data && data.locationVo && data.locationVo.placeName && data.category) {
                            const placeName = String(data.locationVo.placeName).trim();
                            const category = String(data.category).trim();

                            const locationData = {
                                id: String(id),
                                locEnum: String(locEnum),
                                placeName,
                                category,
                                day: selectedDay
                            };

                            if (selectedDay === "day1") {
                                day1Locations.push(locationData);
                            } else {
                                day2Locations.push(locationData);
                            }

                            console.log("Added location to", selectedDay, locationData);
                            console.log("day1Locations:", day1Locations, "day2Locations:", day2Locations);

                            renderLocations();
                            updateNumbersAndDistances();
                            updateMapVisibility();
                        } else {
                            console.error("Invalid location data:", data);
                        }
                    })
                    .catch(error => console.error("Error fetching location details:", error));
            });

            function renderLocations() {
                const planLocList = document.querySelector(".planLocList");
                const subDiv = document.querySelector(".subDiv");
                if (!planLocList || !subDiv) {
                    console.error("planLocList or subDiv not found");
                    return;
                }

                // 기존 장소 목록 제거 (subDiv 제외)
                Array.from(planLocList.children).forEach(child => {
                    if (child !== subDiv) child.remove();
                });

                // 선택된 날짜의 배열 선택
                const locations = selectedDay === "day1" ? day1Locations : day2Locations;

                // 장소 렌더링
                locations.forEach((location, index) => {
                    const newDiv = document.createElement("div");
                    newDiv.className = "addedLocation";
                    newDiv.dataset.id = location.id;
                    newDiv.dataset.locEnum = location.locEnum;
                    newDiv.dataset.day = selectedDay;

                    const innerDiv = document.createElement("div");
                    innerDiv.className = "innerDiv";

                    const numberSpan = document.createElement("span");
                    numberSpan.className = "locationNumber";
                    numberSpan.textContent = (index + 1) + " ";

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
                    innerDiv.appendChild(numberSpan);
                    innerDiv.appendChild(addLocTextDiv);

                    newDiv.appendChild(innerDiv);
                    newDiv.appendChild(button);

                    planLocList.insertBefore(newDiv, subDiv);
                });

                updateNumbersAndDistances();
                updateMapVisibility(); // 지도 업데이트 추가
            }

            // 팝업 열기
            function openLocationPopup() {
                window.open(contextPath + '/loc/list.do?locationEnum=ALL', 'locationListPopup', 'width=500,height=800');
            }

            addLocBtn.addEventListener("click", openLocationPopup);

            let isEditMode = false;
            let draggedItem = null;

            // 수정/삭제 모드 토글 함수
            function toggleEditMode() {
                isEditMode = !isEditMode;
                const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                const distanceItems = document.querySelectorAll(".distanceItem");
                console.log("Toggling Edit Mode:", isEditMode, "Locations:", addedLocations.length);

                if (isEditMode) {
                    editBtn.innerHTML = "";
                    editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done.png")`;
                    editBtn.style.backgroundSize = "cover";
                    editBtn.style.width = "32px";
                    editBtn.style.height = "32px";

                    // mouseover 이벤트: done-orange.png로 변경
                    editBtn.addEventListener("mouseover", function () {
                        editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done-orange.png")`;
                    });

                    // mouseout 이벤트: done.png로 복원
                    editBtn.addEventListener("mouseout", function () {
                        editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/done.png")`;
                    });

                    // 공유 버튼을 삭제 버튼으로 변경
                    shareBtn.classList.remove("shareBtn");
                    shareBtn.classList.add("deleteBtn");
                    shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete.png")`;
                    shareBtn.addEventListener("mouseover", function () {
                        shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete-orange.png")`;
                    });
                    shareBtn.addEventListener("mouseout", function () {
                        shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/delete.png")`;
                    });
                    console.log("Share button changed to delete button");

                    // 거리 div와 장소 추가 버튼 숨기기
                    distanceItems.forEach(item => {
                        item.style.display = "none";
                        console.log("Distance item hidden");
                    });
                    addLocBtn.style.display = "none";
                    console.log("Add location button hidden");

                    addedLocations.forEach((location, index) => {
                        console.log(`Processing location ${index + 1}`);
                        const numberSpan = location.querySelector(".locationNumber");
                        const removeBtn = location.querySelector(".removeBtn");
                        if (numberSpan) {
                            const checkbox = document.createElement("input");
                            checkbox.type = "checkbox";
                            checkbox.className = "location-checkbox";
                            numberSpan.replaceWith(checkbox);
                            console.log("Checkbox added for location", index + 1);
                        } else {
                            console.error("NumberSpan not found in location", index + 1);
                        }

                        if (removeBtn) {
                            removeBtn.style.display = "inline-block";
                            console.log("Remove button displayed for location", index + 1);
                        }

                        location.setAttribute("draggable", "true");
                        location.addEventListener("dragstart", handleDragStart);
                        location.addEventListener("dragover", handleDragOver);
                        location.addEventListener("drop", handleDrop);
                        location.addEventListener("dragend", handleDragEnd);
                    });

                    // 수정 모드 진입 시 지도 초기화
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
                    editBtn.innerHTML = "";
                    editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit.png")`;
                    editBtn.style.backgroundSize = "cover";
                    editBtn.style.width = "32px";
                    editBtn.style.height = "32px";

                    // mouseover 이벤트: done-orange.png로 변경
                    editBtn.addEventListener("mouseover", function () {
                        editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit-orange.png")`;
                    });

                    // mouseout 이벤트: done.png로 복원
                    editBtn.addEventListener("mouseout", function () {
                        editBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/edit.png")`;
                    });

                    // 삭제 버튼을 공유 버튼으로 복원
                    shareBtn.classList.remove("deleteBtn");
                    shareBtn.classList.add("shareBtn");
                    shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-black.png")`;
                    shareBtn.addEventListener("mouseover", function () {
                        shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-orange.png")`;
                    });
                    shareBtn.addEventListener("mouseout", function () {
                        shareBtn.style.backgroundImage = `url("${pageContext.request.contextPath}/resources/images/common/share-button-black.png")`;
                    });
                    console.log("Delete button changed back to share button");

                    // 거리 div와 장소 추가 버튼 다시 표시
                    distanceItems.forEach(item => {
                        item.style.display = "block";
                        console.log("Distance item displayed");
                    });
                    addLocBtn.style.display = "flex";
                    console.log("Add location button displayed");

                    addedLocations.forEach((location, index) => {
                        const checkbox = location.querySelector(".location-checkbox");
                        const removeBtn = location.querySelector(".removeBtn");
                        if (checkbox) {
                            const numberSpan = document.createElement("span");
                            numberSpan.className = "locationNumber";
                            numberSpan.textContent = (index + 1) + " ";
                            checkbox.replaceWith(numberSpan);
                            console.log("Checkbox replaced with number for location", index + 1);
                        }

                        if (removeBtn) {
                            removeBtn.style.display = "none";
                            console.log("Remove button hidden for location", index + 1);
                        }

                        location.removeAttribute("draggable");
                        location.removeEventListener("dragstart", handleDragStart);
                        location.removeEventListener("dragover", handleDragOver);
                        location.removeEventListener("drop", handleDrop);
                        location.removeEventListener("dragend", handleDragEnd);
                    });
                    updateNumbersAndDistances();
                }
            }

            // 드래그 앤 드롭 핸들러
            function handleDragStart(e) {
                draggedItem = e.target;
                e.target.classList.add("dragging");
            }

            function handleDragOver(e) {
                e.preventDefault();
            }

            function handleDrop(e) {
                e.preventDefault();
                const dropTarget = e.target.closest(".addedLocation");
                if (dropTarget && draggedItem !== dropTarget && dropTarget.dataset.day === selectedDay) {
                    const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                    const draggedIndex = locations.findIndex(loc => loc.id === draggedItem.dataset.id);
                    const dropIndex = locations.findIndex(loc => loc.id === dropTarget.dataset.id);
                    const [dragged] = locations.splice(draggedIndex, 1);
                    locations.splice(dropIndex, 0, dragged);
                    renderLocations();
                    addMarkersForLocations();
                }
            }

            function handleDragEnd(e) {
                e.target.classList.remove("dragging");
                draggedItem = null;
            }

            // 삭제 및 완료 이벤트
            document.addEventListener("click", async function (e) {
                if (e.target.classList.contains("removeBtn")) {
                    const addedLocation = e.target.closest(".addedLocation");
                    if (addedLocation) {
                        const id = addedLocation.dataset.id;
                        const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                        const index = locations.findIndex(loc => loc.id === id);
                        if (index !== -1) {
                            locations.splice(index, 1);
                            const marker = markerMap.get(id);
                            if (marker && typeof marker.setMap === "function") {
                                marker.setMap(null);
                                markerMap.delete(id);
                            }
                            renderLocations();
                            await updateMapVisibility();
                            console.log("Individual location removed via removeBtn");
                        }
                    }
                } else if (e.target.classList.contains("deleteBtn")) {
                    const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                    const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                    let deletedCount = 0;
                    addedLocations.forEach(location => {
                        const checkbox = location.querySelector(".location-checkbox");
                        if (checkbox && checkbox.checked) {
                            const id = location.dataset.id;
                            const index = locations.findIndex(loc => loc.id === id);
                            if (index !== -1) {
                                locations.splice(index, 1);
                                const marker = markerMap.get(id);
                                if (marker && typeof marker.setMap === "function") {
                                    marker.setMap(null);
                                    markerMap.delete(id);
                                }
                                deletedCount++;
                            }
                        }
                    });
                    renderLocations();
                    await updateMapVisibility();
                    if (deletedCount > 0) {
                        console.log(`${deletedCount} location(s) deleted via delete button`);
                    }
                    if (locations.length === 0) {
                        toggleEditMode();
                    }
                }
            });

            editBtn.addEventListener("click", toggleEditMode);

            // 수정된 updateNumbersAndDistances 함수
            async function updateNumbersAndDistances() {
                const locations = selectedDay === "day1" ? day1Locations : day2Locations;
                const planLocList = document.querySelector(".planLocList");
                const subDiv = document.querySelector(".subDiv");

                const existingDistanceItems = document.querySelectorAll(".distanceItem");
                existingDistanceItems.forEach(item => item.remove());

                if (!isEditMode) {
                    for (let index = 0; index < locations.length - 1; index++) {
                        const fromLocation = locations[index];
                        const toLocation = locations[index + 1];
                        const distanceDiv = document.createElement("div");
                        distanceDiv.className = "distanceItem";
                        distanceDiv.textContent = "계산 중...";

                        const addedLocations = document.querySelectorAll(`.addedLocation[data-day="`+selectedDay+`"]`);
                        addedLocations[index].insertAdjacentElement("afterend", distanceDiv);

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
                    console.log("Sending locationMap:", locationMap);
                    const response = await fetch(contextPath + '/loc/getDistance.do', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(locationMap)
                    });

                    if (!response.ok) {
                        const errorText = await response.text();
                        console.error("Response not OK:", response.status, errorText);
                        throw new Error(`Failed to fetch distance: ${errorText}`);
                    }

                    const text = await response.text();
                    console.log("Raw response text:", text);
                    const data = text ? JSON.parse(text) : {};
                    console.log("Parsed data:", data);

                    let distance;
                    if (data && typeof data === 'object' && 'distance' in data && !isNaN(data.distance)) {
                        distance = parseFloat(data.distance);
                    } else {
                        console.warn("Invalid distance value or format:", data);
                        distance = "N/A";
                    }

                    console.log("Extracted distance:", distance);
                    return distance === "N/A" ? "N/A" : distance;
                } catch (error) {
                    console.error("Error fetching distance:", error);
                    return "N/A";
                }
            }

            // Day 선택 이벤트
            const dayElements = document.querySelectorAll(".day1, .day2");
            dayElements.forEach(day => {
                day.addEventListener("click", async function () {
                    dayElements.forEach(d => d.classList.remove("active"));
                    this.classList.add("active");
                    selectedDay = this.classList.contains("day1") ? "day1" : "day2";
                    console.log("Selected day:", selectedDay, "day1Locations:", day1Locations, "day2Locations:", day2Locations);

                    // UI와 지도를 순차적으로 갱신
                    renderLocations();
                    await updateMapVisibility();
                });
            });

            // 초기 활성화
            dayElements[0].classList.add("active");

            // 초기 수정 버튼 상태 설정 및 맵 가시성 설정
            toggleEditButton();
            await updateMapVisibility();
            window.addEventListener("message", async function () {
                toggleEditButton();
                await updateMapVisibility();
            });

            // savePlan 함수 정의
            function savePlan() {
                // 유효성 검사
                if (!${requestScope.plan.planId} || (day1Locations.length === 0 )) {
                    alert("저장할 데이터가 없습니다.");
                    return;
                }

                // 폼 필드에 데이터 설정
                document.getElementById("day1Locations").value = JSON.stringify(day1Locations);
                if(!(day1Locations.length === 0)) {
                    document.getElementById("day2Locations").value = JSON.stringify(day2Locations);
                }

                // 폼 제출
                document.getElementById("saveLocationsForm").submit();
            }


            // 버튼에 이벤트 리스너 추가
            document.getElementById("saveBtn").addEventListener("click", savePlan);
        });

        function modifiedPlanName() {
            // updatePlanFirst 페이지 이동
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
<div class="planNameDiv">
    <p class="planName">${requestScope.plan.planTitle}</p>
    <button class="planModified" type="button" onclick="modifiedPlanName()">편집</button>
</div>
<div class="map"></div>
<div class="planDay">
    <div class="planDateTitle">
        <div class="day1"><p class="dayTitle">Day1</p>
            <p class="planDate">
                <fmt:formatDate value="${requestScope.plan.planStartDate}" pattern="M.d" var="startDate"/>
                (${startDate})
            </p></div>
        <c:if test="${requestScope.plan.planStartDate != requestScope.plan.planEndDate}">
            <div class="day2"><p class="dayTitle">Day2</p><p class="planDate">
                <fmt:formatDate value="${requestScope.plan.planEndDate}" pattern="M.d" var="endDate"/>
                (${endDate})</p></div>
        </c:if>
        <button class="checklistBtn"><span class="checkListText">체크리스트</span>
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
<form id="saveLocationsForm" action="${pageContext.request.contextPath}/plan/createPlanSecond.do" method="POST">
    <input type="hidden" name="planId" value="${requestScope.plan.planId}">
    <input type="hidden" name="day1Locations" id="day1Locations">
    <input type="hidden" name="day2Locations" id="day2Locations">
    <div class="saveDiv">
        <button class="saveBtn" type="button" id="saveBtn">저장</button>
    </div>
</form>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>