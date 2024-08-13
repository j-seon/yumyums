function addToCart(menuId) {
    const row = $("div[data-menu-id='" + menuId + "']");
    const quantity = row.find('input[name="quantity"]').val();

	// 수량이 1보다 작을 경우 에러 메시지
	if (isNaN(quantity) || quantity <= 0) {
		alert("수량은 1 이상이어야 합니다.");
		return;
	}

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

	var myCartModal = document.getElementById('myCartModal');

	// 모달이 열릴 때 Ajax 요청을 보냄
	myCartModal.addEventListener('show.bs.modal', function () {
		// Ajax 요청
		$.ajax({
			url: '/api/v1/partyCartItems',
			method: 'GET',
            data: { encryptedPartyId: partyId },
			dataType: 'json',
			success: function (data) {
				// 기존 내용 초기화
				var tableBody = myCartModal.querySelector('tbody');
				tableBody.innerHTML = '';

				// 받아온 데이터를 이용해 테이블을 채움
				data.forEach(function (cartItem) {
					var row = `
						<tr>
							<td class="align-middle">
								<p class="mb-0">${cartItem.menuDTO.name}</p>
							</td>
							<td class="align-middle">
								<p class="mb-0" data-price="${cartItem.menuDTO.price}">${cartItem.menuDTO.price} 원</p>
							</td>
							<td class="align-middle">
								<input type="number" name="menuCount" min="1" value="${cartItem.menuCount}" class="form-control text-center border-0" data-price="${cartItem.menuDTO.price}" data-menu-id="${cartItem.menuDTO.id}" oninput="updateTotalPrice(this)">
							</td>
							<td class="align-middle">
								<p class="mb-0 total-price">${cartItem.menuDTO.price * cartItem.menuCount} 원</p>
							</td>
							<td class="align-middle">
								<form action="/cart/remove" method="post" class="mb-0" onsubmit="return confirmDeletion(event)">
									<input type="hidden" name="menuId" value="${cartItem.menuDTO.id}" />
									<button class="btn btn-md rounded-circle bg-light border" type="submit">
										<i class="fa fa-times text-danger"></i>
									</button>
								</form>
							</td>
						</tr>
					`;
					tableBody.insertAdjacentHTML('beforeend', row); // 새로운 행을 추가
				});

				// 총 금액 업데이트
				updateTotalOrderAmount();
			},
			error: function (xhr, status, error) {
				console.error('Failed to fetch data:', status, error);
			}
		});
	});
});

// 총 금액 업데이트 함수
function updateTotalOrderAmount() {
	var totalAmount = 0;
	$('#myCartModal').find('tbody tr').each(function () {
		var price = parseInt($(this).find('[data-price]').attr('data-price'));
		var quantity = parseInt($(this).find('input[name="menuCount"]').val());
		totalAmount += price * quantity;
	});

	$('#totalOrderAmount').text(totalAmount + ' 원');
	$('#totalAmount').text(totalAmount + ' 원');
}

// 삭제 확인 함수
function confirmDeletion(event) {
	return confirm('정말 삭제하시겠습니까?');
}

// 개별 항목의 총 가격 업데이트 함수
function updateTotalPrice(element) {
	var price = $(element).attr('data-price');
	var quantity = $(element).val();
	var totalPrice = price * quantity;
	$(element).closest('tr').find('.total-price').text(totalPrice + ' 원');

	updateTotalOrderAmount();
}

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


