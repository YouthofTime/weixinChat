$(function () {

    $("#input").click(function(){
        $(this).addClass("border");
    });
    // 创建WebSocket连接
    var local="ws://localhost:8888/ws/chat";
    var web="ws://101.201.124.20:8080/ws/chat";
    var sockeet=new WebSocket(web);
    // 连接
    sockeet.onopen=function () {
    }
    // 发生错误
    sockeet.onerror=function () {

    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
    //关闭WebSocket连接
    function closeWebSocket() {
        sockeet.close();
    }
    // 客户端接收浏览器的消息(用null判断空)
    sockeet.onmessage=function (result) {
        var bitcoin = eval("("+result.data+")");
        var thisId=$("span#user").attr("userid");
        var frameUid=$("span#name").attr("uid");
        var frameGcid=$("span#name").attr("gcid");// 聊天框gcid
        var headImg=$("span#user").attr("headImg");// 给头像用
        // 2.处理发送的消息
        if(thisId==bitcoin.sendUid){
            // 确保接收的gcid和uid一致才显示在右边
            if((bitcoin.receiveGcid==frameGcid&&frameGcid!=undefined)||(bitcoin.receiveUid==frameUid&&frameUid!=undefined)){
                var div=document.getElementById("chat_frmae");
                // 自己的消息显示在右边
                var onemsg=$("<div class=\"right\">\n" +
                    "\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                    "\t\t\t<img class=\"headphoto1\" src=../img/headphoto/"+headImg+">\n" +
                    "\t\t</div>");
                var clear=$("<p class=\"clearp\"></p>")
                // 设置宽度不就行了,不用拆分消息内容
                var frame=$("#chat_frmae");
                frame.append(onemsg);
                frame.append(clear);
                AjustWidth();
                div.scrollTop=div.scrollHeight;
            }
        }
         // 3.好友消息
        // 第一层
        else if(thisId==bitcoin.receiveUid){
            // 1.聊天框的消息
            if(frameUid==bitcoin.sendUid){
                var div=document.getElementById("chat_frmae");
                var bname=bitcoin.name;
                var friendImg=bitcoin.headImg;
                //没消息时不接收
                if(bname=="undefined")
                    return;
                var onemsg=$("<div class=\"one_msg\">\n" +
                    "\t\t\t<img class=\"headphoto\" src=../img/headphoto/"+friendImg+">\n" +
                    "\t\t\t<div class=\"msg\">\n" +
                    "\t\t\t\t<span class=\"user_name\">"+bname+"</span><br>\n" +
                    "\t\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>");
                $("#chat_frmae").append(onemsg);
                // 调整消息宽度
                AjustWidth();
                // 新消息过来时,聊天框滚动到新消息这
                div.scrollTop=div.scrollHeight;
            }
        }
         // 群聊消息
        // 有可能都是undefined(好友发消息的时候)
        else if(bitcoin.receiveGcid==frameGcid&&frameGcid!=undefined){
            if(thisId==bitcoin.sendUid)
                return; // 不再发送了(发送过了)
            // 显示在左边(别个的消息)
            else{
                // 1.聊天框的消息
                var div=document.getElementById("chat_frmae");
                var bname=bitcoin.name;
                var friendImg=bitcoin.headImg;
                //没消息时不接收
                if(bname=="undefined")
                    return;
                var onemsg=$("<div class=\"one_msg\">\n" +
                    "\t\t\t<img class=\"headphoto\" src=../img/headphoto/"+friendImg+">\n" +
                    "\t\t\t<div class=\"msg\">\n" +
                    "\t\t\t\t<span class=\"user_name\">"+bname+"</span><br>\n" +
                    "\t\t\t\t<span class=\"chat_msg\">"+bitcoin.value+"</span>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>");
                $("#chat_frmae").append(onemsg);
                AjustWidth();
                div.scrollTop=div.scrollHeight;
            }
        }
        // 2.最后一条消息的显示
        $(".oneChat").each(function () {
            function sameLast(data) {
                // 1.播放提示音
                $('#chatAudio')[0].play();
                // 2.最后一条消息更新
                var lastMsgSpan=data.find(".lastMsg");
                lastMsgSpan.text(bitcoin.name+":"+bitcoin.value);
                // 3.时间更新
                var lastTimeSpan=data.find(".lastTime");
                var now=new Date();
                var time=now.getHours();
                var minutes=now.getMinutes();
                if(time<10)
                    time="0"+time;
                if(minutes<10)
                    minutes="0"+minutes;
                lastTimeSpan.text(time+":"+minutes);
                cutlength("lastMsg");

            }
            // 群聊消息
            var gcid=$(this).attr("gcid");
            var uid=$("#user").attr("userid");
            if(gcid!=undefined&&gcid==bitcoin.receiveGcid&&bitcoin.sendUid!=uid) {
                // 1.未读消息的数量+1(当前选中聊天框uid和接收uid不相等情况下)
                if (frameGcid != gcid) {
                    var unreadSpan = $(this).find(".unreadCount");
                    unreadSpan.show();
                    var number = Number(unreadSpan.text()) + 1;
                    unreadSpan.text(number);
                }
                sameLast($(this));
                return false;
            }
            // 好友消息
            var uid=$(this).attr("uid");
            if(uid!=undefined&&uid==bitcoin.sendUid&&thisId==bitcoin.receiveUid) {
                // 1.未读消息的数量+1(当前选中聊天框uid和接收uid不相等情况下)
                if (frameUid != uid) {
                    var unreadSpan = $(this).find(".unreadCount");
                    unreadSpan.show();
                    var number = Number(unreadSpan.text()) + 1;
                    unreadSpan.text(number);
                }
                sameLast($(this));
                return false;
            }

        });

    }
                /**事件函数*/
     /*1.发消息按钮*/
    $("#send").click(function () {
        save();
    });
    /*2.回车键发消息*/
    $(document).keydown(function(e){
        if(e.keyCode == 13) {
            save();
        }
    });


    /**公共函数*/
    /*1.保存消息*/
    function save(){
        if($("#input").val()!=""){
            // 不要获取文本,有换行的
            var name=$("span#user").attr("username");
            var value=$("#input").val();

            // 消息数据的获取
            var receiveUid=$("#name").attr("uid");
            var receiveGcid=$("#name").attr("gcid");
            var sendUid=$("#user").attr("userid");
            var headImg=$("#user").attr("headImg");
            var time=new Date();
            // 保存到数据库中的
            var data={"time":time,"value":value,"sendUid":sendUid,"receiveGcid":receiveGcid};
            var singleData={"time":time,"value":value,"sendUid":sendUid,"receiveUid":receiveUid};
            // 发送消息(后悔用名字命名图片了)
            var groupmsg={"time":time,"name":name,"value":value,"sendUid":sendUid,"receiveGcid":receiveGcid,"headImg":headImg};
            var singlemsg={"time":time,"name":name,"value":value,"sendUid":sendUid,"receiveUid":receiveUid,"headImg":headImg};
            // 发送请求,将群聊消息保存到数据库中
            if(receiveGcid!=undefined){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    contentType:"application/json;charset=UTF-8",
                    data:JSON.stringify(data),
                    url:"/msg/saveGroup",
                    success:function () {
                        sockeet.send(JSON.stringify(groupmsg));
                    }
                });
            }
            // 发送请求,将个人消息保存到数据库中
            else if(receiveUid!=undefined){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    contentType:"application/json;charset=UTF-8",
                    data:JSON.stringify(singleData),
                    url:"/msg/saveSingle",
                    success:function () {
                        sockeet.send(JSON.stringify(singlemsg));
                    }
                });
            }
        }
        // 清空输入域
        $("#input").val("");
    }

    /*
    2.调整消息宽度(接收的数据转换成字符串的方式最好是String())
    */
    function AjustWidth() {
        var frame=$("#chat_frmae");
        var chat_msg=document.getElementsByClassName("chat_msg");
        var frmaeWidth=frame.css("width");
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
    /*3.显示时间*/
    // 先调用一次
    showTime();
    // 每5分钟显示一次时间
    setInterval(showTime,5*60*1000);
    function showTime(){
        var date=new Date();
        var hours=date.getHours();
        var minutes=date.getMinutes();
        var time=$("<div class=\"time\">"+hours+":"+minutes+"</div>");
        $("#chat_frmae").append(time);
    }
    /*4.声音播放函数*/
    // $("#jplayer").jPlayer({
    //     swfPath: "http://www.jplayer.org/latest/js/Jplayer.swf",
    //     ready: function () {
    //         $(this).jPlayer("setMedia", {
    //             mp3: "http://demo.lixiphp.com/jplayer_auto_play/resources/message.mp3"
    //         });
    //     },
    //     supplied: "mp3"
    // });
    /*5.声音播放函数*/
    /*声音播放标签添加*/
    function AudioPlayer() {
        $('<audio id="chatAudio"><source src="../audio/notify.ogg" type="audio/ogg">' +
            '<source src="../audio/notify.mp3" type="audio/mpeg">' +
            '<source src="../audio/notify.wav" type="audio/wav">' +
            '</audio>').appendTo('body');
    }
    AudioPlayer();
    /* 6.删减群聊,最后一条消息长度(一个汉字大概相当于俩个数字,英文的长度)*/
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
})
