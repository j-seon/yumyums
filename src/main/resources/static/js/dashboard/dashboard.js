function setBusyBtn(busy) {
    $(".main-busy-btn").removeClass("btn-info")
    $(".main-busy-btn").removeClass("btn-success")
    $(".main-busy-btn").removeClass("btn-warning")
    $(".main-busy-btn").removeClass("btn-danger")

    switch(busy) {
        case 'SPACIOUS':   $(".main-busy-btn").addClass("btn-info"); break;
        case 'NOMAL': $(".main-busy-btn").addClass("btn-success"); break;
        case 'CROWDED':  $(".main-busy-btn").addClass("btn-warning"); break;
        case 'FULL':  $(".main-busy-btn").addClass("btn-danger"); break;
    }
}
function setReviewDifferRate() {
    if(differRate>0){
            differRate=" + " + differRate;
            $("#differ-rate").addClass("text-danger");
            $("#differ-rate").text(differRate);
        }else if(differRate < 0){
            differRate=" - " + differRate
            $("#differ-rate").addClass("text-info");
            $("#differ-rate").text(differRate);
        }

}

$(document).ready(function() {
    //혼잡도에 따라 버튼 색 설정

console.log("storeBusy"+storeId);
    //버튼 색 변경
    setBusyBtn($('#storeBusy').val());
    //평점 증감에 따라 색 변경
    setReviewDifferRate();

    $('.busy-buttons').on('click', function(e) {

        setBusy = $(this).data('busy');

        $.ajax({
            url: "/dashboard" , // URL 경로에 categoryText를 포함
            method: "put",
            data:{storeId : storeId,busy:setBusy},
            success: function(data, status, xhr) {
               console.log("success")
                   setBusyBtn(setBusy);
            },
            error: function(xhr, status, error) {
                console.error("Error occurred:", error);
            }
        });
    })
})