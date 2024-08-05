   const duplicate = (e, field) => {
        const input = e.target;
        const keyword = e.target.value.trim();

        if(keyword.length === 0){
            input.classList.remove('is-invalid')
            input.classList.remove('is-valid')
            return
        }

        $.ajax({
            url: "/api/v1/duplicate",
            type: 'GET',
            data: {
                keyword : keyword,
                field : field
            },
            success: (data) => {
                if (data) {
                    input.classList.remove('is-valid');
                    input.classList.add('is-invalid');
                } else {
                    input.classList.remove('is-invalid');
                    input.classList.add('is-valid');
                }
            },
            error: (xhr, status, error) => {
                input.classList.remove('is-valid', 'is-invalid');
            }

        });
    }