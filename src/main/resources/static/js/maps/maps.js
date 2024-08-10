// 지도를 생성합니다.
const mapContainer = document.getElementById('map'); // 지도를 표시할 div
const mapOption = {
    center: new kakao.maps.LatLng(37.49799, 126.978), // 초기 위치
    level: 3 // 지도의 확대 레벨
};
const map = new kakao.maps.Map(mapContainer, mapOption);

// 주소-좌표 변환 객체를 생성
var geocoder = new kakao.maps.services.Geocoder();

// 기본 위치 설정 (서울)
const DEFAULT_LAT = 37.49799; // 기본 위도
const DEFAULT_LON = 127.027912; // 기본 경도
const DEFAULT_RADIUS = 300;

let markers = []; // 마커를 저장할 배열
let currentCircle; // 현재 원을 저장할 변수
let currentOverlay; // 현재 커스텀 오버레이를 저장할 변수

// 현재 위치를 기반으로 마커를 추가하는 함수
function loadStores(lat, lon, radius) {
    $.ajax({
        url: `/api/v1/maps`, // 서버의 API URL
        type: 'GET',
        data: {
            lat: lat,
            lon: lon,
            radius: radius // 미터 단위로 반경 전달
        },
        success: function(data) {
            // 기존 마커와 원 제거
            removeMarkers();
            removeCircle();

            data.forEach(store => {
                const markerPosition = new kakao.maps.LatLng(store.convY, store.convX);

                //마커 생성
                const marker = new kakao.maps.Marker({
                    position: markerPosition,
                    title: store.name,
                });
                marker.setMap(map); // 마커를 지도에 표시합니다.
                markers.push(marker); // 마커 배열에 추가

                // 마커 클릭 시 오버레이 표시
                kakao.maps.event.addListener(marker, 'click', function() {
                    if (currentOverlay) {
                        currentOverlay.setMap(null); // 이전 오버레이 제거
                    }

                    // 커스텀 오버레이에 표시할 컨텐츠
                    const content = `
                        <div class="wrap">
                            <div class="info">
                                <div class="title">
                                    ${store.name}
                                    <div class="close" onclick="closeOverlay()" title="닫기"></div>
                                </div>
                                <div class="body">
                                    <div class="img">
                                        <img src="/img/${store.imagesDTO.imgUrl}" width="73" height="70">
                                    </div>
                                    <div class="desc">
                                        <div class="ellipsis">${store.address}</div>
                                        <div class="jibun ellipsis">영업시간 ${store.openTime}:00 ~ ${store.closeTime}:00</div>
                                        <div><a href="/menu/${store.storeId}" class="link">상세보기</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;

                    currentOverlay = new kakao.maps.CustomOverlay({
                        content: content,
                        map: map,
                        position: markerPosition,
                    });
                    currentOverlay.setMap(map); // 오버레이를 지도에 표시
                });
            });

            // 원을 추가
            drawCircle(lat, lon, radius);
        },
        error: function(xhr, status, error) {
            console.error("AJAX 요청 실패:", error);
        }
    });
}


// 기존 마커를 제거하는 함수
function removeMarkers() {
    markers.forEach(marker => {
        marker.setMap(null); // 마커를 지도에서 제거
    });
    markers = []; // 마커 배열 초기화
}

// 원을 제거하는 함수
function removeCircle() {
    if (currentCircle) {
        currentCircle.setMap(null); // 원을 지도에서 제거
        currentCircle = null; // 현재 원 초기화
    }
}

// 원을 추가하는 함수
function drawCircle(lat, lon, radius) {
    currentCircle = new kakao.maps.Circle({
        center: new kakao.maps.LatLng(lat, lon), // 원의 중심
        radius: radius, // 반경 (미터 단위)
        strokeWeight: 2, // 선 두께
        strokeColor: '#81c408', // 선 색상
        strokeOpacity: 0.8, // 선 투명도
        fillColor: '#81c408', // 채우기 색상
        fillOpacity: 0.1 // 채우기 투명도
    });
    currentCircle.setMap(map); // 지도의 원을 표시합니다.
}

// 사용자의 현재 위치를 가져옵니다.
function getCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            const userLat = position.coords.latitude; // 위도
            const userLon = position.coords.longitude; // 경도

            // 지도 중심을 현재 위치로 업데이트
            map.setCenter(new kakao.maps.LatLng(userLat, userLon));

            // 매장 로드
            loadStores(userLat, userLon, document.getElementById('radiusRange').value);
        }, function(error) {
            console.error("위치 정보를 가져오는 데 실패했습니다:", error);
            // 기본값을 사용하여 매장 로드
            map.setCenter(new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON));
            loadStores(DEFAULT_LAT, DEFAULT_LON, DEFAULT_RADIUS);
        });
    } else {
        console.error("이 브라우저는 Geolocation을 지원하지 않습니다.");
        // 기본값을 사용하여 매장 로드
        map.setCenter(new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LON));
        loadStores(DEFAULT_LAT, DEFAULT_LON, DEFAULT_RADIUS);
    }
}

// 지도 이동 시 현재 위치를 업데이트하는 함수
function updateLocationOnMove() {
    const center = map.getCenter(); // 현재 지도 중심 좌표
    const lat = center.getLat();
    const lon = center.getLng();

    loadStores(lat, lon, document.getElementById('radiusRange').value); // 새로운 중심 좌표로 매장 로드
}

// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
function closeOverlay() {
    if (currentOverlay) {
        currentOverlay.setMap(null); // 오버레이를 제거합니다.
        currentOverlay = null; // 현재 오버레이 초기화
    }
}

// 주소 검색 시 현재 위치를 업데이트하는 함수
function searchAddressSetCenter() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수

            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {

                    var result = results[0]; // 첫번째 결과의 값을 활용

                // 해당 주소에 대한 좌표를 받아서
                var coords = new kakao.maps.LatLng(result.y, result.x);

                // 지도 중심을 변경한다.
                map.setCenter(coords);

                loadStores(result.y, result.x, document.getElementById('radiusRange').value); // 새로운 중심 좌표로 매장 로드
                }
            });
        }
    }).open();
}

// 지도 이벤트 리스너 추가
kakao.maps.event.addListener(map, 'center_changed', updateLocationOnMove);

// DOMContentLoaded 이벤트 리스너 추가
document.addEventListener("DOMContentLoaded", function() {
    const radiusRange = document.getElementById('radiusRange');
    const radiusValue = document.getElementById('radiusValue');

    // 초기값을 표시
    radiusValue.textContent = radiusRange.value;

    // 슬라이더 값이 변경될 때마다 호출되는 이벤트 리스너
    radiusRange.addEventListener('input', function() {
        radiusValue.textContent = this.value; // 현재 값을 표시
        const center = map.getCenter(); // 현재 지도 중심 좌표
        const lat = center.getLat();
        const lon = center.getLng();
        loadStores(lat, lon, this.value); // 새로운 반경으로 매장 로드
    });
});

// 페이지 로드 시 현재 위치 가져오기
getCurrentLocation();