
    const sendEmail = () => {
      const emailInput = document.getElementById('email');
      const email = emailInput.value.trim();
      console.log(email);

      $.ajax({
        type: 'POST',
        url: '/email/send',
        data: { email: email },
        success: function(response) {
          alert('이메일이 전송되었습니다.');
          const emailChk = document.getElementById('emailChk');
          emailInput.readOnly = true;
          emailChk.disabled = false;
        },
        error: function(xhr, status, error) {
          alert('이메일 전송 실패');
        }
      });
    };

    const checkEmail = (e, emailChkInput) => {
        const emailCode = emailChkInput.value.trim();
        const emailInput = document.getElementById('email');
        const email = emailInput.value.trim();
        console.log("isNan", isNaN(e.key))

        // 숫자가 아닌 경우 입력 막기
        if (isNaN(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
            emailChkInput.value = emailCode.slice(0, emailCode.length - 1);
            return;
        }

        // 6자리 이상이면 6자리까지만 남김
        if (emailCode.length > 6) {
            emailChkInput.value = emailCode.slice(0, 6);
        }

        // 6자리 숫자 검사 통과 시 AJAX 요청 실행
        $.ajax({
            type: 'POST',
            url: '/email/check',
            data: {
              email: email,
              emailCode: emailCode
            },
            success: function(response) {
              // 포커스 해제 후 disabled 설정
              emailChkInput.blur();
              emailChkInput.disabled = true;
              const joinButton = document.getElementById('joinButton');
              const sendEmailBtn = document.getElementById('sendEmailBtn');
              joinButton.disabled = false;
              sendEmailBtn.disabled = true;
            },
            error: function(xhr, status, error) {
              // 에러 처리
            }
          });
    }