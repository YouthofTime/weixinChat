$(function () {
    // 1.建立webSocket连接
    var local="ws://localhost:8888/ws/chat";
    var web="ws://101.201.124.20:8080/ws/chat";
    var sockeet=new WebSocket(local);
// 连接
    sockeet.onopen=function () {
    }
// 发生错误
    sockeet.onerror=function () {

    }
// 接收服务器的消息
    sockeet.onmessage=function (result) {
    }
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
//关闭WebSocket连接
    function closeWebSocket() {
        sockeet.close();
    }
                /**事件函数*/
/* 1.标签框点击事件(向下隐藏,向上显示)*/
    var tagName=$(".tagName");
   $(".friendImgAndName").hide();
    tagName.click(function () {
        var updown=$(this).find(".labelupdown");
        var labelSon=$(this).siblings();
        if(updown.attr("name")=="down"){
            updown.attr("name","right");
            labelSon.hide();
            updown.attr("class","glyphicon glyphicon-menu-right labelupdown");
        }
        else{
            updown.attr("name","down");
            labelSon.show();
            updown.attr("class","glyphicon glyphicon-menu-down labelupdown");
        }
    });
    /* 2.左边点击一个好友右边显示出来*/
    $(".friendImgAndName").each(function () {
        $(this).attr("count",0);// 计数
    });
    $(".friendImgAndName").click(function () {
        // 先判断该用户是否在
        var uid=$(this).attr("uid");
        var gcId=$("#gcId").text();
        var beAddUser=$(this);
        $.ajax({
            url:"/gc/isInGroup?gcId="+gcId + "&&uid=" + uid,
            async:false,
            success:function(result) {
                var judge = result.judge;
                if (judge == false)
                    alert("该用户已经在该群聊中,请勿重复添加!")
                else {
                    var username = beAddUser.find(".friendName").text();
                    var headImg=beAddUser.find("img").attr("src");
                    // 采用备忘录(思想)避免重复添加
                    var count = Number(beAddUser.attr("count")) + 1;
                    beAddUser.attr("count", count);
                    if (count <= 1) {
                        var oneUser = $("<div class=\"friendImgAndName1\" uid=" + uid + ">\n" +
                            "                                        <span class=\"friendPhoto\">\n" +
                            "                                            <img src=" +headImg+ ">\n" +
                            "                                        </span>\n" +
                            "                                    <span class=\"friendName\" >" + username + "</span>\n" +
                            "                                </div>");
                        oneUser.find(".friendImgAndName1").show();
                        $("#modalRight").append(oneUser);
                    }
                }
            }
        });
    });
    /* 3.确定按钮点击*/
    $("#nextStepButton").click(function () {

        // 根据模态窗口名字判断执行操作
        var item=$("#selectItem").text();
        if(item=="添加成员"){
            var sendUid=$("#gcId").text();
            var groupName=$("#gsName").text();
            var selectUsers=$("#modalRight").find(".friendImgAndName1");
            var username=$("#gc").attr("username");
            var headImg=$("#gc").attr("headImg");
            selectUsers.each(function () {
                var receiveUid=$(this).attr("uid");
                var applyUser={"username":username,"textConfirm":"邀请你加入群聊:"+groupName,"sendUid":sendUid,"receiveId":receiveUid,"groupName":groupName,"headImg":headImg};
                var apply={"username":username,"textConfirm":"邀请你加入群聊:"+groupName,"sendUid":sendUid,"receiveId":receiveUid,"remark":"","gsId":"","isApprove":0,"headImg":headImg};
                $.ajax({
                    type:"post",
                    dataType:"json",
                    contentType:"application/json;charset=UTF-8",
                    data:JSON.stringify(apply),
                    url:"/apply/addApply",
                    success:function () {
                        sockeet.send(JSON.stringify(applyUser));
                    }
                });
            });
            alert("邀请成功");
            $("#myModal").modal("hide");// 隐藏模态框
        }
        else if(item=="创建群聊"){
           var belongId=$("#gc").attr("uid");
           if(belongId==undefined)
               belongId=$("#user").attr("userid");
           var gcname=$("#groupName").val();
           if(gcname=="")
           {
               alert("请输入群聊的名字");
               return;
           }
           // 1.创建这个群
            $.post(
                "/gc/addGroup?belongId="+belongId+"&&gcname="+gcname,
                function (result) {
                    if(result.judge==true){
                        var sendUid=result.hashmap.gc.gcId;
                        var groupName=gcname;
                        var selectUsers=$("#modalRight").find(".friendImgAndName1");
                        var username=$("#gc").attr("username");
                        var headImg=result.hashmap.gc.headImg;
                        selectUsers.each(function () {
                            var receiveUid=$(this).attr("uid");
                            var applyUser={"username":username,"textConfirm":"邀请你加入群聊:"+groupName,"sendUid":sendUid,"receiveId":receiveUid,"groupName":groupName,"headImg":headImg};
                            var apply={"username":username,"textConfirm":"邀请你加入群聊:"+groupName,"sendUid":sendUid,"receiveId":receiveUid,"remark":"","gsId":"","isApprove":0,"headImg":headImg};
                            $.ajax({
                                type:"post",
                                dataType:"json",
                                contentType:"application/json;charset=UTF-8",
                                data:JSON.stringify(apply),
                                url:"/apply/addApply",
                                success:function () {
                                    sockeet.send(JSON.stringify(applyUser));
                                }
                            });
                        });
                    }
                        alert("创建成功");
                }
            );
            $("#myModal").modal("hide");// 隐藏模态框
        }
    });
    /*4.关闭按钮点击*/
    $("#closeButton").click(function () {
        $("#myModal").modal("hide");
        $(".friendImgAndName1").hide();
        $(".friendImgAndName").attr("count",0);
    })
})
