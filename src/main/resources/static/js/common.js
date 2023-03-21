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
