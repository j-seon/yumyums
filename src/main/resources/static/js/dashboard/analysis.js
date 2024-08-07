var ctxLine
var lineChart
var table

// 차트 데이터 설정
 function setChart(term) {
    var goUrl=''
    switch(term) {
        case 'day':   goUrl = "dailySums"; break;
        case 'month': goUrl = "monthlySums"; break;
        case 'year':  goUrl = "yearlySums"; break;
    }
    $.ajax({
        url: "/api/v1/"+goUrl , // URL 경로에 categoryText를 포함
        method: "get", // GET 메소드 사용
        data:{storeId : storeId},
        success: function(data, status, xhr) {
            var labels = data.map(function(item) {
                return item.date;
            });

            var dataset = data.map(function(item) {
                return item.totalPrice;
            });

            lineChart.data.labels = labels;
            lineChart.data.datasets[0].data = dataset;
            lineChart.update();
        },
        error: function(xhr, status, error) {
            console.error("Error occurred:", error);
        }
    });
}

//차트 생성
function createChart() {
   ctxLine = document.getElementById('lineChart').getContext('2d');
   lineChart = new Chart(ctxLine, {
       type: 'line',
       data: {
           labels: [],
           datasets: [{
               data: [],
               label: '매출액',
               fill: false,
               borderColor: 'rgba(75, 192, 192, 1)',
               tension: 0.1
           }]
       },
       options: {
           scales: {
               y: {
                   beginAtZero: true
               }
           },
            onClick: function(event, elements) {
              if (elements.length > 0) { // 클릭된 요소가 있는지 확인
                  const firstElement = elements[0];

                  const datasetIndex = firstElement.datasetIndex;
                  const index = firstElement._index;

                  const label = lineChart.data.labels[index];
                  const value = lineChart.data.datasets[0].data[index];

                  console.log('Label:', label, 'Value:', value);
              } else {
                  console.log('No element clicked');
              }
          }
       }
   });
}
function createTable(){
    if ($.fn.DataTable.isDataTable('#myTable')) {
        $('#myTable').DataTable().clear().destroy(); // 기존 테이블 제거
    }

    table = $('#myTable').DataTable({
        columns: [
            { title: "메뉴" },
            { title: "가격" },
            { title: "판매 수량" },
            { title: "평점" }
        ],
        data: []
    });
}
function setTable(){
var goUrl=''
    $.ajax({
        url: "/api/v1/menuInfo" , // URL 경로에 categoryText를 포함
        method: "get", // GET 메소드 사용
        data:{storeId : storeId},
        success: function(data, status, xhr) {
            data.forEach((menuInfo) => {
                table.row.add([menuInfo.name, menuInfo.price, menuInfo.orderCount ,menuInfo.avgRate]).draw();
             });
        },
        error: function(xhr, status, error) {
            console.error("Error occurred:", error);
        }
    });
}

$(document).ready(function() {
    createChart();
    setChart("day");
    createTable();
    setTable();

    $('.nav-link').on('click', function(e) {
        e.preventDefault();
        var term = $(this).data('term')
        setChart(term);
    })

});

