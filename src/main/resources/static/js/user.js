/**
 * 詳細画面遷移
 */
function showDetail(id) {
    let form = document.getElementById('detailForm');
    let input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', 'user_id');
    input.setAttribute('value', id);
    form.appendChild(input);

    form.submit();
}