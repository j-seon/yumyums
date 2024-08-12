$(document).ready(function() {
    // 초기 로딩 시 선택된 결제 방법을 hidden 필드에 설정
    var initialSelection = $("input[name='paymentMethod']:checked").val();
    $("#hiddenPaymentMethod").val(initialSelection);

    console.log("Initial Payment Method: " + $("#hiddenPaymentMethod").val()); // 초기 선택된 결제 방법 로그

    // 결제 방법 변경 시 hidden 필드 업데이트 및 active 클래스 관리
    $(".btn-group .btn").on("click", function() {
        // 모든 버튼에서 active 클래스 제거
        $(".btn-group .btn").removeClass("active");

        // 클릭한 버튼에 active 클래스 추가
        $(this).addClass("active");

        // 클릭된 라디오 버튼을 checked 상태로 만들고 다른 버튼은 자동으로 해제
        var selectedPaymentMethod = $(this).find("input").val();
        $("#hiddenPaymentMethod").val(selectedPaymentMethod);
        console.log("Selected Payment Method: " + selectedPaymentMethod); // 선택된 결제 방법 로그
    });

    // 폼 제출 시, hidden 필드의 값만 전송되도록 보장
    $("#orderForm").on("submit", function() {
        var selectedPaymentMethod = $("#hiddenPaymentMethod").val();
        $("input[name='paymentMethod']").prop('checked', false); // 모든 라디오 버튼 체크 해제
        $("input[name='paymentMethod'][value='" + selectedPaymentMethod + "']").prop('checked', true); // hidden 필드 값에 맞는 라디오 버튼 선택
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