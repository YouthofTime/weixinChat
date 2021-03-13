$(function () {
                    /*一.事件函数*/
    /*1.全选*/
    $("#selectAll").click(function(){
        if("false"==$(this).attr("selectit"))
        {
            //设置自己的selectit
            $(this).attr("selectit","true");
            $(this).prop("checked",true);
            //遍历所有的子选中
            $(".select").each(function(){
                $(this).attr("selectit","false");
                $(this).click();
            })
        }
        else{
            $(this).prop("checked",false);
            //1.设置selectit为false
            $(this).attr("selectit","false");
            //2.遍历所有的
            $(".select").each(function(){
                $(this).attr("selectit","true");//首先要全部设置为true
                $(this).click();
            });
            beginGray();
        }
    });
    /*2.添加成员点击*/
    $("#addGuser").click(function () {
        $("#myModal").modal("show");// 显示出模态框
        // 模态框设置
        $("#selectItem").text("添加成员");
        $("#groupName").hide();
    });
    /*3.添加群聊点击*/
    $("#addGroup").click(function () {
        $("#myModal").modal("show");
        // 模态框设置
        $("#selectItem").text("创建群聊");
        $("#groupName").show();
    });
    /*4.删除成员点击*/
    $("#delGuser").click(function () {
        if($(this).attr("click")=="click")
        {
            // 遍历所有复选框(.select)
            $(".select").each(function () {
                if($(this).attr("selectit")=="true"){
                    var gcId=$("#gcId").text();
                    var uid=$(this).attr("uid");
                    $.post(
                        "/gc/delGcUser?gcId="+gcId+"&&uid="+uid,
                        function (result) {
                            init();// 刷新页面
                        }
                    )
                }
            })
        }
    });
    /*5.解散群聊点击*/
    $("#delGroup").click(function () {
        if($(this).attr("click")=="click")
        {
            var flag=confirm("你是该群的群主,你确定要解散群聊吗?");
            if(flag){
                $.post(
                    "/gc/delGroup?gcId="+$("#gcId").text(),
                    function (result) {
                        alert("解散群聊成功");
                        // 刷新页面
                        window.location.href="/weixin/main";
                    }
                )
            }
        }
    });

                /*二.公共函数*/
    /*1. 发送请求,查询群聊,群聊用户数据*/
    function init(){
        var gus=document.getElementById("groupUsers");
        //删除之前显示的所有群聊用户
        while (gus.hasChildNodes()){
            gus.removeChild(gus.firstChild);
        }
        var gcId=$("#gc").attr("gcId");
        $.post(
            "/gc/findGroup?gcId="+gcId,
            function (result) {
                var gcUsers=result.hashmap.gcUsers;
                var group=result.hashmap.group;
                var enterTimes=result.hashmap.EnterTimes;
                var leaveTimes=result.hashmap.LeaveTimes;
                $("#gsName").text(group.gcname);
                $("#gcId").text(group.gcId);
                $("#gsNumber").text(gcUsers.length+"/200");
                var gcUsersDiv=$("#groupUsers");
                for(var i=0;i<gcUsers.length;i++){
                    var gcUser=gcUsers[i];
                    var enterTime=enterTimes[i];
                    var leaveTime=leaveTimes[i];
                    var oneUser=$("<div class=\"groupUser\">\n" +
                        "                <input class=\"select\" selectit=\"false\" type=\"checkbox\" uid="+gcUser.id+">\n" +
                        "                <span class=\"list\">"+(i+1)+"</span>\n" +
                        "                <span class=\"userImgAndName\">\n" +
                        "                    <img src=\"../img/headphoto/"+gcUser.headImg+"\">\n" +
                        "                    <span class=\"username\">"+gcUser.username+"</span>\n" +
                        "                </span>\n" +
                        "                <span class=\"inGroupName\"></span>\n" +
                        "                <span class=\"dsqId\">"+gcUser.dsqId+"</span>\n" +
                        "                <span class=\"sex\">"+gcUser.sex+"</span>\n" +
                        "                <span class=\"enterTime\">"+enterTime+"</span>\n" +
                        "                <span class=\"lastMsg\">"+leaveTime+"</span>\n" +
                        "            </div>");
                    var sex=oneUser.find(".sex");
                    if(sex.text()=="null")
                        sex.css("visibility","hidden");
                    gcUsersDiv.append(oneUser);
                    // 第一个为群主需要隐藏复选框(占用位置,hide不占用)
                    if(i==0){
                        oneUser.find("input").css("visibility","hidden");
                        oneUser.find("input").removeAttr("class");// 不然全选框不会生效
                    }
                    selectOne(oneUser);
                }
                isGroupOwner();// 是否为群主
                oneClick();// 复选框点击
            }
        );
    }
    init();
    /*2.按钮变绿*/
    function beginGreen(){
        $("#delGroup").css("background-color","#1AAD19");
        $("#delGuser").css("background-color","#1AAD19");
        $("#delGroup").attr("click","click");
        $("#delGuser").attr("click","click");
    }
    /*3.按钮变为灰色*/
    function beginGray(){
        $("#delGroup").css("background-color","#d4d7dc");
        $("#delGuser").css("background-color","#d4d7dc");
        $("#delGroup").attr("click","no");
        $("#delGuser").attr("click","no");
    }

    /*4.复选框点击*/
    function oneClick(){
        $(".select").click(function(){
            if("false"==$(this).attr("selectit")) {
                $(this).prop("checked",true);
                var selectAll = true;//如果有1个没有选中后面是会修改成false的
                //1.设置selectit
                $(this).attr("selectit", "true");
                //2.按钮颜色发生变化
                beginGreen();
                //4.遍历所有的复选框
                $(".select").each(function () {
                    if ("false" == $(this).attr("selectit"))//只要有1个为false
                        selectAll = false;
                });
                if (selectAll == true){//全部选中了
                    $("#selectAll").attr("selectit","true");
                    $("#selectAll").prop("checked",true);
                }
            }
            else{
                $("#selectAll").attr("checked",false);
                //1.将全选的设置为false(只要有1个点击了false)
                $("#selectAll").attr("selectit","false");
                $(this).prop("checked",false);
                //2.设置selectit为false
                $(this).attr("selectit","false");
                var settleBegin=false;
                //遍历所有的复选框
                $(".select").each(function(){
                    if("true"==$(this).attr("selectit"))//只要有1个为true
                        settleBegin=true;
                });
                //3.按钮变为灰色(只要有1个选中就不会变为灰色)
                if(!settleBegin)
                    beginGray();
            }
        });
    }
    oneClick();
    /*普通用户(不是群主)界面删除某些元素)*/
    function isGroupOwner() {
        var gcId=$("#gcId").text();
        $.ajax({
            url:"/gc/isGroupOwner?gcId="+gcId,
            async:false,
            success:function(result) {
                var judge = result.judge;
                if (judge == false) {
                    var select = $(".select");
                    var selectAll = $("#selectAll");
                    var delGuser = $("#delGuser");
                    var delGroup=$("#delGroup");
                    select.css("visibility", "hidden");
                    selectAll.css("visibility", "hidden");
                    delGuser.remove();
                    delGroup.remove();
                }
            }
        });
    }
    /*选中一条记录*/
    function selectOne(data){
        data.children().click(function () {
            if($(this).attr("class")=="select"){
                return;
            }
            data.find(".select").trigger("click");
        });
    }
})