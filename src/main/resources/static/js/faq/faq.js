$(document).ready(function() {
    $('.nav-item a').on('click', function(e) {
        e.preventDefault();
        var categoryText = $(this).find('div').text();

        $.ajax({
            url: "/faq/" + categoryText, // URL 경로에 categoryText를 포함
            method: "get", // GET 메소드 사용
            success: function(data, status, xhr) {
                console.log(data);
                // 받은 데이터를 처리하는 로직을 추가할 수 있습니다.
                var contentHtml = '<div id="Accordion_wrap">';

                $.each(data, function(index, faq) {
                    contentHtml += '<div class="que">';
                    contentHtml += '<span>' + faq.title + '</span>';
                    contentHtml += '<div class="arrow-wrap">';
                    contentHtml += '<span class="arrow-top">↑</span>';
                    contentHtml += '<span class="arrow-bottom">↓</span>';
                    contentHtml += '</div>';
                    contentHtml += '</div>';
                    contentHtml += '<div class="anw">';
                    contentHtml += '<span>' + faq.content + '</span>';
                    contentHtml += '</div>';
                });

                contentHtml += '</div>';
                $("div.tab-content").empty().append(contentHtml);

                $(".que").off("click").on("click", function() {
                    $(this).next(".anw").stop().slideToggle(300);
                    $(this).toggleClass('on').siblings().removeClass('on');
                    $(this).next(".anw").siblings(".anw").slideUp(300); // 1개씩 펼치기
                });
            },
            error: function(xhr, status, error) {
                console.error("Error occurred:", error);
            }
        });

        $('.nav-item a').removeClass('active');
        $(this).addClass('active');
    });
});
