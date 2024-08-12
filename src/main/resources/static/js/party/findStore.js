
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
		document.querySelector('#select-footer').classList.remove('d-none');

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
		document.querySelector('#select-footer').classList.add('d-none');

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