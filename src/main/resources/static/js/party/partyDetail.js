
	// 파티초대 링크 복사
	function copyUrlToClipboard() {
		// joinPartyUrl 요소에서 텍스트 값 가져오기
		const joinPartyUrl = document.getElementById('join-party-url').innerText;

		// 임시 텍스트 영역을 생성하여 텍스트를 복사
		const tempInput = document.createElement('input'); //input 요소 생성
		tempInput.value = joinPartyUrl; 		// 복사할 텍스트(joinPartyUrl)를 할당
		document.body.appendChild(tempInput); 	//input 요소를 문서의 body에 추가
		tempInput.select();						//input 요소의 내용을 선택
		document.execCommand('copy'); 			//현재 선택된 텍스트를 클립보드에 복사
		document.body.removeChild(tempInput); 	//생성한 input 요소 제거


		Swal.fire({
		  title: '복사가 완료되었습니다!',
		  icon: 'success',
		  confirmButtonColor: '#FFA62F',
		  confirmButtonText: 'OK'
		})
	}





	$(document).ready(function() {
		// party-leader-id 값을 가져오기
		var partyLeaderId = document.querySelector('input[name="party-leader-id"]').value;
		var partyJoinKey = document.querySelector('input[name="joinPartyKey"]').value;

		// 버튼을 추가할 div 요소를 선택
		var buttonContainer = document.querySelector('.store-container');

		// 파티 삭제 버튼 생성
		var leaveButton = document.createElement('input');
		leaveButton.type = 'button';
		leaveButton.className = 'm-2 btn btn-sm btn-plus bg-light border';
		leaveButton.onclick = function() {
			leaveParty(partyJoinKey); // 파티 삭제 함수 호출
		};

		// 현재 유저가 파티장이면서, 인원이 혼자라면
		if (partyLeaderId === loginUserId && partyMembersSize <= 1) {
			leaveButton.value = '파티 삭제';
		} else { // 파티장이 아니거나 파티가 여러명이라면
			leaveButton.value = '파티 탈퇴';
		}

		buttonContainer.appendChild(leaveButton); // 버튼을 div에 추가
	});

    function leaveParty(partyJoinKey) {
		$.ajax({
			url: '/party/' + partyJoinKey,
			method: 'DELETE',
			success: function(data) {
				$('.container').html(data);
			},
			error: function() {
				alert('페이지를 로드하는 데 실패했습니다.');
			}
		});
    }
