$(function () {
    // クリック時に移動してしまうのを無効化
    $('div').on('click', function() {
        event.preventDefault();
    });
});
