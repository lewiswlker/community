$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");

    // 发送AJAX请求之前，将CSRF令牌设置到请求消息头中
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function (e, xhr, options) {
    //     xhr.setRequestHeader(header, token);
    // })

    // 获取标题内容
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();
    $.post(
        CONTEXT_PATH + "/discuss/add",
        {"title": title, "content": content},
        function (data) {
            data = $.parseJSON(data);
            // 在提示框显示返回消息
            $("#hintBody").text(data.msg);
            // 显示提示框
            $("#hintModal").modal("show");
            // 1秒后自动隐藏提升框
            setTimeout(
                function () {
                    $("#hintModal").modal("hide");
                    // 如果成功，刷新页面
                    if(data.code == 0) {
                        window.location.reload();
                    }
                }, 1000);
        }
    );
}