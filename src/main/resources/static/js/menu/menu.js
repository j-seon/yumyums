//장바구니에 상품 추가
function addToCart(menuId) {
    const row = $("div[data-menu-id='" + menuId + "']");
    console.log('row:', row);
    const quantity = row.find('input[name="quantity"]').val();
    console.log('quantity:', quantity);

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
            menuCount: quantity
        }),
        success: function (response) {
            console.log('response:', response);
            alert(response); // 성공 메시지
            updateCartCount(); // 장바구니 카운트 업데이트
        },
        error: function (xhr) {
            console.error("Error Status: " + xhr.status);
            console.error("Error Response: " + xhr.responseText);

            if (xhr.status === 400) {
                alert("같은 가게의 메뉴만 담을 수 있습니다");
            } else if (xhr.status === 401) {
                alert("로그인 해주세요");
                window.location.href = "/login";
            } else {
                alert("오류가 발생했습니다 다시 시도해 주세요");
            }
        }
    });
}


// 필터 폼이 제출될 때 결과를 화면에 출력하는 코드
$('#filterForm').on('submit', function (e) {
    e.preventDefault(); // 폼의 기본 제출 동작 방지

    const filterData = $(this).serialize();

    $.ajax({
        url: "/menu",
        method: "GET",
        data: filterData,
        success: function (response) {
            // #menuResults 부분만 추출하여 갱신
            const newContent = $(response).find('#menuResults').html();
            $('#menuResults').html(newContent);
        },
        error: function (xhr) {
            console.error("Error Status: " + xhr.status);
            console.error("Error Response: " + xhr.responseText);
            alert("결과를 조회하는 중 오류가 발생했습니다.");
        }
    });
});

// 필터 선택 글씨 색깔 변경 유지
$('input[type="checkbox"], input[type="radio"]').on('change', function () {
    const name = $(this).attr('name');
    $(`input[name="${name}"]`).each(function () {
        const label = $(this).closest('div').find('label');
        if (this.checked) {
            label.css('color', 'var(--bs-secondary)');
        } else {
            label.css('color', 'var(--bs-primary)');
        }
    });
});

// 체크된 항목에 대해 색상 변경 처리
$('input[type="checkbox"]:checked, input[type="radio"]:checked').each(function () {
    $(this).closest('div').find('label').css('color', 'var(--bs-secondary)');
});
