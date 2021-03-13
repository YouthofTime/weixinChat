$(function () {
            /**事件函数*/
    /*1.body点击*/
    $("body").click(function () {

    })
    /*2.图标切换(聊天,好友,收藏)点击*/
    var userhandle=document.getElementById("UserHandle");
    var imgs=userhandle.getElementsByTagName("img");
    // 1.遍历图片1-3点击事件
    for(var i=1;i<4;i++){
        var img=$(imgs[i]);
        img.click(function () {
            // 中间框跟着切换
            if($(this).attr("name")=="chat"){
                // 输入框重置
                $("#cleanEmpty").click();
                window.location.href="/weixin/main";

            }
            else if($(this).attr("name")=="friend"){
                $("#cleanEmpty").click();
                window.location.href="/weixin/selectFriend";
            }
            else if($(this).attr("name")=="collect"){
                $("#cleanEmpty").click();
                window.location.href="/weixin/selectCollect";
            }
        });
    }
    /* 3.搜索框点击事件*/
    var inputFind=$("#find");
    var clean=$("#cleanEmpty");
    clean.hide();
    inputFind.click(function () {
        // 1.搜索框的样式
        $("#searchLeft").css("background-color","#ffffff");
        $(this).css("background-color","#ffffff");
        $(this).attr("placeholder","");
        clean.show();
        // 2.3步看起来就像是清空搜索框下方的内容
        // 2.搜索框下方div的隐藏
        $(this).parents().find("#search").siblings().hide();
        // 3.创建一个空div和被隐藏的大小一样
        var oneDiv=$("<div id='temporaryDiv'></div>");
        $(this).parents().find("#search").parent().append(oneDiv);
        // 4.发送请求查询出符合条件的好友(键盘弹起)
        $("#find").keyup(function () {
            $.ajax({
                url:"/weixin/searchObject?val="+$(this).val(),
                success:function(result) {
                    // 删除之前的节点
                    var temporaryDiv = document.getElementById("temporaryDiv");
                    while (temporaryDiv.hasChildNodes()) {
                        temporaryDiv.removeChild(temporaryDiv.firstChild);
                    }
                    if(result.judge==false)
                        return;
                    var friends = result.hashmap.users;
                    var remarks = result.hashmap.remarks;
                    // 添加节点
                    for (var i = 0; i < friends.length; i++) {
                        var user = friends[i];
                        var remark = remarks[i];
                        var oneNote = $("<div class=\"oneChat\"  uid=" + user.id + ">\n" +
                            "                    <span class=\"chatPhoto\">\n" +
                            "                         <img src=\"../img/headphoto/" + user.headImg + "\">\n" +
                            "                    </span>\n" +
                            "                    <span>\n" +
                            "                        <div class=\"nameAndTime\" >\n" +
                            "                            <span class=\"chatName\">" + user.username + "</span>\n" +
                            "                            <span class=\"remarkName\">(" + remark + ")</span>\n" +
                            "                        </div>\n" +
                            "                         <div class=\"dsqId\">dsq:" + user.dsqId + "</div>\n" +
                            "                    </span>\n" +
                            "                </div>");
                        if(remark=="")
                            oneNote.find(".remarkName").hide();
                        $("#temporaryDiv").append(oneNote);
                        // 1.背景颜色切换
                        traverse(chats);
                        // 2.点击函数
                        reverseChat();
                    }
                }
            });
        })
    });
    /*4.搜索框中的X点击*/
    clean.click(function () {
        remove($(this));

    });
    /*5.搜索框左边,右边大div点击*/
    $("#UserSelectChat").siblings().click(function () {
        remove($(this));
    });
    // 6.跳转到添加好友页面
    var addFriend=$("#plus");
    addFriend.click(function () {
        window.open("../apply/addApply.jsp");
    });
    /*7.头像点击*/
    $("#headPhoto").click(function () {
        $("#headModel").modal("show");
    });
    /*8.退出登录点击*/
    $("#backLogin").click(function () {
        $.post(
            "/user/backLogin",
            function (result) {
                window.location.href="/index.jsp";
            }
        )
    });
    /*9.设置图片点击*/
    $("#setImg").click(function () {
        $("#headModel").modal("show");
    });
    /*10.编辑资料点击*/
    $("#editInfo").click(function () {
        $("#headModel").modal("hide");
        $("#edi_Personal_Model").modal("show");
    });

    /*提交个人信息*/
    $("#submitInfo").click(function () {
        var EditInfo=$("#edi_Personal_Model");
        var username=EditInfo.find("#username").val();
        var phone=EditInfo.find("#phone").val();
        // 1.手机号格式判断
        var reg=/^1[0-9]{10}$/;
        if(!reg.test(phone)){
            alert("手机号输入格式有误");
            return;
        }
        var password=EditInfo.find("#password").val();
        var id=$("#user").attr("userid");
        $.post(
            "/user/findUser?uid="+id,
            function (result) {
                var user=result.hashmap.user;
                if(password!=user.password)
                    alert("密码错误!");
                else{
                    var newPw=EditInfo.find("#newPassword").val();
                    var confrimPassword=EditInfo.find("#confrimPassword").val();
                    // 2.密码格式判断

                    var regPw=/^[a-z0-9_-]{6,16}$/;
                    if(!regPw.test(newPw)||!regPw.test(confrimPassword)){
                       alert("新密码格式应为6-16位的数字字符");
                        return;
                    }
                    if(newPw!=confrimPassword)
                    {
                        alert("前后密码不一致!");
                        return;
                    }
                    // 3.邮箱格式判断
                    var bind_email=EditInfo.find("#bind_email").val();
                    var regEm=/^([a-z0-9A-Z_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
                    if(!regEm.test(bind_email)){
                        alert("邮箱格式有误");
                        return;
                    }
                    var address=EditInfo.find("#address").val();
                    var sex=EditInfo.find("#sex").val();
                    var dsqId=EditInfo.find("#dsqId").val();
                    // 4.重复判断
                    var users=result.hashmap.users;
                    for(var i=0;i<users.length;i++){
                        var oneUser=users[i];
                        if(oneUser.id==id)
                            continue;
                        if(phone==oneUser.phone){
                            alert("该手机号已经被注册!");
                            return;
                        }
                        if(bind_email==oneUser.bind_email){
                            alert("该邮箱已经被绑定");
                            return;
                        }
                        if(dsqId==oneUser.dsqId){
                            alert("该DsqId已经有人使用");
                            return;
                        }
                    }
                    var user={"id":id,"phone":phone,"password":newPw,"username":username,"bind_email":bind_email,"address":address,"sex":sex,"dsqId":dsqId,"headImg":user.headImg};
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        contentType:"application/json;charset=UTF-8",
                        data:JSON.stringify(user),
                        url:"/user/updateUser",
                        success:function (result) {
                            alert("更改成功,登录失效,请重新登录!");
                            $("#backLogin").trigger("click");
                        }
                    });
                }
            }
        );

    });

                /**公共函数*/
    /* 1.选中框(好友,聊天,收藏页面)的背景颜色更换*/
    var chats=document.getElementsByClassName("oneChat");
    var friends=document.getElementsByClassName("friendImgAndName");
    var collects=document.getElementsByClassName("oneCollect");
    // 1.切换选中框背景
    function toggleData(i,data){
        var bgColor;
        for(var j=0;j<data.length;j++){
            // 换背景
            if(j==i){
                bgColor="#C8C6C6";
                $(data[i]).css("background-color",bgColor);
                $(data[i]).attr("select","select");
            }
            else {
                // 聊天信息的第一个初始颜色不一样,其他都一样
                if($(data[j]).attr("name")=="top")
                    bgColor="#E1DFDE";
                else
                    bgColor="#ECEAE8";
                $(data[j]).css("background-color",bgColor);
                // 不能省略(不然选中一次就一直代表选中了)
                $(data[j]).attr("select","no");
            }
        }
    }
    /* 2.遍历所有选中框(好友,聊天,收藏页面)进入退出函数*/
    function traverse(data) {
        // 遍历选中框
        for(var i=0;i<data.length;i++){
            var chat=$(data[i]);
            chat.attr("index",i);
            chat.click(function () {
                toggleData($(this).attr("index"),data);
            });
        }
        // 鼠标进入
        $(data).mouseover(function () {
            // 已经选中的直接退出
            if($(this).attr("select")=="select")
                return;
            $(this).css("background-color","#DAD9D9");
        });
        // 鼠标退出
        $(data).mouseout(function () {
            if($(this).attr("select")=="select")
                return;
            if($(this).attr("name")=="top")
                $(this).css("background-color","#E1DFDE");
            else
                $(this).css("background-color","#ECEAE8");
        });
    }
    traverse(chats);
    traverse(friends);
    traverse(collects);
    /*3.搜索框样式更换函数*/
    function remove(data){
        $("#searchLeft").css("background-color","#DAD8D8");
        $(inputFind).css("background-color","#DAD8D8");
        $(inputFind).attr("placeholder","搜索");
        $(inputFind).val("");
        clean.hide();
        data.parents().find("#search").siblings().show();
        $("#temporaryDiv").remove();
    }


    /*聊天框*/
    var chat_group=$("#chat_group");
    var UserChatFrame=$("#UserChatFrame");
    chat_group.hide();
    /*4调整聊天消息的宽度*/
    function AjustWidth() {
        var frame=$("#chat_frmae");
        var chat_msg=document.getElementsByClassName("chat_msg");
        var frmaeWidth=frame.css("width");
        if(frmaeWidth==undefined)
            return;
        frmaeWidth=frmaeWidth.substring(0,frmaeWidth.length-2);
        // 消息宽度超过聊天框一半限制消息宽度为一半
        for(var i=0;i<chat_msg.length;i++) {
            var singlemsg = $(chat_msg[i]);
            var msgWidth = singlemsg.css("width");
            /* 去掉px*/
            // 获取倍数
            msgWidth = msgWidth.substring(0, msgWidth.length - 2);
            if (msgWidth > frmaeWidth / 2)
                msgWidth = frmaeWidth / 2;
            // 加上px
            msgWidth += "px";
            singlemsg.css("width", msgWidth);
        }
    }
    /*5.遍历聊天消息添加进聊天框中*/
    function showMesgs(msgs) {
        // 清空之前的
        var chat_frame=$("#chat_frmae");
        chat_frame.text("");
        var thisId=$("span#user").attr("userid");// 登录用户id
        var frameName=$("span#name").text();     // 聊天框名字
        var frameUid=$("span#name").attr("uid"); // 聊天框uid
        var frameGcid=$("span#name").attr("gcid");// 聊天框gcid
        var headImg=$("span#user").attr("headImg");// 给头像用
        for(var i=0;i<msgs.length;i++){
            var bitcoin = msgs[i]; // 一条聊天记录
            var sendUser=bitcoin.sendUser; // 发送消息的用户
            // 1.登录用户发送的消息
            if(thisId==bitcoin.sendUid){
                var div=document.getElementById("chat_frmae");
                // 自己的消息显示在右边
                var onemsg=$("<div class=\"right\">\n" +
                    "\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                    "\t\t\t<img class=\"headphoto1\" src=../img/headphoto/"+headImg+">\n" +
                    "\t\t</div>");
                var clear=$("<p class=\"clearp\"></p>")
                var frame=$("#chat_frmae");
                frame.append(onemsg);
                frame.append(clear);
                AjustWidth();
                div.scrollTop=div.scrollHeight;
            }
            // 2.其他用户接收的消息
            // 好友的消息显示在左边
            // 第一层(和聊天的接收好友id相匹配)
            else if(thisId==bitcoin.receiveUid){
                // 第二层(和聊天框uid匹配)
                if(frameUid==bitcoin.sendUid){
                    var div=document.getElementById("chat_frmae");
                    var bname=frameName;
                    var length=Number(bname.length);
                    var username=bname.substring(0,length);
                    var friendImg=sendUser.headImg;
                    var onemsg=$("<div class=\"one_msg\">\n" +
                        "\t\t\t<img class=\"headphoto\" src=../img/headphoto/"+friendImg+">\n" +
                        "\t\t\t<div class=\"msg\">\n" +
                        "\t\t\t\t<span class=\"user_name\">"+username+"</span><br>\n" +
                        "\t\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                        "\t\t\t</div>\n" +
                        "\t\t</div>");
                    $("#chat_frmae").append(onemsg);
                    AjustWidth();
                    div.scrollTop=div.scrollHeight;
                }
            }
            // 群聊的消息(全都显示在左边,发送的那个人的聊天记录已经显示在右边了的)
            else if(bitcoin.receiveGcid==frameGcid){
                var div=document.getElementById("chat_frmae");
                var bname=sendUser.username;
                var length=Number(bname.length);
                var username=bname.substring(0,length);
                var friendImg=sendUser.headImg;
                var onemsg=$("<div class=\"one_msg\">\n" +
                    "\t\t\t<img class=\"headphoto\" src=../img/headphoto/"+friendImg+">\n" +
                    "\t\t\t<div class=\"msg\">\n" +
                    "\t\t\t\t<span class=\"user_name\">"+username+"</span><br>\n" +
                    "\t\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>");
                $("#chat_frmae").append(onemsg);
                AjustWidth();
                div.scrollTop=div.scrollHeight;
            }
        }
    }
    /*6聊天页面遍历好友,群聊选择框,右边框显示出对应的聊天框*/
    var chats=document.getElementsByClassName("oneChat");
    function reverseChat() {
        for(var i=0;i<chats.length;i++){
            var chat=$(chats[i]);
            chat.click(function () {
                // 1.样式修改
                // 消息显示数量
                var unreadSpan=$(this).find(".unreadCount");
                unreadSpan.text(0);
                unreadSpan.hide();
                // 搜索结果去掉
                remove($("#cleanEmpty"));
                $("#selectChat").show();
                UserChatFrame.hide();
                chat_group.show();
                // 2.发送请求分俩种(一是群聊id,一是好友id)
                var gcId=$(this).attr("gcid");
                var uid=$(this).attr("uid");
                if(gcId!=undefined){
                    $.post(
                        "/chat/group?gcId="+gcId,
                        function (data) {
                            // 获得后端传过来的数据
                            var size="("+data.hashmap.gsize+")";
                            var groupchat=data.hashmap.groupchat;
                            var name=groupchat.gcname;
                            // 设置群聊名字和人数
                            var chatname=$("#name");
                            var number=$("#number");
                            chatname.attr("gcid",groupchat.gcId);// 转换成群聊(删除uid)
                            chatname.removeAttr("uid");
                            chatname.text(name);
                            number.text(size);
                            // 设置选中id
                            $("#user").attr("selectId",gcId);
                            // 清空右边聊天框内容,添加该群聊的聊天记录
                            var msgs=groupchat.groupMsgs;
                            showMesgs(msgs);
                        }
                    );
                }
                else if(uid!=undefined){
                    $.post(
                        "/chat/friend?uid="+uid,
                        function (data) {
                            // 获得后端传过来的数据
                            var user=data.hashmap.user;
                            var name=user.username;
                            // 设置好友名字
                            var chatname=$("#name");
                            var number=$("#number");
                            chatname.attr("uid",user.id);// 转换成好友,删除gcid
                            chatname.removeAttr("gcid");
                            chatname.text(name);
                            number.text("");
                            // 设置选中id
                            $("#user").attr("selectId",uid);
                            // 清空右边聊天框内容,添加和好友的聊天记录
                            var msgs=data.hashmap.msgs;
                            showMesgs(msgs);
                        }
                    );
                }
            });
        }
    }
    reverseChat();
    /*7.刷新页面后选中最后一个选中的*/
    function trverseSelect() {
        var id=$("#user").attr("selectid");
        for(var i=0;i<chats.length;i++){
            var chat=$(chats[i]);
            var gcId=chat.attr("gcid");
            var uid=chat.attr("uid");
            if(gcId==id||uid==id){
                chat.trigger("click");
                break;
            }
        }
    }
    trverseSelect();
    // 调整消息长度
    AjustWidth();
    /* 8.删减群聊,最后一条消息长度(一个汉字大概相当于俩个数字,英文的长度)*/
    function cutlength(data) {
        var chatNames=document.getElementsByClassName(data);
        var size=100;
        if(data=="chatName")
            size=8;
        if(data=="lastMsg")
            size=10;
        for(var i=0;i<chatNames.length;i++){
            var chat=$(chatNames[i]);
            var name=chat.text();
            var value = name.replace(/[^0-9]/ig,"");
            var b = name.replace(/[^a-z]+/ig,"");
            var nslength=value.length+b.length;
            var length=name.length-nslength+nslength/2;
            // 数字和字符长度转换后超过8(2个数字相当于一个中文)
            if(length>size){
                var reg=/[\u4e00-\u9fa5]/; // 匹配是中文的
                var k=0;
                var newName="";
                for(j in name){
                    if(k>=size-1)
                        break;
                    if(reg.test(name[j]))
                        k+=1;
                    else
                        k+=0.5;
                    newName+=name[j];
                }
                chat.text(newName+"...");
                newName=""; // 重置
            }
        }
    }
    // 调用
    cutlength("chatName");
    cutlength("lastMsg");

    /*9.设置未读消息的数目*/
    function unReadCount() {
        $.ajax({
            url: "/msg/unRead",
            async: false,//改为同步方式(可以解决刷新显示未读消息初始化的问题)
            success:function(result) {
                var unread = result.hashmap.unread;
                var loginId = $("#user").attr("userid");
                for (var key in unread) {
                    var value = unread[key];
                    var oneChats = $(".oneChat");
                    oneChats.each(function () {
                        var uid = $(this).attr("uid");
                        var gcid = $(this).attr("gcId");
                        var chatRoom;
                        if (uid != undefined && gcid == undefined)
                            chatRoom = loginId + "-" + uid;
                        else if (gcid != undefined && uid == undefined)
                            chatRoom = gcid + "-" + loginId;
                        var unReadSpan = $(this).find(".unreadCount");
                        if (unread[chatRoom] == 0)
                            unReadSpan.hide();
                        else{
                            unReadSpan.text(unread[chatRoom]);
                        }
                        console.log(unread[chatRoom]);

                    })
                }
            }
        });
    }
    unReadCount();
    /*初始化隐藏未读为0的消息*/
    function unReadInit() {
        var i=0;
        $(".unreadCount").each(function () {
            if($(this).text()!=0){
                $(this).show();
                console.log(i);
                i+=1;
            }

        });
    }
    unReadInit();
    /*11.页面大小调整*/
    function adjustPage() {
        // 1.主页的好友,群聊选择框
        var WMHeight=String($("#UserSelectChat").css("height"));
        WMHeight=WMHeight.substring(0,WMHeight.length-2);
        WMHeight=Number(WMHeight)-65;
        $("#selectChat").css("height",WMHeight+"px");
        // 2.主页的最右边聊天框,初始化微信界面
        var WRWidth=String($("#index").css("width"));
        WRWidth=WRWidth.substring(0,WRWidth.length-2);
        WRWidth=Number(WRWidth)-318;
        $("#UserChatFrame").css("width",WRWidth+"px");
        // 3.聊天室大小调整
        // 宽度
        var CTWidth=String($("#index").css("width"));
        CTWidth=CTWidth.substring(0,CTWidth.length-2);
        CTWidth=Number(CTWidth)-318;
        $("#chat_group").css("width",CTWidth+"px");
        // 高度
        var CFHeight=String($("#index").css("height"));
        CFHeight=CFHeight.substring(0,CFHeight.length-2);
        CFHeight=Number(CFHeight)-199;
        $("#chat_frmae").css("height",CFHeight+"px");
        // 5.搜索框搜索出的内容div高度调整
        $("#temporaryDiv").css("height",WMHeight);

    }

    setInterval(adjustPage,10)




})