// 파티 index 페이지의 partyItem에 클릭 이벤트 삽입
document.addEventListener('DOMContentLoaded', function() {
    const partyItems = document.querySelectorAll('.party_item');
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