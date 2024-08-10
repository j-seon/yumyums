function addToCart(menuId) {
    const row = $("div[data-menu-id='" + menuId + "']");
    console.log('row:',row);
    const quantity = row.find('input[name="quantity"]').val();
    console.log('quantity:',quantity);

    $.ajax({
        url: "/cart/add",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            menuDTO: { id: menuId },
            menuCount: quantity
        }),
        success: function (response) {
            console.log('response:',response);
            alert(response); // 성공 메시지
            updateCartCount(); // 장바구니 카운트 업데이트
        },
        error: function (xhr) {
            console.error("Error Status: " + xhr.status); // 콘솔에 상태 코드 출력
            console.error("Error Response: " + xhr.responseText); // 콘솔에 응답 내용 출력

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

function updateCartCount() {
    $.get("/cart/count", function (count) {
        $("#cart-count").text("장바구니에 " + count + "개의 메뉴가 담겨 있습니다");
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