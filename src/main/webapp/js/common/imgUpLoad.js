$(function () {
    /*1.点击上传本地照片div*/
    $("#uploadDiv").click(function () {
        $("#hideUpload").trigger("click");
    });
    /*2.点击确认上传*/
    $("#confrimUpload").click(function () {
        $("#hidesubmit").trigger("click");
    })
    /*取消上传*/
    $("#cancelUpload").click(function () {
        $("#uploadModel").modal("hide");
    })
})