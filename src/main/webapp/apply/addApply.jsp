<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<head>
    <meta charset="UTF-8">
    <title>添加好友</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <script src="../js/apply/addApply.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/apply/addApply.css">
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>
<body>
    <div id="addFriend" uid="${user.id}" username="${user.username}" headImg="${user.headImg}">
        <div id="top">
            <img style="width: 20px;height: 20px;position: relative;top:5px;left: 2px;" src="../img/qq.png">
            <span style="color: white;font-size: 12px;">查找</span>
        </div>
        <div id="find">
            <div class="left">
                <div id="inputBorder">
                    <input id="inputQQ" style="width: 240px; position: relative;
        top:5px;" placeholder="请输入QQ号码/昵称">
                </div>
                <div id="inputBorder1">
                    <input id="inputGroup" style="width: 240px; position: relative;
        top:5px;" placeholder="请输入群聊号码/昵称">
                </div>
            </div><!--.left-->
            <div id="findButton" class="left" >
                查找
            </div>
            <div id="findButton1" class="left" >
                查找
            </div>
        </div>
        <div id="meetUser">

        </div>
        <div id="bottom">
            <div style="font-size: 12px;color: #999999;position: absolute;bottom: 5px;right: 30px;">好友恢复不要急,批量恢复来帮你!</div>
        </div>
    </div>
    <jsp:include page="addModel.jsp"></jsp:include>
</body>
</html>