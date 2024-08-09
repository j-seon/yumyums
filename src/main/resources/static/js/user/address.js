    // 기본 위치 (강남역의 위도와 경도)
    const DEFAULT_LAT = 37.49799;
    const DEFAULT_LNG = 127.027912;

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LNG), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    // 지도를 미리 생성
    var map = new kakao.maps.Map(mapContainer, mapOption);
    // 주소-좌표 변환 객체를 생성
    var geocoder = new kakao.maps.services.Geocoder();
    // 마커를 미리 생성
    var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LNG),
        map: map
    });

    // 클라이언트의 현재 위치를 가져오는 함수
    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition, showError);
        } else {
            alert("이 브라우저에서는 Geolocation을 지원하지 않습니다.");
            showPosition({ coords: { latitude: DEFAULT_LAT, longitude: DEFAULT_LNG } });
        }
    }

    // 위치를 가져온 후 지도를 표시하는 함수
    function showPosition(position) {
        var lat = position.coords.latitude; // 위도
        var lng = position.coords.longitude; // 경도

        var coords = new kakao.maps.LatLng(lat, lng);
        map.setCenter(coords); // 지도 중심을 현재 위치로 변경
        marker.setPosition(coords); // 마커를 현재 위치로 이동

        // 위도와 경도를 입력 필드에 설정
        document.getElementById("convX").value = lng;
        document.getElementById("convY").value = lat;

        // 주소 변환
        geocoder.coord2Address(lng, lat, function(results, status) {
            if (status === kakao.maps.services.Status.OK) {
                var address = results[0].address.address_name; // 주소
                document.getElementById("address").value = address; // 주소 값을 입력 필드에 설정
            }
        });
    }

    // 오류 처리 함수
    function showError(error) {
        switch(error.code) {
            case error.PERMISSION_DENIED:
                alert("사용자가 위치정보 제공을 거부했습니다.");
                break;
            case error.POSITION_UNAVAILABLE:
                alert("위치정보를 사용할 수 없습니다.");
                break;
            case error.TIMEOUT:
                alert("위치정보 요청이 시간 초과되었습니다.");
                break;
            case error.UNKNOWN_ERROR:
                alert("알 수 없는 오류가 발생했습니다.");
                break;
        }

        // 기본 위치로 설정
        var coords = new kakao.maps.LatLng(DEFAULT_LAT, DEFAULT_LNG);
        map.setCenter(coords);
        marker.setPosition(coords);
        document.getElementById("convX").value = DEFAULT_LNG;
        document.getElementById("convY").value = DEFAULT_LAT;
        document.getElementById("address").value = "서울 강남구 강남대로 지하 396"; // 기본 주소 설정
    }

    // 페이지 로드 시 위치를 가져옴
    window.onload = function() {
        getLocation();
    };

    function searchAddressSetCoords() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("address").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === kakao.maps.services.Status.OK) {

                        var result = results[0]; // 첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    var coords = new kakao.maps.LatLng(result.y, result.x);
                    document.getElementById("convX").value = result.x;
                    document.getElementById("convY").value = result.y;
                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords);
                    }
                });
            }
        }).open();
    }