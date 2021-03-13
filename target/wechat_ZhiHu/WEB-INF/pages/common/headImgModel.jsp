<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/11
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../js/common/headImgModel.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/common/headImgModel.css"/>
</head>
<body>
<div class="modal " id="headModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height:240px;width: 300px">
            <div class="modal-header">
               <div id="headLeft">
                   <div id="headName" style="margin-bottom: 5px;">
                       ${user.username}
                       <c:if test="${!empty user.sex}">
                           <img src="../img/index/leftPanle/friend/${user.sex}性.png">
                       </c:if>

                   </div>
                   <div id="headDsqId">DSQ号:${user.dsqId}</div>
               </div>
               <img id="headImg" src="../img/headphoto/${user.headImg}">
            </div>
            <div class="modal-body">
                <span style="margin:10px 20px">地区</span>
                <span style="margin-top:10px">${user.address}</span><br>
                <span id="editInfo">编辑资料</span>
                <span id="backLogin">退出登录</span>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

</body>
</html>
