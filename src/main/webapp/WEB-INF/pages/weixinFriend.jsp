<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2020/12/27
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

    <link rel="icon" href="../img/微信.ico" type="image/x-icon">
    <title>微信3.0</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <script src="../js/weixinFriend.js"></script>
</head>
<script>
    $(function () {
        var friendImg=$("#friendImg");
        // 默认选择(切换颜色)
        friendImg.click(function () {
            var src="../img/index/leftPanle/"+$(this).attr("name")+"opt.png";
            $(this).attr("src",src);
        });
        friendImg.trigger("click");
    })

</script>
<body>
<span id="user" userid="${user.id}" username="${user.username}" selectId="${selectId}" gssize="${fn:length(user.groupSplits)}"></span>
<div id="index">
    <%@include file="common/left.jsp"%>
    <div id="UserSelectFriend">
        <%@include file="common/search.jsp"%>
        <div id="selectFriend">
            <div id="ipaManage">
                <span class="glyphicon glyphicon-user"></span>
                <span>通讯录管理</span>
            </div>
            <div id="newFriend" class="oneFriend">
                <div class="tagName">验证消息</div>
                <div class="friendImgAndName">
                                <span class="friendPhoto">
                                    <img src="../img/index/MiddlePanel/验证消息.png">
                                </span>
                    <span class="friendName">验证消息</span>
                </div>
            </div>
            <!--加入的群聊-->
            <div  class="oneFriend groups">
                <div class="tagName" >
                    群聊
                    <span class="glyphicon glyphicon-menu-down labelupdown" name="down" style="float: right;margin:10px 10px 0px 0px;color: #999999"></span>
                </div>
                <!--群聊-->
                <c:forEach items="${gc}" var="group">
                    <div class="friendImgAndName" gcId="${group.gcId}">
                                        <span class="friendPhoto">
                                            <img src="../img/headphoto/${group.headImg}">
                                        </span>
                        <span class="friendName" >${group.gcname}</span>
                    </div>
                </c:forEach>
            </div>
            <!--用户拥有的分组-->
            <c:forEach items="${user.groupSplits}" var="groupsplit">
                <div class="oneFriend friends">
                    <div class="tagName" gsId="${groupsplit.gsId}">
                        <input class="inputGsName" type="text" value="${groupsplit.gsname}">
                        <span class="glyphicon glyphicon-menu-down labelupdown" name="down" style="float: right;margin:10px 10px 0px 0px;color: #999999">
                        </span>
                    </div>
                    <!--分组下的好友-->
                    <c:forEach items="${gs[groupsplit.gsId]}" var="gs">
                        <div class="friendImgAndName" uid="${gs.id}">
                                        <span class="friendPhoto">
                                            <img src="../img/headphoto/${gs.headImg}">
                                        </span>
                            <span class="friendName" >${gs.username}</span>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
            <div>
                <span id="addGsplit">新建分组</span>
            </div>
            <button id="confirmGsName" style="float: right">确认</button>
            <input id="inputGsName"type="text" placeholder="请输入分组名称">
        </div>
    </div>
    <div id="UserChatFrame">
        <img src="../img/index/RightPanle/weixin.png">
    </div>
    <div id="SelectGroupChat" style="display: none">
        <div id="gc_name">
            <span id="gcname"></span>
            <span id="gcnumber"></span>
            <a id="supManage" target="_blank">高级管理</a>
        </div>
        <div id="gcUsers">
        </div>
        <div class="sendMsg">
            <div class="sendMsgButton" >发消息</div>
        </div>
    </div>
    <div id="SelectFriendChat" style="display: none">
        <div id="nameAndImg">
            <span id="name" uid=""></span>
            <img class="sex" src="">
            <img  id="InfoHeadImg" src="">
        </div>
        <div id="personalResume">
            <div class="line">
                <span class="first">备&nbsp;&nbsp;&nbsp;注</span>
                <input id="remark" type="text" value="">
            </div>
            <div class="line">
                <span class="first">地&nbsp;&nbsp;&nbsp;区</span>
                <span id="address"></span>
            </div>
            <div class="line">
                <span class="first">微信号</span>
                <span id="weixin">CYTHEDOCTORS</span>
            </div>
            <div class="line">
                <span class="first">来&nbsp;&nbsp;&nbsp;源</span>
                <span id="from">通过群聊添加</span>
            </div>
        </div>
        <div class="sendMsg">
            <div class="sendMsgButton">发消息</div>
        </div>

    </div>
    <div id="SelectNewFriend" style="display: none">
        <div class="NFName">
            验证消息
        </div>
        <div id="applyUsers">

        </div>
    </div>
</div>

<jsp:include page="common/rClickModel.jsp"></jsp:include>


</body>
</html>
