<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/1
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<html>
<head>
    <title>Title</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/apply/addModel.css">
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>
<body>
<div class="modal " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height:365px;width: 458px">
            <div class="modal-header">
                <div id="modalTop" class="positionSet">
                    <img style="width: 20px;height: 20px" src="../img/qq.png">
                    <span id="applyName" uid="${user.id}">${user.username}</span>
                    <span>-</span>
                    <span>添加好友</span>
                    <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                </div>
            </div>
            <div class="modal-body">
                <div id="modalLeft" class="positionSet">
                    <img src="../img/headphoto/郑宇明.jpg">
                    <div id="beApplyName" rid="3">花朝初二</div>
                    <div id="beApplyDsqId">2938441813</div>
                    <div id="otherMsg">
                        <div id="beApplySex">性别:男</div>
                        <div id="beApplyAge">年龄:22岁</div>
                        <div id="beApplyAddress">所在地:湖南 邵阳</div>
                    </div>
                </div>
                <div id="modalRight1" >
                    <div>请输入验证信息</div>
                    <textarea id="textConfirm">我是</textarea>
                </div>
                <div id="modalRight2" style="display: none">
                    <div>
                        <span>备注姓名:</span>
                        <input id="remark" placeholder="选填" type="text">
                    </div>
                    <div>
                        <span>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;组:</span>
                        <select id="groupSplit">
                            <option>狐妖涂山</option>
                            <option>家人</option>
                            <option>朋友</option>
                        </select>
                        <span id="addGsplit">新建分组</span>
                    </div>
                    <button id="confirmGsName" style="float: right">确认</button>
                    <input id="inputGsName"type="text" placeholder="请输入分组名称">

                </div>
                <div id="modalRight3" style="display: none">
                    <img src="../img/smallIcon/yes.png">
                    <span>你的好友添加请求已经发送成功,正在等待对方确认。</span>
                </div>
            </div>
            <div id="modalBottom" >
                <div data-dismiss="modal" id="closeButton">关闭</div>
                <div id="nextStepButton" >下一步</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


</body>
</html>
