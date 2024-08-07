function previewImage(event) {
        const files = event.target.files;
        const imagePreviewBox = $('#imagePreviewBox');
        imagePreviewBox.empty(); // 기존 이미지를 초기화

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();

            reader.onload = function(e) {
                imagePreviewBox.append(`<img src="${e.target.result}" class="col-4 p-0 shadow">`); // 이미지 추가
            }

            if (file) {
                reader.readAsDataURL(file); // 파일을 읽어서 Data URL로 변환
            }
        }
    }