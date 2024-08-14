// SweetAlert 스크립트를 추가하는 함수
function loadSweetAlert() {
    const script = document.createElement('script');
    script.src = "https://cdn.jsdelivr.net/npm/sweetalert2@9";
    document.head.appendChild(script);
}
// 함수 호출
loadSweetAlert();



// 경고메세지
function alertWarningMessage (message) {
    Swal.fire({
        title: message,
        icon: 'warning',
        confirmButtonColor: '#FFA62F',
        confirmButtonText: 'OK'
    })
}

// 경고메세지 + 다른버튼
function alertWarningMessageOkAndCustomButton (message, buttonMessage) {
    Swal.fire({
        title: message,
        icon: 'warning',
        confirmButtonColor: '#FFA62F',
        confirmButtonText: 'OK',
        cancelButtonColor: '#C0C0C0', // 쉼표 추가
        cancelButtonText: buttonMessage // 쉼표 추가
    })
}


// 에러메세지
function alertErrorMessage (message) {
    Swal.fire({
        title: message,
        icon: 'error',
        confirmButtonColor: '#FFA62F',
        confirmButtonText: 'OK'
    })
}

// 성공메세지
function alertSuccessMessage (message) {
    Swal.fire({
        title: message,
        icon: 'success',
        confirmButtonColor: '#FFA62F',
        confirmButtonText: 'OK'
    })
}

// 성공메세지 후 새로고침
function alertSuccessRefresh (message) {
    Swal.fire({
        title: message,
        icon: 'success',
        confirmButtonColor: '#FFA62F',
        confirmButtonText: 'OK',
        closeOnClickOutside : false
    }).then(function(){
        location.reload();
    })
}