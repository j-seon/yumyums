$(document).ready(function() {
    //평점 증감에 따라 색 변경
    if(differRate>0){
        differRate=" + " + differRate;
        $("#differ-rate").addClass("text-danger");
    }else if(differRate < 0){
        differRate=" - " + differRate
        $("#differ-rate").addClass("text-info");
    }
    $("#differ-rate").text(differRate);
})