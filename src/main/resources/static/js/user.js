/**
 * 詳細画面遷移
 */
function showDetail(id) {
    let form = $('#detailForm');
    $('<input>').attr({
        type: 'hidden',
        name: 'userId',
        value: id
    }).appendTo(form);
    form.submit();
}
