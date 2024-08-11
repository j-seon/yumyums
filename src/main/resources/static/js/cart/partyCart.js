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
connectWebSocket(partyId);



// PartyCart 아이템 업데이트 함수
function updatePartyCartItems(partyCartDTO) {
    const memberName = partyCartDTO.memberDTO.name; // DTO에서 회원 이름 가져오기
    const cartItemsContainer = document.querySelector(`.cart-items[data-member-name="${memberName}"] tbody`);

    // 기존 아이템 찾기
    const existingRow = [...cartItemsContainer.rows].find(row => {
        return row.cells[0].innerText === partyCartDTO.menuDTO.name; // 메뉴 이름으로 확인
    });

    if (existingRow) {
        // 기존 아이템의 수량과 합계 업데이트
		const newCount = partyCartDTO.menuCount;
		const quantityInput = existingRow.cells[2].querySelector('input');
		const totalPriceElem = existingRow.cells[3].querySelector('p.total-price');

		if (quantityInput) {
			quantityInput.value = newCount; // 수량 업데이트
		}
		if (totalPriceElem) {
			totalPriceElem.innerText = (partyCartDTO.menuDTO.price * newCount) + ' 원'; // 합계 업데이트
		}
    } else {
        // 신규 아이템이 없을 경우, 새 아이템 추가
        const newRow = document.createElement("tr");
        newRow.innerHTML = `
            <td class="align-middle">
                <p class="mb-0">${partyCartDTO.menuDTO.name}</p>
            </td>
            <td class="align-middle">
                <p class="mb-0">${partyCartDTO.menuDTO.price} 원</p>
            </td>
            <td class="align-middle">
                <input type="number" name="menuCount" min="1"
                       value="${partyCartDTO.menuCount}"
                       class="form-control text-center border-0"
                       data-price="${partyCartDTO.menuDTO.price}"
                       data-menu-id="${partyCartDTO.menuDTO.id}"
                       oninput="updateTotalPrice(this)"
                       style="background-color: white;"
                       readonly>
            </td>
            <td class="align-middle">
                <p class="mb-0 total-price">${partyCartDTO.menuDTO.price * partyCartDTO.menuCount} 원</p>
            </td>
        `;
        cartItemsContainer.appendChild(newRow);
    }
}
