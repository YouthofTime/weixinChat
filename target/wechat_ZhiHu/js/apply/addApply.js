$(function () {
    // 1.建立webSocket连接
    var local="ws://localhost:8888/ws/chat";
    var web="ws://101.201.124.20:8080/ws/chat";
    var sockeet=new WebSocket(web);
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
    /*1.查找按钮点击*/
    var findFriend=$("#findButton");
    findFriend.click(function () {
        findFriends();
    });
    $("#findButton1").click(function () {
        findGroups();
    });
                    /**公共函数**/
    /*1.查找群聊功能*/
    function findGroups() {
        var gcIdName=$("#inputGroup").val();
        $.post(
            "/gc/findGroups?gcIdName="+gcIdName,
            function (result) {
                var data=result.hashmap;
                var gcs=document.getElementById("meetUser");
                // 删除之前显示的所有用户
                while (gcs.hasChildNodes()){
                    gcs.removeChild(gcs.firstChild);
                }
                // 显示查询出的好友
                // 1.根据dsqId查询的
                var group=data.gc;
                if(group!=undefined){
                    var oneUser=$("<div class=\"oneUser\">\n" +
                        "                <div class=\"left\">\n" +
                        "                    <img src=\"../img/haedphoto/"+group.headImg+"\">\n" +
                        "                </div>\n" +
                        "                <div class=\"UserIntro\">\n" +
                        "                    <div class=\"name\" >"+group.gcname+"</div>\n" +
                        "                    <div class=\"number\">"+
                        "                       <span class=\"glyphicon glyphicon-user\"></span>"+data.number+"/100" +
                        "                    </div>\n" +
                        "                    <div class=\"joinGroup\" gcId="+group.gcId+">+ 加群</div>\n" +
                        "                </div>\n" +
                        "            </div>");
                    var meetUsers=$("#meetUser");
                    meetUsers.append(oneUser);
                    // 最开始该方法没有获取到这些新添加的addButton,需要调用一次(使用js获取节点获得的灵感)
                    // 不然的话,就像该div的class是addButton,但click事件不会执行
                    addFri();
                    var joinButton=oneUser.find(".joinGroup");
                    joinGroup(joinButton);
                }
                // 2.根据名字查询的
                else if(data.gcs!=undefined){
                    var groups=data.gcs;
                    var size=groups.length;
                    console.log(size);
                    for(var i=0;i<size;i++)
                    {
                        var group=groups[i];
                        var oneUser=$("<div class=\"oneUser\">\n" +
                            "                <div class=\"left\">\n" +
                            "                    <img src=\"../img/headphoto/"+group.headImg+"\">\n" +
                            "                </div>\n" +
                            "                <div class=\"UserIntro\">\n" +
                            "                    <div class=\"name\" >"+group.gcname+"</div>\n" +
                            "                    <div class=\"number\">"+
                            "                       <span class=\"glyphicon glyphicon-user\"></span>"+data.numbers[i]+"/100" +
                            "                    </div>\n" +
                            "                    <div class=\"joinGroup\" gcId="+group.gcId+">+ 加群</div>\n" +
                            "                </div>\n" +
                            "            </div>");
                        var meetUsers=$("#meetUser");
                        meetUsers.append(oneUser);
                        addFri();
                        var joinButton=oneUser.find(".joinGroup");
                        joinGroup(joinButton);
                    }
                }
            }// function
        );
    }

    /*2.查找好友功能*/
    function findFriends(){
        var dsqIdName=$("#inputQQ").val();
        $.post(
            "/user/findUsers?dsqIdName="+dsqIdName,
            function (result) {
                var data=result.hashmap;

                var gcs=document.getElementById("meetUser");
                // 删除之前显示的所有用户
                while (gcs.hasChildNodes()){
                    gcs.removeChild(gcs.firstChild);
                }
                // 可以查找到数据
                if(result.judge==true){
                    // 隐藏好友推荐,显示查询出的好友
                    var meetUsers=$("#meetUser");
                    meetUsers.show();
                    $("#showUsers").hide();
                    // 1.根据dsqId查询的
                    if(data.user!=undefined){
                        var oneUser=$("<div class=\"oneUser\">\n" +
                            "                <div class=\"left\">\n" +
                            "                    <img src=\"../img/headphoto/"+data.user.headImg+"\">\n" +
                            "                </div>\n" +
                            "                <div class=\"UserIntro\">\n" +
                            "                    <div class=\"name\" >"+data.user.username+"</div>\n" +
                            "                    <div class=\"age\">"+data.user.sex+"</div>\n" +
                            "                    <div class=\"addButton\" uid="+data.user.id+">+好友</div>\n" +
                            "                </div>\n" +
                            "            </div>");
                        meetUsers.append(oneUser);
                        // 最开始该方法没有获取到这些新添加的addButton,需要调用一次(使用js获取节点获得的灵感)
                        // 不然的话,就像该div的class是addButton,但click事件不会执行
                        addFri();
                    }
                    // 2.根据名字查询的
                    else if(data.users!=undefined){
                        var users=data.users;
                        var size=data.size;
                        for(var i=0;i<size;i++)
                        {
                            var user=users[i];
                            var oneUser=$("<div class=\"oneUser\">\n" +
                                "                <div class=\"left\">\n" +
                                "                    <img src=\"../img/headphoto/"+user.headImg+"\">\n" +
                                "                </div>\n" +
                                "                <div class=\"UserIntro\">\n" +
                                "                    <div class=\"name\">"+user.username+"</div>\n" +
                                "                    <div class=\"age\">"+user.sex+"</div>\n" +
                                "                    <div class=\"addButton\"  uid="+user.id+">+好友</div>\n" +
                                "                </div>\n" +
                                "            </div>");
                            meetUsers.append(oneUser);
                            addFri();
                        }
                    }
                }
            }
        );
    }
    /* 3.点击添加好友功能*/
    function addFri() {
        var addFriend=$(".addButton");
        addFriend.click(function () {
            $("#myModal").modal("show");
            /*一.三个div共用一个位置,
            * 1.第一个是填写验证信息
            * 2.第二个是设置分组,备注
            * 3.第三个是完成显示界面
            * */
            $("#modalRight1").show();
            // 下一步按钮,关闭按钮
            $("#closeButton").text("关闭");
            $("#closeButton").css("border-color","black");
            $("#nextStepButton").show();
            $("#modalRight2").hide();
            $("#modalRight3").hide();
            $("#confirmGsName").hide();
            $("#inputGsName").hide();
            // 二.设置模态窗口的数据
            var uid=$(this).attr("uid");
            $.post(
                "/user/findUser?uid="+uid,
                function (result) {
                    var user=result.hashmap.user;
                    // 模态窗口左边
                    var img=$("#modalLeft").find("img");
                    img.attr("src","../img/headphoto/"+user.headImg);
                    $("#beApplyName").text(user.username);
                    $("#beApplyName").attr("rid",user.id);
                    $("#beApplyDsqId").text(user.dsqId);
                    $("#beApplySex").text("性别:"+user.sex);
                    $("#beApplyAge").text("年龄:22岁");
                    $("#beApplyAddress").text("所在地:"+user.address);
                    // 遍历分组
                    var size=result.hashmap.size;
                    var groupSplits=result.hashmap.gs;
                    // 删除之前显示的所有分组
                    var gs=document.getElementById("groupSplit");
                    while(gs.hasChildNodes()){
                        gs.removeChild(gs.firstChild);
                    }
                    for(var i=0;i<size;i++){
                        var gsName=groupSplits[i].gsname;
                        var option=$("<option gsId="+groupSplits[i].gsId+">"+gsName+"</option>");
                        $("#groupSplit").append(option);
                    }
                }
            );
        });
    }
    /*4.加入群聊功能*/
    function joinGroup(data) {
        data.click(function () {
            var groupName=$(this).parent().find(".name").text();
            var gcId=$(this).attr("gcId");
            var sendUid=$("#addFriend").attr("uid");
            var username=$("#addFriend").attr("username");
            var headImg=$("#addFriend").attr("headImg");
            console.log(headImg)
            var applyUser={"username":username,"textConfirm":"申请加入群聊:"+groupName,"sendUid":sendUid,"receiveId":gcId,"headImg":headImg};
            var apply={"username":username,"textConfirm":"申请加入群聊:"+groupName,"sendUid":sendUid,"receiveId":gcId,"remark":"","gsId":"","isApprove":0,"headImg":headImg};
            $.ajax({
                type:"post",
                dataType:"json",
                contentType:"application/json;charset=UTF-8",
                data:JSON.stringify(apply),
                url:"/apply/addApply",
                success:function (result) {
                    var judge=result.judge;
                    if(judge==false){
                        alert("您已加入该群聊,无需重复添加");
                    }
                    else {
                        sockeet.send(JSON.stringify(applyUser));
                        alert("已发送加入群聊申请,请等待群主同意");
                    }
                }
            });
        });
    }
    addFri();

    /***addModel的js部分*/
    /**事件函数*/
    /* 1.点击下一步按钮*/
    var nextButton=$("#nextStepButton");
    nextButton.click(function () {
        // 1.在右边框1中点击下一步按钮
        var modalR1=$("#modalRight1");
        var modalR2=$("#modalRight2");
        var username=$("#applyName").text();
        var textConfirm=$("#textConfirm").val();
        var sendUid=$("#applyName").attr("uid");
        var receiveId=$("#beApplyName").attr("rid");
        var headImg=$("#addFriend").attr("headImg");
        var remark=$("#remark").val();


        if(modalR1.css("display")!="none")
        {
            $("#modalRight1").hide();
            $("#modalRight2").show();
        }
        // 2.在右边框2中点击下一步按钮(第二个下一步)
        else if(modalR2.css("display")!="none"){
            // 先创建分组,才能发送添加好友请求
            var groupSplit=document.getElementById("groupSplit");
            if(!groupSplit.hasChildNodes()){
                alert("请先新建一个分组,再选择下一步操作!");
                return;
            }
            // 保存到数据库并发送消息
            var gsId=$("#groupSplit option:selected").attr("gsId");
            var applyUser={"username":username,"textConfirm":textConfirm,"sendUid":sendUid,"receiveId":receiveId,"gsId":gsId,"headImg":headImg};
            var apply={"username":username,"textConfirm":textConfirm,"sendUid":sendUid,"receiveId":receiveId,"remark":remark,"gsId":gsId,"isApprove":0,"headImg":headImg};
            if(sendUid==receiveId){
                alert("你不能添加自己为好友");
                return;
            }
            $.ajax({
                type:"post",
                dataType:"json",
                contentType:"application/json;charset=UTF-8",
                data:JSON.stringify(apply),
                url:"/apply/addApply",
                success:function (result) {
                    var judge=result.judge;
                    if(judge==false){
                        alert("你已经添加该用户为好友!");
                    }
                    else {
                        $("#modalRight1").hide();
                        $("#modalRight2").hide();
                        $("#modalRight3").show();
                        // 隐藏下一步按钮,关闭改成完成
                        $("#closeButton").text("完成");
                        $("#closeButton").css("border-color","rgb(79,173,216)");
                        nextButton.hide();
                        sockeet.send(JSON.stringify(applyUser));
                    }

                }
            });
        }
    });
    $("#inputGsName").hide();
    $("#confirmGsName").hide();
    /*2.点击新建分组*/
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
                    $("#myModal").modal("hide");
                }
            )
        });

    });




})