$(document).ready(function() {
    // 초기 로딩 시 선택된 결제 방법을 hidden 필드에 설정
    var initialSelection = $("input[name='paymentMethod']:checked").val();

    if (initialSelection) {
        $("#hiddenPaymentMethod").val(initialSelection);
    } else {
        // 기본적으로 첫 번째 결제 방법을 선택함
        var firstPaymentMethod = $("input[name='paymentMethod']").first();
        firstPaymentMethod.prop('checked', true);
        $("#hiddenPaymentMethod").val(firstPaymentMethod.val());
    }

    console.log("Initial Payment Method: " + $("#hiddenPaymentMethod").val()); // 초기 선택된 결제 방법 로그

    // 결제 방법 변경 시 hidden 필드 업데이트
    $("input[name='paymentMethod']").on("change", function() {
        var selectedPaymentMethod = $(this).val(); // 변경된 라디오 버튼의 값을 가져옴
        $("#hiddenPaymentMethod").val(selectedPaymentMethod);
        console.log("Selected Payment Method: " + selectedPaymentMethod); // 선택된 결제 방법 로그
    });

    // 폼 제출 시 결제 방식이 설정되지 않았다면 경고
    $("#orderForm").on("submit", function(event) {
        var selectedPaymentMethod = $("#hiddenPaymentMethod").val();

        if (!selectedPaymentMethod) {
            alert("결제 방식을 선택해주세요.");
            event.preventDefault();
            return false;
        }

        alert("주문 완료되었습니다");

        if (confirm("주문을 확인하시겠습니까?")) {
            this.submit();
        } else {
            window.location.href = "/menu";
        }
    });
});