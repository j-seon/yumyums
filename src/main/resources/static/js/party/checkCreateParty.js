
	document.addEventListener('DOMContentLoaded', function() {
		checkValidCreatePartyInfo();
	});


	// createParty의 submit 버튼을 누를때 실행하는 유효성 검사
	function checkValidCreatePartyInfo () {
		$('#submitBtn').click(function(event) {

			// 가게를 선택하지않았다면
			if ($('#store_name').val().trim() === '') {
				stopSubmitWithAlertErrorMessage('가게를 선택해주세요.', event);
				return;
			}

			// 옵션을 선택하지 않았다면
			$('.party-option-item:visible').each(function() {
				const radios = $(this).find('input[type="radio"]');
				const selects = $(this).find('select');

				// 라디오 옵션 선택
				if (radios.length > 0) {
					const isRadioChecked = radios.is(':checked');
					if (!isRadioChecked) {
						stopSubmitWithAlertErrorMessage('모든 옵션을 선택해 주세요.', event);
						return;
					}
				}

				// 셀렉트 옵션 선택
				if (selects.length > 0) {
					selects.each(function() {
						if ($(this).val() === '') {
							stopSubmitWithAlertErrorMessage('모든 옵션을 선택해 주세요.', event);
							return;
						}
					});
				}
			});
		});
	}

	// 에러알럿 출력 출력하고 submit 중지
	function stopSubmitWithAlertErrorMessage (message, event) {
		Swal.fire({
		  title: message,
		  icon: 'warning',
		  confirmButtonColor: '#FFA62F',
		  confirmButtonText: 'OK'
		})
		event.preventDefault();
	}