<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/21
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../js/common/editPersonal.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/common/editPersonal.css"/>
    <title>Title</title>
</head>
<body>
<div class="modal " id="edi_Personal_Model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height:365px;width: 370px">
            <div class="modal-header">
                <span class="info_left">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</span><input class="info_right"id="username"  type="text" value=""><br>
                <span class="info_left">手机号码:</span><input class="info_right" id="phone"  type="text" value=""><br>
                <span class="info_left">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</span><input class="info_right" id="password"   type="password" value=""><br>
                <span class="info_left">新的密码:</span><input class="info_right" id="newPassword"  type="password" value=""><br>
                <span class="info_left">确认密码:</span><input class="info_right" id="confrimPassword"  type="password" value=""><br>
                <span class="info_left">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</span><input class="info_right" id="bind_email"  type="text" value=><br>
                <span class="info_left">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</span><input class="info_right" id="address"  type="text" value=""><br>
                <span class="info_left">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</span>
                <select  class="info_right" id="sex">
                    <option>男</option>
                    <option>女</option>
                </select><br>
                <span class="info_left">DS&nbsp;Q&nbsp;I&nbsp;D:</span><input class="info_right" id="dsqId" type="text" value=""><br>
                <div id="submitInfo">提交</div>
            </div>
<%--            <div class="modal-body">--%>

<%--            </div>--%>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>
