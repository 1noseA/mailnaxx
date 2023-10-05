/* --------------------------------
    パスワード表示非表示
-------------------------------- */
$(function() {
    $(".togglePass").on("click", function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $(this).prev("input");
        if (input.attr("type") == "text") {
            input.attr("type", "password");
        } else {
            input.attr("type", "text");
        }
    });
});

/* --------------------------------
    日プルダウン生成
-------------------------------- */
$(function() {
    $('#yearList, #monthList').on('change', function() {
        var year = $('#yearList').val();
        var month = $('#monthList').val();
        if (month === "") {
            return
        }
        var daysInMonth = new Date(year, month, 0).getDate();
        $('#dayList').empty();
        for (var i = 1; i <= daysInMonth; i++) {
            $('#dayList').append($('<option>', {
                value: i,
                text: i
            }));
        }
        if (month === 2 && isLeapYear(year)) {
            $('#dayList').append($('<option>', {
                value: 29,
                text: 29
            }));
        }
    });
});

/* --------------------------------
    うるう年判定
-------------------------------- */
function isLeapYear(year) {
    if( year % 4 === 0 ) {
        if( year % 100 === 0 ){
            if( year % 400 !== 0 ) return false;
        }
        return true;
    }
    return false;
}