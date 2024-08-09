

// 웹소켓 연결
function connectWebSocket(encryptedPartyId) {
    var socket = new SockJS("/ws-stomp");
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected to party socket ' + encryptedPartyId + ': ' + frame);

        // 구독
        stompClient.subscribe('/topic/party/' + encryptedPartyId, function (message) {
            console.log("Received message: ", message.body);  // 메시지 로그 추가
            const partyMemberDtoAndMassage = JSON.parse(message.body);

            //파티 탈퇴라면
            if (partyMemberDtoAndMassage.action === 'leaveParty') {
                // 파티원을 제거하는 함수 호출
                removePartyMemberDiv(partyMemberDtoAndMassage.memberId);

                // 새로운 파티장이 설정된 경우 HTML 업데이트
                if (partyMemberDtoAndMassage.leaderChange) {
                    updatePartyLeaderInfo(partyMemberDtoAndMassage.newLeaderMemberId);
                }
            // 파티원 추가라면
            } else {
                // 새로운 파티원 추가
                showNewPartyMember(partyMemberDtoAndMassage);
            }
        });
    });
}

//추가된 파티원의 정보값을 출력
function showNewPartyMember(partyMemberDTO) {
    // party-member-container 요소를 가져옴
    const partyMemberListDiv = document.getElementById('party-member-container');

    // 새로운 파티원 정보를 담을 div 생성
    const newPartyMemberDiv = document.createElement('div');
    newPartyMemberDiv.className = 'col-md-6 col-lg-6 col-xl-3';

    // 파티원의 정보값을 담음
    newPartyMemberDiv.innerHTML = `
        <div class="counter bg-white rounded p-5">
            <!-- 프로필 이미지로 변경할 것 -->
            <i class="fa fa-users text-secondary"></i>
            <div class="d-flex justify-content-center">
                <h4>파티원</h4>
            </div>
            <h2>${partyMemberDTO.memberDTO.name}</h2>
        </div>
        <input type="hidden" name="party-leader-id" value="${partyMemberDTO.memberDTO.memberId}">
    `;

    // 파티 초대 아이콘을 가져옴
    const inviteIcon = partyMemberListDiv.querySelector('svg.bi-person-plus');

    // 파티 멤버 리스트에 새로 추가된 파티원 정보 삽입 (파티 초대 아이콘 앞에)
    partyMemberListDiv.insertBefore(newPartyMemberDiv, inviteIcon);

    // 파티원 수 증가
    partyMembersSize++;  // 파티원 수 증가

    // 파티 탈퇴/삭제 버튼 업데이트
    updatePartyLeaveButton();
}


// 파티 탈퇴시 파티한 파티원의 정보폼을 삭제
function removePartyMemberDiv(memberId) {
    const partyMemberListDiv = document.getElementById('party-member-container');
    const memberDivs = partyMemberListDiv.getElementsByClassName('col-md-6 col-lg-6 col-xl-3'); // 파티원 div 클래스 이름에 맞춰 수정

    // 파티원 목록에서 해당 사용자의 <div>를 찾기
    for (let div of memberDivs) {
        const hiddenInput = div.querySelector('input[name="party-leader-id"]'); // 여기서 hidden input의 value 확인

        // hiddenInput에서 사용자의 ID를 추출
        if (hiddenInput && hiddenInput.value === memberId) {
            // 해당 사용자의 <div>를 제거.
            partyMemberListDiv.removeChild(div); // div 직접 제거
            break; // 찾았으니 루프 종료
        }
    }

    // 파티원 수 감소
    partyMembersSize--;  // 파티원 수 감소

    // 파티 탈퇴/삭제 버튼 업데이트
    updatePartyLeaveButton();
}

