// 定时
var count=5;
// 全局,计时器函数
var clock;
// 发送验证码的时间
var sendTime;
// 验证码
var code;
$(function (){
    $("body").mousemove(function () {
        var value=$("#password").val();
        if(value=="")
        {
            $("#password").css("font-size","14px");
        }
    });
    // 1.输入框竖线
    $("input").click(function () {
        this.style.caretColor="black";/*添加一闪一霎那的竖线*/
    });
    // 2.密码输入框字体大小(要移动一下才会触发,用键盘事件)
    $("#password").keydown(function () {
        var value=$("#password").val();
        var src=$("#eye").attr("src");// 满足是不可见

        if(value!=""&&src=="img/eye.png")
        {
            // 扩大字体大小
            this.style.fontSize="26px";
            // 不加的话一开时不点击eye,就会造成图片是不可见,而密码是可见
            this.type="password";
        }
        else {
            // 不加的话一开时先点击一下eye,就会造成图片是可见,而密码是不可见
            this.type="text";
            this.style.fontSize="14px";
        }
    });
    // 3.键盘弹起
    $("#password").keyup(function () {
        var value=$("#password").val();
        if(value=="")
        {
            $("#password").css("font-size","14px");
        }
    });
    // 4.密码显示图片点击
    $("img#eye").click(function () {
        // 如果密码输入框颜色为红,此事件失效(会发生冲突,把提示信息变成密码了)
        var color=$("#password").css("color");
        // 少了空格,我笑了
        if(color=="rgb(255, 0, 0)") {
            return;
        }
        var value=$("#password").val();

        var eye=$("img#eye");
        if(eye.attr("src")=="img/eye.png")
            eye.attr("src","img/eyeline.png");
        else
            eye.attr("src","img/eye.png");
        var src=eye.attr("src");
        // 不加的话,当点击eye的时候如果没有进入输入框就不会触发
        // 俩个if语句判断都是value!=""的时候才切换
        if(value!=""&&src=="img/eyeline.png")
        {

            $("#password").attr("type","text");
            $("#password").css("font-size","14px");
        }
        else if(value!=""&&src=="img/eye.png"){
            $("#password").attr("type","password");
            $("#password").css("font-size","26px");
        }
    });

    // 5.密码和免密码相互切换
    $("#selectNotPw").click(function () {
        orginal();
        $("#selectNotPw").addClass("weight");
        $("#selectPw").removeClass("weight");
        $("#notPw").css("display","");
        $("#Pw").css("display","none");
        $(".input1").css("display","");
        // 手机号输入框显示
        $("#togglePhone").click();
    });
    // 6.选中输入密码切换点击
    $("#selectPw").click(function () {
        orginal();
        $("#selectPw").addClass("weight");
        $("#selectNotPw").removeClass("weight");
        $("#notPw").css("display","none");
        $("#Pw").css("display","");
       $("#toggleEmail").click();
    });
    // 7.手机号码登录
    $("#togglePhone").click(function () {
        orginal();
        $(".input1").css("display","");
        $("#input3").css("display","none");
        $("#toggleEmail").css("display","");
        $("#togglePhone").css("display","none");
    });
    // 8.邮箱登录
    $("#toggleEmail").click(function () {
        orginal();
        $(".input1").css("display","none");
        $("#input3").css("display","");
        $("#toggleEmail").css("display","none");
        $("#togglePhone").css("display","");
    });

    $("#waitMsgCode").hide();// 隐藏时间显示
    // 9.点击获取短信验证码提示一个随机6未数字，隐藏超链接
    // 定时更新值
    function showAuto(){
        // 到0秒退出函数，更新超链接文本
        if(count<=0){
            $("#value").text(60);
            $("#getMsgCode").show();
            $("#waitMsgCode").hide();
            $("#getMsgCode").text("重新获取短信验证码");
            window.clearInterval(clock);// 不然计时会出现加速的情况
            return;
        }
        // 计时和发送时间一样,弹出六位随机数字
        if(count==sendTime){
            var codes=new Array(6);
            code=new String();
            for(var i=0;i<codes.length;i++){
                codes[i]=Math.round(Math.random()*9);
                code+=codes[i];
            }
            $("#inputMsgCode").focus();
            $("#inputMsgCode").val(code);
        }
        $("#value").text(--count);
    }
    // 10.获取验证码
    $("#getMsgCode").click(function(){
        //orginal();
        var val=$(".phoneNumber").val();
        var color=$(".phoneNumber").css("color");
        // 加颜色判断是因为当第一次获取验证码,手机号为空,给手机输入框加了提示信息了

        if(val==""||color=="rgb(255, 0, 0)"){
            // 失去焦点,显示红色
            $(".phoneNumber").first().blur();
            return;
        }
        var phoneinput=$(".phoneNumber");
        var phone=phoneinput.val();
        var reg=/^1[0-9]{10}$/;
        if(!reg.test(phone)){
            phoneinput.val("手机号输入格式有误");
            phoneinput.css("color","red");
            return;
        }
        $("#getMsgCode").hide();
        $("#waitMsgCode").show();
        count=5;
        sendTime= Math.round(Math.random()*4);
        clock=setInterval(showAuto, 1000);
    });

    // 11.免密码登录
    $("#registerLoginButton").click(function () {
        // 1.为空判断,先让输入框失去焦点
        var inputphone=$(".phoneNumber").first();
        var inputmsg=$("#inputMsgCode");
        inputphone.blur();
        inputmsg.blur();
        var red="rgb(255, 0, 0)";
        // 遍历寻找有无红色输入框
        if(inputphone.css("color")==red||inputmsg.css("color")==red)
            return;
        console.log("第一步成功");
        // 2.手机号码正则表达式判断
        var phoneinput=$(".phoneNumber");
        var phone=phoneinput.val();
        var reg=/^1[0-9]{10}$/;
        if(!reg.test(phone)){
            phoneinput.val("手机号输入格式有误");
            phoneinput.css("color","red");
            return;
        }
        console.log("第二步成功");
        var msgcodeinput=$("#inputMsgCode");
        var value=msgcodeinput.val();
        // 3.验证码匹配判断
        if(code!=value)
        {
            msgcodeinput.val("验证码填写错误");
            msgcodeinput.css("color","red");
            return;
        }
        console.log("第三步成功");
        var user={"phone":phone};
        $.ajax({
            type:"post",
            contentType:"application/json;charset=UTF-8",
            url:"/user/register",
            data:JSON.stringify(user),
            dataType:"json",
            success:function (state) {
                /// 登录成功
                if(state.judge)
                {
                    alert(state.hashmap.success);
                    window.location.href="/weixin/main";

                }
                // 没提供get方法,怪不得之前有异常说找不到err
                else {
                    alert(state.hashmap.err);
                }

            }
        })
    });

    // 12.快速登录(方便测试)
    $("#quickButton").click(function () {
        var data={"phone":"15684961565","password":"123456"};
        $.ajax({
           type:"post",
           data:JSON.stringify(data),
           dataType:"json",
           url:"/user/quicklogin",
           contentType:"application/json;charset=UTF-8",
           success:function (result) {
               if(result.judge==true)
                window.location.href="/weixin/main";
            }
        });
    })
    // 13.密码登录
    $("#loginButton").click(function () {

        // 1.为空判断,先让输入框失去焦点
       var inputphone=$(".phoneNumber").last();
       var pw=$("#password");
       var inputemail=$("#inputEmail");

        // 创建jquery对象数组
        var inputs=new Array(inputphone,pw,inputemail);
        // 失去焦点
        for(var i=0;i<inputs.length;i++){
            // 隐藏了的就不要了
            if(inputs[i].parent().css("display")!="none")
                inputs[i].blur();
        }

        // 遍历寻找有无红色输入框
        for(var i=0;i<inputs.length;i++)
        {
            // 跳过隐藏的input()
            if(inputs[i].parent().css("display")=="none")
            {
                continue;
            }
            var lcolor=inputs[i].css("color");
            if(lcolor=="rgb(255, 0, 0)"){
                return;
            }
        }
        console.log("第一步成功");


        // 2.密码格式判断
        var password=$("#password").val();
        var reg=/^[a-z0-9_-]{6,16}$/;
        if(!reg.test(password)){
            $("#password").attr("type","text");
            $("#password").val("密码为6-16位的数字字符");
            $("#password").css("font-size","14px");
            $("#password").css("color","red");
            return;
        }
        console.log("第二步成功");
        // 3.邮箱格式判断
        var email=$("#inputEmail").val();
        var reg=/^([a-z0-9A-Z_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        var phoneinput=$(".phoneNumber").last();
        var phone=phoneinput.val();
        var reg1=/^1[0-9]{10}$/;
        // 此时是邮箱框在输入
        if($("#inputEmail").parent().css("display")!="none"){
            // 加空判断是手机框和邮箱框发生切换
            if(!reg.test(email)){
                $("#inputEmail").val("邮箱格式有误");
                $("#inputEmail").css("color","red");
                return;
            }
            var data={"bind_email":email,"password":password};
            $.ajax({
               type:"post",
               contentType: "application/json;charset=UTF-8",
                url:"/user/login",
                data:JSON.stringify(data),
                dataType: "json",
                success:function (state) {
                   // 匹配失败
                    if(state.judge==false){
                        // 邮箱尚未注册
                        if(state.hashmap.emailerr!=null) {
                            $("#inputEmail").val(state.hashmap.emailerr);
                            $("#inputEmail").css("color","red");
                        }
                        // 密码错误
                        else if(state.hashmap.passerr!=null){
                            $("#password").val(state.hashmap.passerr);
                            $("#password").attr("type","text");
                            $("#password").css("color","red");
                            $("#password").css("font-size","14px");
                        }
                    }
                    else {

                        window.location.href="/weixin/main";
                    }
                }

            });
                console.log("e第三步成功");
        }
        // 3.手机号码判断(class的第二个)
        // 此时是手机号框在输入
        if(phoneinput.parent().css("display")!="none") {
            if(!reg1.test(phone)){
                phoneinput.val("手机号输入格式有误");
                phoneinput.css("color","red");
                return;
            }
            var data={"phone":phone,"password":password};
            $.ajax({
                type:"post",
                contentType:"application/json;charset=UTF-8",
                url:"/user/login",
                data:JSON.stringify(data),
                dataType:"json",
                success:function (state) {
                    // 匹配失败
                    if(state.judge==false){
                        // 手机号尚未注册
                        if(state.hashmap.phoneerr!=null) {
                            $(".phoneNumber").last().val(state.hashmap.phoneerr);
                            $(".phoneNumber").last().css("color","red");
                        }
                        // 密码错误
                        else if(state.hashmap.passerr!=null){
                            $("#password").val(state.hashmap.passerr);
                            $("#password").attr("type","text");
                            $("#password").css("color","red");
                            $("#password").css("font-size","14px");
                        }
                    }
                    else{
                        window.location.href="/weixin/main";
                    }

                }

            });
            console.log("p第三步成功");
        }

    });
    // 14.输入框失去焦点
    $("input").blur(function () {
        var type=this.type;
        var val=this.value;
        // 失去焦点文本为空,且不为密码框,字体变红
        if(val==""&&type!="password"){
            this.style.color="red";
            // war输入框代表的是手机号,邮箱,验证码之类的
            this.value=$(this).attr("warn")+"不能为空";
        }
        // 密码框
        if(val==""&&type=="password"){
            this.type="text";
            this.style.color="red";
            // war输入框代表的是手机号,邮箱,验证码之类的
            this.value=$(this).attr("warn")+"不能为空";
        }
    });
    // 15.输入框获得焦点
    $("input").focus(function () {
        var type=this.type;
        var color=this.style.color;

        // 获得焦点如果颜色为红色,清空输入框u,还原字体(但是验证码那里我服了)
        if(color=="red"&&type!="password"){
            var msg=$("#inputMsgCode").val();
            // 正则表达式
            var reg=/\d/;
            // 验证码需要另行判断,因为会自动填充验证码
            if(this.id=="inputMsgCode"){
                if(reg.test(msg)){

                }
                else{
                    this.value="";
                }
            }
            else{
                this.value="";
            }
            this.style.color="";

        }
        // 密码框得到焦点没必要再判断了,有键盘按下函数(而且此时密码框类型为text,一旦键盘按下就为password)
    });
});
// 不加的话每次切换还会保留红色(玄学)
function orginal() {
    var inputs=document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++){
        var type=inputs[i].type;
        var color=inputs[i].style.color;
        var value=inputs[i].value;
        if(color=="red"&&type!="password")
        {
            inputs[i].style.color = "";
            inputs[i].value = "";
        }
    }
}
/*2.判断当前登录session是否为存在*/
function skipMain() {
    $.post(
        "/user/skipMain",
        function (result) {
            if(result.judge==true){
                window.location.href="/weixin/main";
            }
        }
    );
}

skipMain();

