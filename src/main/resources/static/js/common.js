/**
 * 年プルダウン作成
 * @param {string} id プルダウンのid属性
 * @param {string} startYear 開始年
 * @param {string} endYear 終了年
 * @param {string} initialYear 初期値
 */
function createYearList(id, startYear, endYear, initialYear) {
    $(id).empty();
    for (let i = startYear; i <= endYear; i++) {
        $(id).append($('<option>').val(i).text(i));
    }
    $(id).val(initialYear);
}

/**
 * 月プルダウン作成
 */
function createMonthList() {
    $('.monthList').empty();
    $('.monthList').append($('<option>').val('').text(''));
    for (let i = 1; i <= 12; i++) {
        $('.monthList').append($('<option>').val(i).text(i));
    }
}
