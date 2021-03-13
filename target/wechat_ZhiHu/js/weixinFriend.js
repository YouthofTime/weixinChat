$(function () {
    // 建立WebSocket连接
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
        var data= eval("("+result.data+")");
        // 获取data的sendUid和receiveId判断是好友申请还是群聊申请
        var sendUid=data.sendUid;
        var receiveUid=data.receiveId;

        if(!isNaN(sendUid)&&!isNaN(receiveUid)) {  // 好友申请
            createFriApply(data);
            agreeButtoni+=1;
        }
        else if(isNaN(sendUid)&&!isNaN(receiveUid)) { // 邀请入群申请
            createGroupVerify(data);
            agreeGcButtoni+=1;
        }
        else if(!isNaN(sendUid)&&isNaN(receiveUid)){// 申请加入群聊(和邀请入群基本一样,做一些修改即可)
            createGroupApply(data);
            agreeGcButtoni+=1;

        }
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
    // $(".friendImgAndName").hide();
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

    /*2.屏蔽浏览器右击事件*/
    $(".friendImgAndName").bind("contextmenu", function(){
        return false;
    });
    /*3.好友div右击函数*/
    $(".oneFriend .friendImgAndName").mousedown(function(e) {
        var parent=$(this).parent();
        if(parent.attr("id")=="newFriend")
            return;
        //右键为3
        if (3 == e.which) {
            // 获取鼠标点击的位置坐标
            var y=$(this).offset().top;
            var x=$(this).offset().left;
            $("#myModal").modal("show");
            // 设置模态窗口的位置
            $("#myModal").css("margin-top",y);
            // 设置uid
            $("#modalBottom").attr("uid",$(this).attr("uid"));
            $("#modalBottom").attr("gcId",$(this).attr("gcId"));
            $("#modalBottom").attr("gsId",$(this).parent().find(".tagName").attr("gsId"));
            /*
            1.需要修改底部框的文本
            2.需要删除uid或者gcId属性(在单击底部框根据gcId来判断到底是退出群聊还是删除好友)
             */
            if($(this).attr("gcId")!=undefined){
                $("#modalBottom").text("退出群聊");
                $("#modalBottom").removeAttr("uid");
            }
            else{
                $("#modalBottom").text("删除好友");
                $("#modalBottom").removeAttr("gcId");
            }

        }
    });
    /*分组div右击*/
    $(".friends .tagName").mousedown(function(e) {
        //右键为3
        if (3 == e.which) {
            var y=$(this).offset().top;
            var x=$(this).offset().left;
            $("#myModal").modal("show");
            // 设置模态窗口的位置
            $("#myModal").css("margin-top",y);
            // 设置uid
            $("#modalBottom").attr("gsId",$(this).attr("gsid"));
            $("#modalBottom").text("删除分组");
            $("#modalTop").text("重命名");
        }
    });
    /* 4.点击发消息跳到对应的聊天消息记录栏*/
    var sendMsg=$(".sendMsgButton");
    sendMsg.click(function(){
        // 获取对应的id
        var gcId=$("#gcname").attr("gcid");
        var uid=$("#name").attr("uid");
        $.post(
            "/weixin/selectChat?gcid="+gcId+"&&uid="+uid,
            function () {
            }
        )
        // 跳到聊天消息记录
        $("#chatImg").trigger("click");
    });
    /* 5.点击好友,群聊,新的朋友右边框显示对应的信息*/
    var friend=$(".friendImgAndName");
    friend.click(function () {
        // 1.显示隐藏
        $("#UserChatFrame").hide();

        // 2.获取对应的id
        var gsId=$(this).parent().find(".tagName").attr("gsid");
        var uid=$(this).attr("uid");
        var gcId=$(this).attr("gcid");
        var index=$(this).attr("index");
        // 1.判断是否是新的朋友
        if(index==0){
            // 和群聊用的是一个框,但只显示个名字,注意切换为群聊的时候要重新显示出来
            $("#SelectGroupChat").hide();
            $("#SelectFriendChat").hide();
            $("#SelectNewFriend").show();
            var uid=$("#user").attr("userid");
            var gcs=document.getElementById("applyUsers");
            // 删除之前显示的所有群聊用户
            while (gcs.hasChildNodes()){
                gcs.removeChild(gcs.firstChild);
            }
            // 查询出所有的好友申请
            $.post(
                "/apply/findAllApply?receiveId="+uid,
                function (result) {
                    // 当前用户拥有的分组
                    var gs=result.hashmap.gs;
                    var gssize=result.hashmap.gssize;
                    // 当前用户接受的好友请求
                    var applies=result.hashmap.applies;
                    var size=result.hashmap.size;
                    for(var i=0;i<size;i++){
                        var data=applies[i];
                        var applyImg;
                        // 获取data的sendUid和receiveId判断是好友申请还是群聊申请
                        var sendUid=data.sendUid;
                        var receiveUid=data.receiveId;
                        var oneNote;
                        var applyUsers=$("#applyUsers");
                        if(!isNaN(sendUid)&&!isNaN(receiveUid)) {  // 好友申请
                            oneNote = createFriApply(data);
                            applyUsers.append(oneNote);
                            agreeButtoni+=1;
                        }
                        else if(isNaN(sendUid)&&!isNaN(receiveUid)) { // 邀请入群申请
                            oneNote = createGroupVerify(data);
                            applyUsers.append(oneNote);
                            agreeGcButtoni+=1;
                        }
                        else if(!isNaN(sendUid)&&isNaN(receiveUid)){ // 同意入群申请
                            oneNote=createGroupApply(data);
                            applyUsers.append(oneNote);
                            agreeGcButtoni+=1;
                        }
                        if(data.isApprove==1){
                            oneNote.find(".agreeButton").hide();
                            oneNote.find(".agreeState").show();
                        }else if(data.isApprove==0){
                            oneNote.find(".agreeButton").show();
                            oneNote.find(".agreeState").hide();
                        }
                    }
                }
            );

        }
        // 2.显示好友信息框
        else if(gsId!=undefined){
            $("#SelectGroupChat").hide();
            $("#SelectNewFriend").hide();
            $("#SelectFriendChat").show();
            $(".sendMsg").show();// 发送消息按钮(被新朋友隐藏了)
            // 发送showFriend请求,获得好友信息
            $.post(
                "/friend/showFriend?gsId="+gsId+"&&uid="+uid,
                function (result) {
                    if(result.judge==true){
                        var user=result.hashmap.user;
                        // 设置对应的信息
                        $("#name").text(user.username);
                        $("#name").attr("uid",user.id);
                        $(".sex").attr("src","../img/index/leftPanle/friend/"+user.sex+"性.png");
                        if(user.sex==null||user.sex=="")
                            $(".sex").hide();
                        else
                            $(".sex").show();
                        console.log(user.headImg);
                        $("#InfoHeadImg").attr("src","../img/headphoto/"+user.headImg);
                        var remark=result.hashmap.remark;
                        if(remark==null)
                            $("#personalResume input").val("点击添加备注");
                        else
                            $("#personalResume input").val(result.hashmap.remark);
                        if(user.address==null)
                        {
                            // 隐藏,调整高度
                            $("#personalResume #address").parent().hide();
                            $("#personalResume").css("height","148px");
                        }
                        else{
                            $("#personalResume #address").parent().show();
                            $("#personalResume #address").text(user.address);
                            $("#personalResume").css("height","168px");
                        }
                        $("#personalResume #weixin").text(user.dsqId);
                        $("#personalResume #from");
                    }

                }
            );
        }
        // 3.显示群聊信息框
        else {
            $("#SelectNewFriend").hide();
            $("#SelectGroupChat").show();
            $("#SelectFriendChat").hide();
            $("#gcUsers").show();
            $(".sendMsg").show();
            $("#gcnumber").show();
            var gcs=document.getElementById("gcUsers");
            // 删除之前显示的所有群聊用户
            while (gcs.hasChildNodes()){
                gcs.removeChild(gcs.firstChild);
            }
            // 发送请求获得群聊信息
            $.post(
                "/chat/group?gcId="+gcId,
                function (result) {
                    var groupchat=result.hashmap.groupchat;
                    var users=result.hashmap.users;
                    var gsize=result.hashmap.gsize;
                    $("#gcname").text(groupchat.gcname);
                    $("#gcnumber").text("("+gsize+")");
                    $("#gcname").attr("gcid",groupchat.gcId);
                    $("#supManage").attr("href","/gc/groupManage?gcId="+gcId);
                    for(var i=0;i<gsize;i++){
                        var user=users[i];
                        // 添加该群聊的用户
                        var gcUsers=$("#gcUsers");
                        var oneuser=$("<span class=\"gcUser\">\n" +
                            "                <img src=\"../img/headphoto/"+user.headImg+"\">\n" +
                            "                <div class=\"gcUserName\">"+user.username+"</div>\n" +
                            "            </span>");
                        gcUsers.append(oneuser);
                    }
                }
            );
        }
    });
    /*6.备注输入框离开函数*/
    $("#remark").mouseleave(function () {
        var uid=$("#name").attr("uid");
        var remark=$(this).val();
        // 获得该用户所在的分组,选中框
        $(".friendImgAndName").each(function () {
            var select=$(this).attr("select");
            if(select=="select"){
                // 找到该用户所在的分组
                var tagName=$(this).parent().find(".tagName");
                var gsId=tagName.attr("gsId");
                $.post(
                    "/friend/updateFriend?remark="+remark+"&&gsId="+gsId+"&&uid="+uid,
                    function (result) {
                    }
                );
                return;
            }
        });
    });
    $("#inputGsName").hide();
    $("#confirmGsName").hide();
    /*7.点击新建分组*/
    var addGsplit=$("#addGsplit");
    addGsplit.click(function () {
        $("#inputGsName").show();
        $("#confirmGsName").show();
        $("#confirmGsName").click(function () {
            var gsName=$("#inputGsName").val();
            if(gsName==""){
                alert("分组名称不能为空");
                return;
            }
            $.post(
                "/gs/addSplit?gsName="+gsName,
                function (result) {
                    $("#inputGsName").hide();
                    $("#confirmGsName").hide();
                    alert("添加成功");
                    window.location.href="/weixin/selectFriend";
                }
            )
        });

    });
    /*8.重命名分组*/
    $(".inputGsName").mouseleave(function () {
        var gsName=$(this).val();
        var gsId=$(this).parent().attr("gsid");
        $.post(
            "/gs/reSplitName?gsId="+gsId+"&&gsName="+gsName,
            function (result) {

            }
        )
    });
    /*9.重命名时不展开分组*/
    $(".inputGsName").click(function () {
        $(this).parent().trigger("click");// 真牛
    })
    $(".friends .tagName").mouseenter(function () {
        $(this).css("background-color","rgb(218,217,217)");
        $(this).find(".inputGsName").css("background-color","rgb(218,217,217)");
    });
    $(".friends .tagName").mouseleave(function () {
        $(this).css("background-color","#EEEBE9");
        $(this).find(".inputGsName").css("background-color","#EEEBE9");
    });
    /*10.群聊头像点击*/
    $(".groups img").click(function () {
        $("#uploadModel").modal("show");
        /*
        和个人头像上传共用的
         */
        // 1.修改图片
        var src=$(this).attr("src");
        $("#uploadImg").attr("src",src);
        // 2.修改上传路径
        var gcId=$(this).parent().parent().attr("gcId");
        $("#uploadModel form").attr("action","/weixin/uploadGroupImg?gcId="+gcId);
    });
    /*11.添加群聊点击*/
    $("#addGroup").click(function () {
        $("#addFriendModel").modal("show");
        // 模态框设置
        $("#selectItem").text("创建群聊");
        $("#groupName").show();
    });



                                    /**公共函数**/
    /*1.获取当前登录用户的分组*/
    var gsSize=$("#user").attr("gssize");
    var gsNames=new Array(length);
    var gsIds=new Array(length);
    function loginGs() {
        // 获取当前用户的分组
        var i=0;
        $(".tagName").each(function () {
            if($(this).attr("gsId")!=undefined){
                gsIds[i]=$(this).attr("gsId");
                gsNames[i]=$(this).find(".inputGsName").val();
                i+=1;
            }
        });
    }
    loginGs();
    /*2.创建入群申请div*/
    var agreeButtoni=0; // 好友申请节点个数
    var agreeGcButtoni=0;// 群聊申请节点个数
    function createGroupApply(data) {
        // 入群申请获取群主同意
        var oneGroupVerify;
        if(isNaN(data.receiveId)){
            $.ajax({
                url:"/gc/findGroup?gcId="+data.receiveId,
                async: false,//改为同步方式
                success:function(result) {
                    var group = result.hashmap.group;
                    var belongId = group.belongId;
                    var thisId = $("#user").attr("userid");
                    if (thisId == belongId) {
                        // 申请过的不再申请
                        var applyUsers = document.getElementsByClassName("oneGroupVerify");
                        var flag = 0;
                        for (var i = 0; i < applyUsers.length; i++) {
                            var applyUser = $(applyUsers[i]);
                            $(".agreeButton").each(function () {
                                var applyUid = $(this).attr("sendUid");
                                if (applyUid == data.sendUid) {
                                    flag = 1;
                                }
                            });
                        }
                        if (flag == 1)
                            return;
                        console.log(data.headImg);
                        oneGroupVerify = $("<div class=\"oneGroupVerify\">\n" +
                            "<img class=\"left\" src=\"../img/headphoto/" + data.headImg + "\">\n" +
                            " <div id=\"gvMiddle\">\n" +
                            "            <span style='margin-left: 10px;'>" + data.textConfirm + "</span>\n" +
                            "            <div id=\"agreeGcButton" + agreeGcButtoni + "\" class=\"agreeButton\" sendUid=\"" + data.sendUid + "\"  receiveId=" + data.receiveId + ">接受</div>\n" +
                            "            <div class=\"agreeState\">已同意</div>\n" +
                            "</div>" +
                            "<div id=\"gvBottom\">申请人:" + data.username + "</div>\n" +
                            "        </div>");
                        // 添加到验证消息页面中
                        var applyUsers = $("#applyUsers");
                        applyUsers.append(oneGroupVerify);
                        // 接受按钮和已添加共用一个地方,所有这个要隐藏
                        oneGroupVerify.find(".agreeState").hide();
                        // 接受按钮点击函数
                        approveApply($("#agreeGcButton" + agreeGcButtoni));

                    }
                }
            });
            return oneGroupVerify;
        }
    }
    /*3.创建邀请入群申请div*/
    function createGroupVerify(data) {
        var thisId=$("#user").attr("userid");
        // 先判断当前在线用户是否是被申请的用户
        // 邀请好友入群
      if(thisId==data.receiveId) {
          // 申请过的不再申请
          var applyUsers=document.getElementsByClassName("oneGroupVerify");
          var flag=0;
          for(var i=0;i<applyUsers.length;i++){
              var applyUser=$(applyUsers[i]);
              $(".agreeButton").each(function () {
                  var applyUid=$(this).attr("applyUid");
                  if(applyUid==data.sendUid){
                      flag=1;
                  }
              });
          }
          if(flag==1)
              return;
          var oneGroupVerify = $("<div class=\"oneGroupVerify\">\n" +
              "<img class=\"left\" src=\"../img/headphoto/" + data.headImg + "\">\n" +
              " <div id=\"gvMiddle\">\n" +
              "            <span style='margin-left: 10px;'>" + data.textConfirm + "</span>\n" +
              "            <div id=\"agreeGcButton"+agreeGcButtoni+"\" class=\"agreeButton\" applyUid=" + data.sendUid + ">接受</div>\n" +
              "            <div class=\"agreeState\">已同意</div>\n" +
              "</div>" +
              "<div id=\"gvBottom\">邀请人:" + data.username + "</div>\n" +
              "        </div>");
          // 添加到验证消息页面中
          var applyUsers = $("#applyUsers");
          applyUsers.append(oneGroupVerify);
          // 接受按钮和已添加共用一个地方,所有这个要隐藏
          oneGroupVerify.find(".agreeState").hide();
          // 接受按钮点击函数
          approveApply($("#agreeGcButton"+agreeGcButtoni));
          return oneGroupVerify;
        }
    }
     /*4.创建好友申请div*/
    function createFriApply(data) {
        var thisId=$("#user").attr("userid");
        // 先判断当前在线用户是否是被申请的用户
        if(thisId==data.receiveId){
            //遍历原有的申请用户(申请过的不在增加)
            var applyUsers=document.getElementsByClassName("oneNewFriend");
            var flag=0;
            for(var i=0;i<applyUsers.length;i++){
                var applyUser=$(applyUsers[i]);
                $(".agreeButton").each(function () {
                    var applyUid=$(this).attr("applyUid");
                    if(applyUid==data.sendUid){
                        applyUser.find("#applyIntroduce").text(data.textConfirm);
                        flag=1;
                    }
                });
            }
            if(flag==1)
                return;
            var oneNewFriend=$("<div class=\"oneNewFriend\">\n" +
                "            <img class=\"left\" src=\"../img/headphoto/"+data.headImg+"\">\n" +
                "            <div class=\"left\" id=\"applyModel\">\n" +
                "                <div id=\"applyName\" >"+data.username+"</div>\n" +
                "                <div id=\"applyIntroduce\">"+data.textConfirm+"</div>\n" +
                "            </div>\n" +
                " <span id=\"groupSplits\">\n" +
                "                        <span>加入分组:</span>\n" +
                "                        <select class=\"groupSplit\">\n" +
                "                        </select>\n"+
                "                    </span>"+
                "            <div id=\"agreeButton"+agreeButtoni+"\" class=\"agreeButton\" applyUid="+data.sendUid+">接受</div>\n" +
                "            <div class=\"agreeState\">已添加</div>\n" +
                "        </div>");
            // 添加到验证消息页面中
            var applyUsers=$("#applyUsers");
            applyUsers.append(oneNewFriend);
            // 接受按钮和已添加共用一个地方,所有这个要隐藏
            oneNewFriend.find(".agreeState").hide();
            // 接受按钮点击函数
            approveApply($("#agreeButton"+agreeButtoni));
            // 设置获取当前用户拥有的分组
            var groupSplit=oneNewFriend.find(".groupSplit");
            for(var i=0;i<gsSize;i++){
                var option=$("<option gsId="+gsIds[i]+">"+gsNames[i]+"</option>");
                groupSplit.append(option);
            }
            return oneNewFriend;
        }
    }
    /*5.接受申请函数*/
    function approveApply(data) {
        var sendUid=data.attr("applyUid");
        var receiveId=$("#user").attr("userid");
        // 判断是入群申请
        if(data.attr("sendUid")!=undefined&&receiveId!=undefined){
            sendUid=data.attr("sendUid");
            receiveId=data.attr("receiveId");
        }
        // 1.接受好友请求
        if(!isNaN(sendUid)&&!isNaN(receiveId)){
            data.click(function () {
                $.post(
                    "/apply/approveApply?sendUid="+sendUid+"&&receiveId="+receiveId,
                    function (result) {
                        data.hide();
                        data.siblings().show();
                        var gsId=data.parent().find(".groupSplit option:selected").attr("gsId");
                        $.post(
                            "/apply/beApprovedApply?uid="+sendUid+"&&gsId="+gsId,
                            function (result) {
                                // 刷新页面
                                $("#friendImg").trigger("click");
                            });
                    }
                );
            });
        }
        // 2.接受群聊邀请请求
        else if(isNaN(sendUid)&&!isNaN(receiveId)){
            data.click(function () {
                alert("加群成功");
                $.post(
                "/apply/joinGroupApply?sendUid=" + sendUid + "&&receiveId=" + receiveId,
                    function (result) {
                        data.hide();
                        data.siblings().show();
                        // 刷新页面
                        $("#friendImg").trigger("click");
                    });
            });
        }
        // 3.同意入群申请
        else if(!isNaN(sendUid)&&isNaN(receiveId)){
            data.click(function () {
                alert("入群成功");
                $.post(
                    "/apply/joinGroupApprove?sendUid=" + sendUid + "&&receiveId=" + receiveId,
                    function (result) {
                        data.hide();
                        data.siblings().show();
                        // 刷新页面
                        $("#friendImg").trigger("click");
                    });
            });
        }
    }
    /*页面大小调整*/
    function adjustPage() {
        // 1.主页的好友,群聊选择框
        var WMHeight=String($("#UserSelectFriend").css("height"));
        WMHeight=WMHeight.substring(0,WMHeight.length-2);
        WMHeight=Number(WMHeight)-65;
        $("#selectFriend").css("height",WMHeight+"px");
        // 2.主页的大小
        // 主页的宽度
        var indexWidth=String($("#index").css("width"));
        indexWidth=indexWidth.substring(0,indexWidth.length-2);
        indexWidth=Number(indexWidth)-318;
        // 主页的高度
        var indexHeight=String($("#index").css("height"));
        indexHeight=indexHeight.substring(0,indexHeight.length-2);

        // 3.初始化微信界面宽度(只有一张微信图片
        $("#UserChatFrame").css("width",indexWidth+"px");
        // 4.验证消息宽度
        $("#SelectNewFriend").css("width",indexWidth+"px");
        // 5.群聊信息宽度
        $("#SelectGroupChat").css("width",indexWidth+"px");
        // 6.群聊信息图片高度
        $("#gcUsers").css("height",(indexHeight-205)+"px");
        // 7.好友信息宽度
        $("#SelectFriendChat").css("width",indexWidth+"px");
        // 8.好友信息边距
        $("#nameAndImg").css("margin-left",(indexWidth-400)/2+"px");
        $("#personalResume").css("margin-left",(indexWidth-400)/2+"px");
        // 9.搜索框搜索出的内容div高度调整
        $("#temporaryDiv").css("height",WMHeight);
    }

    setInterval(adjustPage,10);
})
