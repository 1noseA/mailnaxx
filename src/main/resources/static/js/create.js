$(function() {
    // 現在
    const nowYear = new Date().getFullYear();

    // 入社年月_年プルダウン作成
    createYearList('#hireYearList', nowYear, nowYear+1, nowYear);

    // 生年月日_年プルダウン作成
    createYearList('#yearList', nowYear-70, nowYear-20, nowYear-30);

    // 月プルダウン作成
    createMonthList();

    // 日プルダウン作成
    $('#yearList, #monthList').on('change', function() {
        const year = $('#yearList').val();
        const month = $('#monthList').val();
        if (month === '') {
            return;
        }
        const daysInMonth = new Date(year, month, 0).getDate();
        $('#dayList').empty();
        $('#dayList').append($('<option>').val('').text(''));
        for (let i = 1; i <= daysInMonth; i++) {
            $('#dayList').append($('<option>').val(i).text(i));
        }
    });

    // パスワード表示非表示
    $(".togglePass").on("click", function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        let input = $(this).prev("input");
        if (input.attr("type") == "text") {
            input.attr("type", "password");
        } else {
            input.attr("type", "text");
        }
    });
});
