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
                case '파티매칭':
                    loadPageContent('party_match');
                    break;
            }
        });
    });

    //파티 매칭으로 가기 버튼을 누르면 리다이렉트
    const partyMatchLink = document.getElementById('partyMatchLink');
    partyMatchLink.addEventListener('click', function(e) {
        e.preventDefault(); // 기본 클릭 동작을 막음
        window.location.href = '/party/match'; // 새로운 URL로 이동
    });
});

// 필요한 페이지를 가져오는 메소드
function loadPageContent(targetPage) {

    $.ajax({
        url: '/party',
        data: { targetPage: targetPage },
        method: 'GET',
        success: function(data) {
            $('#party-container').html(data);

            // 이동할 페이지가 파티생성 페이지라면
            if (targetPage === 'create_party') {
                initializeFormToggles();
                checkValidCreatePartyInfo();
            }

            // 이동할 페이지가 파티매칭 페이지라면
            if (targetPage === 'party_match') {
                checkValidCreatePartyInfo();
            }
        },
        error: function() {
            alert('페이지를 로드하는 데 실패했습니다.');
        }
    });
}

// create_party로 넘어갈때 시작하는 것 (파티 생성 유효성 검사)
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