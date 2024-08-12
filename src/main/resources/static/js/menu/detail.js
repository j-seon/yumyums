function addToCart(menuId) {
    const row = $("div[data-menu-id='" + menuId + "']");
    const quantity = row.find('input[name="quantity"]').val();

	$.ajax({
		url: "/cart/add",
		method: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			menuDTO: { id: menuId },
			menuCount: quantity,
			partyDTO: { id: partyId },
			joinPage: joinPage
		}),
		 success: function (response) {
			alertSuccessMessage(response); // 성공 메시지
			updateCartCount(); // 장바구니 카운트 업데이트
		},
		error: function (xhr) {
			console.error("Error Status: " + xhr.status); // 콘솔에 상태 코드 출력
			console.error("Error Response: " + xhr.responseText); // 콘솔에 응답 내용 출력

			if (xhr.status === 400) {
				alertErrorMessage("같은 가게의 메뉴만 담을 수 있습니다");
			} else if (xhr.status === 401 && joinPage === "party") {
				alertErrorMessage("로그인 해주세요");
				window.location.href = "/login?redirect=/party/" + partyId + "&msg=member";
			} else if (xhr.status === 401) {
				alertErrorMessage("로그인 해주세요");
				window.location.href = "/login?redirect=/menu&msg=member";
			} else {
				alertErrorMessage("오류가 발생했습니다 다시 시도해 주세요");
			}
		}
	});
}


$(document).ready(function () {
    updateCartCount();

    // 이벤트 위임을 사용하여 장바구니 버튼 클릭 시 addToCart 함수 호출
    $(document).on("click", "a[th\\:onclick^='addToCart(']", function (e) {
        e.preventDefault(); // 기본 동작 막기
        const menuId = $(this).attr('th:onclick').match(/\d+/)[0]; // 메뉴 ID 추출
        addToCart(menuId);
    });
});


$(".vegetable-carousel1").owlCarousel({
    autoplay: true,
    smartSpeed: 1500,
    center: false,
    dots: true,
    loop: false,
    margin: 25,
    nav : true,
    navText : [
        '<i class="bi bi-arrow-left"></i>',
        '<i class="bi bi-arrow-right"></i>'
    ],
    responsiveClass: true,
    responsive: {
        0:{
            items:1
        },
        576:{
            items:1
        },
        768:{
            items:2
        },
        992:{
            items:3
        },
        1200:{
            items:4
        }
    }
});


function submitPartyMenu() {
    const url = `/partyCart/${encodeURIComponent(partyId)}?joinPage=${encodeURIComponent(joinPage)}`;
    // TODO 웹소켓을 통해 회원ID "준비완료" 값 전달하기
    window.location.href = url;
}
