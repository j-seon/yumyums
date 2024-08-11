$(document).ready(function() {
    $("input[name='paymentMethod']").on("change", function() {
        var selectedPaymentMethod = $("input[name='paymentMethod']:checked").val();
        $("#hiddenPaymentMethod").val(selectedPaymentMethod);
    });

    $("#orderForm").on("submit", function(event) {
        event.preventDefault();
        alert("주문 완료되었습니다");

        if (confirm("주문을 확인하시겠습니까?")) {
            this.submit();
        } else {
            window.location.href = "/menu";
        }
    });
});