$(function () {
    // 入力欄押下時にスクロール（ページトップ遷移）を無効化(focusだとダメだった)
    /*$('input, select, textarea').on('click', function() {
        event.preventDefault();
    });*/
    // というかどこ押してもページトップに行ってしまうので無効化
    $('div').on('click', function() {
        event.preventDefault();
    });
    // ボタン押下時にスクロールを無効化
    $('button').on('click', function() {
        event.preventDefault();
    });
});

