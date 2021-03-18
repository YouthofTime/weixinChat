<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2020/12/27
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="icon" href="../img/微信.ico" type="image/x-icon">
    <title>微信3.0</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <script src="../js/weixinCollect.js"></script>
</head>
<script>
    $(function () {
        var collectImg=$("#collectImg");
        collectImg.click(function () {
            var src="../img/index/leftPanle/"+$(this).attr("name")+"opt.png";
            $(this).attr("src",src);
        });
        collectImg.trigger("click");
    })
</script>
<body>
<span id="user" userid="${user.id}" username="${user.username}" selectId="${selectId}"></span>
<div id="index">
    <%@include file="common/left.jsp"%>
    <div id="UserSelectCollect">
        <%@include file="common/search.jsp"%>
        <div id="selectCollect">
            <div id="newNote">
                <span class="glyphicon glyphicon-pencil"></span>
                <span>新建笔记</span>
            </div>
            <div id="collects">
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-th-large"></span>
                    <span>全部收藏</span>
                </div>
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-link"></span>
                    <span>链接</span>
                </div>
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-picture"></span>
                    <span>相册</span>
                </div>
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-book"></span>
                    <span>笔记</span>
                </div>
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-file"></span>
                    <span>文件</span>
                </div>
                <div class="oneCollect">
                    <span class="glyphicon glyphicon-music"></span>
                    <span>音乐</span>
                </div>
            </div>
            <div id="label">
                <span class="glyphicon glyphicon-tag"></span>
                <span style="margin-left: 5px">标签</span>
                <span id="labelupdown" class="glyphicon glyphicon-menu-down" name="down" style="float: right;margin:10px 10px 0px 0px;color: #999999"></span>
            </div>
            <div class="labelSon oneCollect">
                四级作文
            </div>
<%--            <div id="collectBottom">--%>
<%--                拖拽文件至次直接新建收藏<br>--%>
<%--                <span style="margin-right: 8px">已使用15.5M,</span>--%>
<%--                <span>总可用2.0G</span>--%>
<%--            </div>--%>
        </div>
    </div>
    <div id="UserChatFrame">
        <img src="../img/index/RightPanle/weixin.png">
    </div>
</div>

</body>

</html>
