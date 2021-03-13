<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2020/12/27
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/weixinMain.css"/>
    <script src="../js/weixinMain.js"></script>
</head>
<body>
    <div id="UserHandle">
        <img id="headPhoto" src="/img/headphoto/${user.headImg}">
        <img  name="chat" id="chatImg" src="../img/index/leftPanle/chat.png">
        <img  name="friend" id="friendImg" src="../img/index/leftPanle/friend.png">
        <img  name="collect" id="collectImg" src="../img/index/leftPanle/collect.png">
        <img  src="../img/index/leftPanle/file.png">
        <img  id="look" src="../img/index/leftPanle/look.png">
        <div id="UserBottom">
            <img  src="../img/index/leftPanle/smallProcedure.png">
            <img  src="../img/index/leftPanle/phone.png">
            <img  id="setImg" src="../img/index/leftPanle/set.png">
        </div>
    </div>
    <jsp:include page="common/imgUpLoad.jsp"></jsp:include>
    <jsp:include page="common/headImgModel.jsp"></jsp:include>
    <jsp:include page="common/editPersonal.jsp"></jsp:include>

</body>
</html>
