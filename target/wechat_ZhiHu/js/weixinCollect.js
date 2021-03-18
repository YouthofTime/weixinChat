$(function () {
            /*事件函数*/
    /*1.新建笔记进入退出函数*/
    var newNote=$("#newNote");
    newNote.mouseover(function () {
        $(this).css("background-color","#F0F0F0");
    });
    newNote.mouseout(function () {
        $(this).css("background-color","#FFFFFF");
    });
    /*2.标签框点击事件(向下隐藏,向上显示)*/
    var label=$("#label");
    var labelSon=$(".labelSon");
    var updown=$("#labelupdown");
    labelSon.hide();
    label.click(function () {
        if(updown.attr("name")=="down"){
            updown.attr("name","up");
            labelSon.show();
            updown.attr("class","glyphicon glyphicon-menu-up");
        }
        else{
            updown.attr("name","down");
            labelSon.hide();
            updown.attr("class","glyphicon glyphicon-menu-down");
        }
    });
    /*3.页面大小调整*/
    function adjustPage() {
        // 1.主页的好友,群聊选择框
        var WMHeight=String($("#UserSelectCollect").css("height"));
        WMHeight=WMHeight.substring(0,WMHeight.length-2);
        WMHeight=Number(WMHeight)-65;
        $("#selectCollect").css("height",WMHeight+"px");
        // 2.主页的最右边聊天框,初始化微信界面
        var WRWidth=String($("#index").css("width"));
        WRWidth=WRWidth.substring(0,WRWidth.length-2);
        WRWidth=Number(WRWidth)-318;
        $("#UserChatFrame").css("width",WRWidth+"px");
        // 3.搜索框搜索出的内容div高度调整
        $("#temporaryDiv").css("height",WMHeight);
    }
    setInterval(adjustPage,100);
})