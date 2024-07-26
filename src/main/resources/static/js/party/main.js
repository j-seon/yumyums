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
						loadPageContent('join_party');
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
			},
			error: function() {
				alert('페이지를 로드하는 데 실패했습니다.');
			}
		});
	}




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