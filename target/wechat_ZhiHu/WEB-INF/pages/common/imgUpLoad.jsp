<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/22
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../js/common/imgUpLoad.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/common/imgUpLoad.css"/>
</head>
<body>
<div class="modal " id="uploadModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height:500px;width: 380px">
            <div class="modal-header">
                <form action="/weixin/uploadImg" method="post" enctype="multipart/form-data">
                    <input style="display: none" id="hideUpload" type="file" name="upload" accept="image/*">
                    <input style="display: none" id="hidesubmit" type="submit" value="上传文件">
                </form>
                <div id="uploadDiv">
                    <span class="glyphicon glyphicon-open"></span>
                    <span>上传本地照片</span>
                </div>
                <img id="uploadImg" src="../img/headphoto/${user.headImg}">
            </div>
            <div class="modal-body">
                <span id="confrimUpload">确定</span>
                <span id="cancelUpload">取消</span>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>
