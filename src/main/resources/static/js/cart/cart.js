document.addEventListener('DOMContentLoaded', (event) => {
    updateTotalCartPrice();
});

function updateTotalPrice(element) {
    const row = element.closest('tr');
    const price = parseInt(row.querySelector('[data-price]').getAttribute('data-price'));
    console.log('query :' ,row.querySelector('[data-price]'));
    console.log('getAtt :' ,row.querySelector('[data-price]').getAttribute('data-price'));

    const quantity = parseInt(element.value);
    const totalPriceElement = row.querySelector('.total-price');

    const totalPrice = parseInt(price * quantity);
    totalPriceElement.innerText = totalPrice + ' 원';

    // 서버에 업데이트 요청 보내기
    const menuId = row.querySelector('input[name="menuId"]').value;
    updateMenuCountOnServer(menuId, quantity);

    // 전체 메뉴의 총 가격 업데이트
    updateTotalCartPrice();
}

function updateTotalCartPrice() {
    const quantityInputs = document.querySelectorAll('input[name="menuCount"]');
    let totalOrderAmount = 0;
    let totalItems =0;

    // 모든 메뉴의 총 가격을 합산
    document.querySelectorAll('.total-price').forEach(function(totalPriceElement) {
        const price = parseInt(totalPriceElement.innerText.replace(' 원', ''));
        totalOrderAmount += price;
    });

    quantityInputs.forEach(input => {
        const quantity = parseInt(input.value);
        totalItems += quantity;
    });

    // 전체 총 가격을 표시할 요소를 업데이트
    document.getElementById('totalOrderAmount').innerText = totalOrderAmount + ' 원';
    document.getElementById('totalAmount').innerText = totalOrderAmount + ' 원';
    document.getElementById('orderButton').innerText = '총 ' + totalItems + '개 주문하기';
}

function updateMenuCountOnServer(menuId, quantity) {
    fetch('/cart/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            'menuId': menuId,
            'menuCount': quantity
        })
    }).then(response => {
        if (response.ok) {
            updateTotalCartPrice();
        } else {
            console.error('수량변경 에러');
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}

function confirmDeletion(event) {
    return confirm('정말로 삭제하시겠습니까?');
}