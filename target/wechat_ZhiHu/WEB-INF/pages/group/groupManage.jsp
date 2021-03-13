<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/3
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>群聊管理</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/group/groupManage.css"/>
    <script src="../js/group/groupManage.js"></script>
</head>
<body>
<span id="gc" gcid="${gc.gcId}" headImg="${gc.headImg}" style="display: none" uid="${user.id}" username="${user.username}"></span>
<div class="body">
    <div id="group">
        <div id="groupTit">
            <span id="gsName">聊天系统</span>
            (<span id="gcId">815249638</span>)
            <span id="addGroup">[添加群聊]</span>
        </div>
        <div id="groupSet">
            群成员人数:<span id="gsNumber">3/200</span>
            <span class="gcButton" id="addGuser">添加成员</span>
            <span class="gcButton" id="delGuser">删除成员</span>
            <span class="gcButton" id="delGroup">解散群聊</span>
        </div>
        <div id="gUsersTit">
                <input id="selectAll" type="checkbox" selectit="false">
                <span class="list" style="visibility: hidden">0</span>
                <span class="userImgAndName">成员</span>
                <span class="inGroupName">群昵称</span>
                <span class="dsqId">DSQ号</span>
                <span class="sex">性别</span>
                <span class="enterTime">入群时间</span>
                <span class="lastMsg">最后发言</span>
        </div>
        <div id="groupUsers">
        </div>
    </div>
</div>
<jsp:include page="addFriendModel.jsp"></jsp:include>
</body>
</html>
