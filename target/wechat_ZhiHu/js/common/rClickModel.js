$(function () {
    $("body").bind("contextmenu", function(){
        return false;
    });
                /**事件函数*/
    /*1.body点击隐藏模态框*/
    $("body").click(function () {
        $("#myModal").modal("hide");
    })
    /*2.单击删除好友,退出群聊*/
    $("#modalBottom").click(function () {
        var value=$(this).text();
        var uid=$(this).attr("uid");
        var gsId=$(this).attr("gsId");
        var gcId=$(this).attr("gcId");
        // 1.删除好友
        if(value=="删除好友"){
            $.post(
                "/friend/deleteFriend?uid="+uid+"&&gsId="+gsId,
                function (result) {
                    $("#myModal").modal("hide");
                    // 刷新页面
                    window.location.href="/weixin/selectFriend";
                }
            );
        }
        // 2.当前登录用户退出群聊
        else if(value=="退出群聊"){
            var loginId=$("#user").attr("userid");
            // 判断该用户是否是群主
            $.post(
                "/gc/isGroupOwner?gcId="+gcId,
                function (result) {
                    // 1.不是群主
                    if(result.judge==false){
                        $.post(
                            "/gc/delGcUser?gcId="+gcId+"&&uid="+loginId,
                            function (result) {
                                // 刷新页面
                                window.location.href="/weixin/selectFriend";
                            }
                        )
                    }
                    // 2.是群主
                    else{
                        var flag=confirm("你是该群的群主,你确定要解散群聊吗?");
                        if(flag){
                            $.post(
                                "/gc/delGroup?gcId="+gcId,
                                function (result) {
                                    alert("解散群聊成功");
                                    // 刷新页面
                                    window.location.href="/weixin/selectFriend";
                                }
                            )
                        }
                    }

                }
            )

        }
        // 3.删除分组
        else if(value=="删除分组"){
            console.log("删除分组");
            $.post(
                "/gs/delSplit?gsId="+gsId,
                function (result) {
                    var judge=result.judge;
                    if(judge==false)
                        alert("该分组下还有好友,请先删除好友");
                    else{
                        window.location.href="/weixin/selectFriend";
                        alert("删除成功");
                    }

                }
            )
        }

    });
    /*3.单击发消息,添加分组*/
    $("#modalTop").click(function () {
            var value=$(this).text();
            // 获取对应的id
            var gcId=$("#modalBottom").attr("gcid");
            var uid=$("#modalBottom").attr("uid");
            var gsId=$("#modalBottom").attr("gsId");
            console.log(value)
            if(value=="发送消息")
            {
                console.log("???");
                $.post(
                    "/weixin/selectChat?gcid="+gcId+"&&uid="+uid,
                    function () {
                        // 跳到聊天消息记录
                        $("#chatImg").trigger("click");
                    }
                );

            }
    });




});