	//입력한 링크를 받아와 리다이렉트 시키기
	function joinParty() {
		// 입력 필드에서 값 가져오기
		var link = document.getElementById("join-link").value;

		// 입력된 링크가 비어있지 않은지 확인
		if (link) {
			// 해당 링크로 리다이렉트
			window.location.href = link;
		} else {
			alert("초대링크를 입력해주세요.");
		}
	}