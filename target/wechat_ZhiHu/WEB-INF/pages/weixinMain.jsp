<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2020/12/23
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="icon" href="../img/微信.ico" type="image/x-icon">
    <title>微信3.0</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/chatRoom.css"/>
    <script src="../js/chatRoom.js"></script>
</head>
<script>
    $(function () {
        // 1.切换图片颜色
        var chatImg=$("#chatImg");
        chatImg.click(function () {
            var src="../img/index/leftPanle/"+$(this).attr("name")+"opt.png";
            $(this).attr("src",src);
        });
        chatImg.trigger("click");
        // 2.刷新页面后设置选中框

    })
    function handleFiles(files) {
        var preview = document.getElementById('preview');
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var imageType = /^image\//;

            if ( !imageType.test(file.type) ) {
                continue;
            }

            var img = document.createElement("img");
            img.style.width="150px";
            img.style.height="80px";
            img.style.margin="5px 0px 0px 40px";
            img.classList.add("obj");
            img.file = file;
            // 假设 "preview" 是将要展示图片的 div
            preview.appendChild(img);

            var reader = new FileReader();
            reader.onload = (function(aImg) {
                return function(e) {
                    aImg.src = e.target.result;
                };
            })(img);
            reader.readAsDataURL(file);
        }
    }
</script>
<body>
<span id="user" userid="${user.id}" username="${user.username}" headImg="${user.headImg}" selectId="${selectId}">
</span>
<div id="jplayer"></div>
<div id="index">
    <%@include file="common/left.jsp"%>
    <div id="UserSelectChat">
        <%@include file="common/search.jsp"%>
        <div id="selectChat">
            <c:forEach items="${gc}" begin="0" end="0" var="item">
                <div class="oneChat" id="topChat" name="top" gcId="${item.gcId}">
                    <span class="chatPhoto">
                         <img src="../img/headphoto/${item.headImg}">
                         <span class="unreadCount" style="display: none">0</span>
                    </span>
                    <span>
                        <div  class="nameAndTime" >
                            <span class="chatName">${item.gcname}</span>
                            <span class="lastTime">${time[groupmsg[item.gcId].gmId]}</span>
                        </div>
                        <!--最后的话是本人说的-->
                        <c:if test="${groupmsg[item.gcId].sendUid==user.id}">
                            <span class="lastMsg">${groupmsg[item.gcId].value}</span>
                        </c:if>
                        <!--不是-->
                        <c:if test="${groupmsg[item.gcId].sendUid!=user.id}">
                                <span class="lastMsg">${groupmsg[item.gcId].sendUser.username}${groupmsg[item.gcId].value}</span>
                        </c:if>
                    </span>
                </div>
            </c:forEach>
            <c:forEach items="${gc}" begin="1" var="item">
                <div class="oneChat" gcId="${item.gcId}">
                    <span class="chatPhoto">
                         <img src="../img/headphoto/${item.headImg}">
                         <span class="unreadCount" style="display: none">0</span>
                    </span>
                    <span>
                        <div  class="nameAndTime">
                            <span class="chatName">${item.gcname}</span>
                            <span class="lastTime">${time[groupmsg[item.gcId].gmId]}</span>
                        </div>
                        <!--最后的话是本人说的-->
                        <c:if test="${groupmsg[item.gcId].sendUid==user.id}">
                            <span class="lastMsg">${groupmsg[item.gcId].value}</span>
                        </c:if>
                        <!--不是-->
                        <c:if test="${groupmsg[item.gcId].sendUid!=user.id}">
                            <span class="lastMsg">${groupmsg[item.gcId].sendUser.username}${groupmsg[item.gcId].value}</span>
                        </c:if>
                    </span>
                </div>
            </c:forEach>
            <c:forEach items="${friends}"  var="item">
                <div class="oneChat" uid="${item.id}">
                    <span class="chatPhoto">
                         <img src="../img/headphoto/${item.headImg}">
                         <span class="unreadCount" style="display: none">0</span>
                    </span>
                    <span>
                        <div  class="nameAndTime">
                            <span class="chatName">${item.username}</span>
                            <span class="lastTime">${time[singlemsg[item.id].singleId]}</span>
                        </div>
                        <!--最后的话是本人说的-->
                        <c:if test="${singlemsg[item.id].sendUid==user.id}">
                            <span class="lastMsg">${singlemsg[item.id].value}</span>
                        </c:if>
                        <!--不是-->
                        <c:if test="${singlemsg[item.id].sendUid!=user.id}">
                            <span class="lastMsg">${singlemsg[item.id].sendUser.username}${singlemsg[item.id].value}</span>
                        </c:if>
                    </span>
                </div>
            </c:forEach>
        </div>
    </div>
    <div id="UserChatFrame">
        <img src="../img/index/RightPanle/weixin.png">
    </div>
    <div id="chat_group">
        <div id="chat_name">
            <span id="name"></span>
            <span id="number"></span>
        </div>
        <div id="chat_frmae">

        </div>
        <div id="send_msg">
            <form action="/weixin/sendFile" method="post" enctype="multipart/form-data">
                <input  id="hideUpload" type="file" name="upload" accept="image/*" onchange="handleFiles(this.files)">
                <input style="display: none" id="sendImgSubmit" type="submit" value="上传文件">
            </form>
            <div id="preview">
            </div>
<%--            <img id="send_file" src="../img/weixinMain/file.png">--%>
<%--            <span style="display: inline-block;margin-left: 30px;"><img src="../img/icon.png"></span>--%>
            <input type="text" id="input" style="border: 0px">
            <button id="send">发送(S)</button>
        </div>
    </div>
</div>
</body>
</html>
