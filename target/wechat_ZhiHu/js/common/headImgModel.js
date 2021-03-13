$(function () {
    /*1.图标更换点击(点击退出登录那里的图片)*/
    $("#headModel #headImg").click(function () {
        $("#headModel").modal("hide");
        $("#uploadModel").modal("show");
        $("#uploadModel form").attr("actioon","/weixin/uploadImg");
    });
})