
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

		// 복사 완료 메시지 (선택 사항)
		alert('링크가 복사되었습니다' + joinPartyUrl);
	}



	$(document).ready(function() {

		// party-leader-id 값을 가져옵니다.
		var partyLeaderId = document.querySelector('input[name="party-leader-id"]').value;
		var partyJoinKey = document.querySelector('input[name="joinPartyKey"]').value;

		// 버튼을 추가할 div 요소를 선택합니다.
		var buttonContainer = document.querySelector('.party-button');

		// 조건에 따라 버튼을 생성합니다.
		if (partyLeaderId === loginUserId && partyMembersSize <= 1) {
			// 파티 삭제 버튼 생성
			var deleteButton = document.createElement('input');
			deleteButton.type = 'button';
			deleteButton.value = '파티 삭제';
			deleteButton.onclick = function() {
				leaveParty(partyJoinKey); // 파티 삭제 함수 호출
				alert("파티 삭제지롱")
			};
			buttonContainer.appendChild(deleteButton); // 버튼을 div에 추가
		} else {
			// 파티 탈퇴 버튼 생성
			var leaveButton = document.createElement('input');
			leaveButton.type = 'button';
			leaveButton.value = '파티 탈퇴';
			leaveButton.onclick = function() {
				leaveParty(partyJoinKey); // 파티 탈퇴 함수 호출
				alert("파티 탈퇴지롱")
			};
			buttonContainer.appendChild(leaveButton); // 버튼을 div에 추가
		}
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
