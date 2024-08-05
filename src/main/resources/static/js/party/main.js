	//== 상점페이지 ==//
	// 파티 index 페이지의 partyItem에 클릭 이벤트 삽입
	document.addEventListener('DOMContentLoaded', function() {
		const partyItems = document.querySelectorAll('.party-item');
		partyItems.forEach(item => {
			item.addEventListener('click', function() {
				switch (this.textContent.trim()) {
					case '파티생성':
						loadPageContent('create_party');
						break;
					case '파티입장':
						loadPageContent('before_join_party');
						break;
				}
			});
		});
	});

	// 필요한 페이지를 가져오는 메소드
	function loadPageContent(targetPage) { // 수정된 부분

		$.ajax({
			url: '/party',
			data: { targetPage: targetPage },
			method: 'GET',
			success: function(data) {
				$('.container').html(data);

				// 이동할 페이지가 파티생성 페이지라면
				if (targetPage === 'create_party') {
					initializeFormToggles();
					checkValidCreatePartyInfo();
				}
			},
			error: function() {
				alert('페이지를 로드하는 데 실패했습니다.');
			}
		});
	}

	// create_party로 넘어갈때 시작하는 것
   function initializeFormToggles() {
		const inviteOption = document.getElementById('inviteOption');
		const randomOption = document.getElementById('randomOption');
		const payTypeRadios = document.querySelectorAll('input[name="payType"]');
		const randomTypeElements = document.querySelectorAll('.random-type');

		// 초대하기 버튼을 누른다면
		inviteOption.addEventListener('click', () => {
			// invite 클래스 항목의 d-none 비활성화
			document.querySelectorAll('.invite').forEach(element => {
				element.classList.remove('d-none');
			});

			// random 클래스 항목의 d-none 활성화
			document.querySelectorAll('.random').forEach(element => {
				element.classList.add('d-none');
			});

			// 모든 라디오 버튼 선택 초기화
			document.querySelectorAll('input[type="radio"]').forEach(radio => {
				radio.checked = false;
			});

			// 랜덤 결제 선택방법 d-none 활성화
			randomTypeElements.forEach(element => {
				element.classList.add('d-none');
			});

			inviteOption.checked = true;
		});

		// 랜덤추가 버튼을 누른다면
		randomOption.addEventListener('click', () => {
			// random 클래스 항목의 d-none 비활성화
			document.querySelectorAll('.random').forEach(element => {
				element.classList.remove('d-none');
			});

			// invite 클래스 항목의 d-none 활성화
			document.querySelectorAll('.invite').forEach(element => {
				element.classList.add('d-none');
			});

			// 모든 선택 초기화
			document.querySelectorAll('input[type="radio"]').forEach(radio => {
				radio.checked = false;
			});

			// 랜덤 결제 선택방법 d-none 활성화
			randomTypeElements.forEach(element => {
				element.classList.add('d-none');
			});

			randomOption.checked = true;
		});

		// 결제타입을 선택한다면
		payTypeRadios.forEach(radio => {
			radio.addEventListener('change', (event) => {
				// 결제타입의 내용이 "랜덤 일괄결제"라면
				if (event.target.id === 'RANDOM_ONCE') {
					randomTypeElements.forEach(element => {
						element.classList.remove('d-none'); //결제방식 선택폼 출력
					});
				// 그외
				} else {
					randomTypeElements.forEach(element => {
						element.classList.add('d-none'); // 결제방식 선택폼 삭제
					});
				}

				// random-type 클래스의 모든 선택 초기화
				randomTypeElements.forEach(element => {
					element.querySelectorAll('input[type="radio"]').forEach(input => {
						input.checked = false;
					});
				});
			});
		});
	}

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

	// 상점 검색폼 출력
	const openFindStoreForm = () => {
		const modal = new bootstrap.Modal(document.getElementById('staticBackdrop'));
		modal.show();
	}

	// 상점 검색
	function findStores(event) {
		// 상점 검색 로직을 여기에 추가
		var searchValue = event.target.value;
		console.log(event);

		$.ajax({
			url: '/api/v1/stores',
			data: { searchValue : searchValue },
			method: 'GET',
			success: (stores) => {
				$('.store-list').empty();

				stores.forEach((storeDTO) => {
					$('.store-list').append(`
					<li class="list-group-item list-group-item-action store"
						data-name="${storeDTO.name}"
						data-address="${storeDTO.address}"
						data-category="${storeDTO.categoryKorName}"
						onclick="setStoreInfo(event)"
						style="cursor:pointer;">${storeDTO.name}
						</li>
					`);
				});
			},
			error: function() {
				console.log('상점 목록을 불러오는데 실패했습니다.');
			}
		});
	}



	// 상점 선택
	const setStoreInfo = (event) => {
		event.preventDefault();

		//검색창 없애고, 검색된 내용 출력
		document.querySelector('.find-store-area').classList.add('d-none');
		document.querySelector('.store-info').classList.remove('d-none');
		document.querySelector('.modal-footer').classList.remove('d-none');

		const dataItems = ['name', 'address', 'category'];
		const values = {};

		dataItems.forEach(item => {
			values[item] = event.target.dataset[item]; //target.data-address = ${넣어진 값}
			document.getElementById(item).value = values[item]; // Id값이 address인 값.value = ${data-address의 내용}
		});
	}


	// 상점 재검색하기
	const resetFindStores = () => {
		// 키워드로 검색된 가게목록 삭제
		$('.store-list').empty();

		//검색된 내용 지우고, 검색창 재출력
		document.querySelector('.find-store-area').classList.remove('d-none');
		document.querySelector('.store-info').classList.add('d-none');
		document.querySelector('.modal-footer').classList.add('d-none');

		//선택으로 입력된 필드값들 초기화
		const fieldNameToClear = ['name', 'address', 'category'];

		//for문을 돌면서 전부 삭제
		fieldNameToClear.forEach(fieldName => {
			document.getElementById(fieldName).value = null;
		});
		document.getElementById("store_name").value = null;
	}


	//가게 선택하기
	const selectStore = () => {
		var selectStoreName = document.getElementById("name").value;
		document.getElementById('store_name').value = selectStoreName;
	}