// 파티장이 변경될 경우 새로운 파티장의 HTML을 변경
function updatePartyLeaderInfo(newLeaderId) {
    const partyMemberListDiv = document.getElementById('party-member-container');
    const memberDivs = partyMemberListDiv.getElementsByClassName('col-md-6 col-lg-6 col-xl-3');

    // 파티원 목록에서 새로운 파티장이 될 멤버의 <div>를 찾기
    for (let div of memberDivs) {
        const hiddenInput = div.querySelector('input[name="party-leader-id"]');

        // 새로운 파티장 ID가 hiddenInput의 값과 일치하면
        if (hiddenInput && hiddenInput.value === newLeaderId) {
            // 파티장 정보를 업데이트
            const titleDiv = div.querySelector('div.d-flex.justify-content-center');

            // 왕관 아이콘 추가
            titleDiv.innerHTML = `
                <h4 class="bi bi-stars text-secondary" alt="파티장 왕관"></h4>
                <h4>파티장</h4>
            `;

            // 추가적인 스타일 조정이 필요할 수 있음
            break; // 찾았으니 루프 종료
        }
    }
}

// 파티 탈퇴,삭제 버튼 상태 업데이트 함수
function updatePartyLeaveButton() {
    var partyLeaderId = document.querySelector('input[name="party-leader-id"]').value;
    var encryptedPartyId = document.querySelector('input[name="joinPartyKey"]').value;
    var buttonContainer = document.querySelector('.store-container');

    // 이미 존재하는 버튼이 있다면 삭제
    var existingButton = buttonContainer.querySelector('input[type="button"]');
    if (existingButton) {
        buttonContainer.removeChild(existingButton);
    }

    // 파티 삭제/탈퇴 버튼 생성
    var leaveButton = document.createElement('input');
    leaveButton.type = 'button';
    leaveButton.className = 'm-2 btn btn-sm btn-plus bg-light border';
    leaveButton.onclick = function() {
        leaveParty(encryptedPartyId); // 파티 삭제/탈퇴 함수 호출
    };

    // 현재 유저가 파티장이면서, 인원이 혼자라면
    if (partyLeaderId === loginUserId && partyMembersSize <= 1) {
        leaveButton.value = '파티 삭제';
    } else { // 파티장이 아니거나 파티가 여러명이라면
        leaveButton.value = '파티 탈퇴';
    }

    // 버튼을 div에 추가
    buttonContainer.appendChild(leaveButton);
}


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

// 파티 탈퇴요청
function leaveParty(encryptedPartyId) {
    $.ajax({
        url: '/party/' + encryptedPartyId,
        method: 'DELETE',
        success: function(data) {
            // 페이지 내용 업데이트
            $('.container').html(data);

            // 파티 탈퇴 후 웹소켓을 통해 사용자에게 알림 전송
            if (websocket) {
                websocket.send(JSON.stringify({
                    action: 'leaveParty',
                    partyId: encryptedPartyId,
                    memberId: loginUserId,  // 현재 사용자의 ID
                }));
            }
        },
        error: function() {
            alert('페이지를 로드하는 데 실패했습니다.');
        }
    });
}


// 페이지가 로딩되면 실행하는 것들
$(document).ready(function() {

    //== 파티 탈퇴, 파티 삭제 버튼 추가 ==//
    // party-leader-id 값을 가져오기
    var partyLeaderId = document.querySelector('input[name="party-leader-id"]').value;
    var encryptedPartyId = document.querySelector('input[name="joinPartyKey"]').value;

    // 버튼을 추가할 div 요소를 선택
    var buttonContainer = document.querySelector('.store-container');

    // 파티 삭제 버튼 생성
    var leaveButton = document.createElement('input');
    leaveButton.type = 'button';
    leaveButton.className = 'm-2 btn btn-sm btn-plus bg-light border';
    leaveButton.onclick = function() {
        leaveParty(encryptedPartyId); // 파티 삭제 함수 호출
    };

    // 현재 유저가 파티장이면서, 인원이 혼자라면
    if (partyLeaderId === loginUserId && partyMembersSize <= 1) {
        leaveButton.value = '파티 삭제';
    } else { // 파티장이 아니거나 파티가 여러명이라면
        leaveButton.value = '파티 탈퇴';
    }
    // 버튼을 div에 추가
    buttonContainer.appendChild(leaveButton);

    //== 웹소켓에 연결 ==//
    connectWebSocket(encryptedPartyId)
});


function redirectToSelectMenu() {
    const baseUrl = window.location.origin;
    const url = `${baseUrl}/menu/${storeId}?joinPage=party&partyId=${joinPartyKey}`;

    window.location.href = url;
}