// 웹소켓 연결
function connectWebSocket(encryptedPartyId) {
    var socket = new SockJS("/ws-stomp");
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected to party socket ' + encryptedPartyId + ': ' + frame);

        // 구독
        stompClient.subscribe('/topic/partyCart/' + encryptedPartyId, function (message) {
            var partyCartDTO = JSON.parse(message.body);
            console.log("Received message: ", partyCartDTO);  // 메시지 로그 추가

            // 해당 ID의 회원 목록에 partyCartItem 추가
            updatePartyCartItems(partyCartDTO);
        });
    });
}



// PartyCart 아이템 업데이트 함수
function updatePartyCartItems(partyCartDTO) {
    const memberName = partyCartDTO.memberName; // DTO에서 회원 이름 가져오기
    const cartItemsContainer = document.querySelector(`.cart-items[data-member-name="${memberName}"] tbody`);

    // 기존 아이템 찾기
    const existingRow = [...cartItemsContainer.rows].find(row => {
        return row.cells[0].innerText === partyCartDTO.menuName; // 메뉴 이름으로 확인
    });

    if (existingRow) {
        // 기존 아이템의 수량과 합계 업데이트
        const newCount = parseInt(existingRow.cells[2].innerText) + partyCartDTO.menuCount; // 기존 수량 + 새 수량
        existingRow.cells[2].innerText = newCount; // 수량 업데이트
        existingRow.cells[3].innerText = (partyCartDTO.price * newCount) + ' 원'; // 합계 업데이트
    } else {
        // 신규 아이템이 없을 경우, 새 아이템 추가
        const newRow = document.createElement("tr");
        newRow.innerHTML = `
            <td>${partyCartDTO.menuName}</td>
            <td>${partyCartDTO.price} 원</td>
            <td>${partyCartDTO.menuCount}</td>
            <td>${partyCartDTO.price * partyCartDTO.menuCount} 원</td>
        `;
        cartItemsContainer.appendChild(newRow);
    }
}
