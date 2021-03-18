<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宇明
  Date: 2021/2/3
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<html>
<head>
    <title>Title</title>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <script src="../js/group/addFriendModel.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/group/addFriendModel.css">
    <link href="../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>


</head>
<body>
<div class="modal " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content" style="height:500px;width: 600px">
            <div class="modal-header">
                <div id="modalTop" class="positionSet">
                    <span id="selectItem"></span>
                </div>
            </div>
            <div class="modal-body">
                <div id="modalLeft">
                    <c:forEach items="${user.groupSplits}" var="groupsplit">
                        <div  class="oneFriend">
                            <div class="tagName" gsId="${groupsplit.gsId}">
                                    ${groupsplit.gsname}
                                <span class="glyphicon glyphicon-menu-right labelupdown" name="right" style="float: right;color: #999999"></span>
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
                </div>
                <div id="modalRight">
                </div>
            </div>
            <div id="modalBottom" >
                <input id="groupName" type="text" placeholder="填写群名称">
                <div data-dismiss="modal" id="closeButton">关闭</div>
                <div id="nextStepButton" >确定</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


</body>
</html>
