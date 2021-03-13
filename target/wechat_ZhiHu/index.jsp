<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/6
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>知乎-有问题，上知乎</title>
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/index.css"/>
    <script src="../js/index.js"></script>
</head>
<body>
<span id="user" uid="${user.id}"></span>
<div id="top">
    <img src="../../img/知乎.png">
</div><!--top-->
<div id="loginDiv">
    <!--选择登陆方式-->
    <div id="selectType">
        <span id="selectNotPw" class="weight">免密码登陆</span>
        <span id="selectPw" >密码登陆</span>
        <img src="../../img/twocode.png">
    </div>
        <!--免密登陆(和密码登录只能显示一个)-->
        <div id="notPw" >
            <!--手机号码-->
            <div class="input1">
                            <span class="country">中国 +86
                                <span class="updown"><></span>
                            </span>
                <input type="text" class="phoneNumber" placeholder="手机号" warn="手机号" name="phone_email">
            </div>
            <!--短信验证码-->
            <div id="input2">
                <input type="text" id="inputMsgCode" placeholder="输入6位短信验证码" warn="短信验证码">
                <a href="#"  id="getMsgCode" >获取短信验证码</a>
                <span id="waitMsgCode" style="float:right"><span id="value">60</span>秒后可重发</span>
            </div>
            <!--验证码-->
            <div class="code" style="display: none;">
                <input class="inputCode" placeholder="验证码">
                <img src="">
            </div>
            <div class="speakCode">
                接收语音验证码
            </div>
            <div>
                <button id="registerLoginButton" >注册/登录</button>
               <button id="quickButton"style="margin-top: 3px">快速登录</button>
            </div>
        </div>
    <!--密码登陆-->
    <div id="Pw" style="display: none">
        <!--邮箱(和手机号只能显示一个)-->
        <div id="input3">
            <input type="text" placeholder="邮箱" id="inputEmail" warn="邮箱">
        </div>
        <!--手机号码-->
        <div class="input1" style="display: none">
						<span class="country">中国 +86
							<span class="updown"><></span>
						</span>
            <input type="text" class="phoneNumber" placeholder="手机号" warn="手机号">
        </div>
        <!--密码-->
        <div id="input4">
            <input type="password" placeholder="密码" id="password" warn="密码">
            <img id="eye"  src="../../img/eye.png" >
        </div>
        <!--验证码-->
        <div class="code" style="display: none;">
            <input class="inputCode" placeholder="验证码">
            <img src="">
        </div>
        <div class="toggle">
            <!--只显示一个-->
            <span id="toggleEmail" style="display: none">邮箱账号登录</span>
            <span  id="togglePhone">手机号登录</span>
            <span id="forgetPw">忘记密码?</span>
        </div>
        <div>
            <button id="loginButton">登录</button>
        </div>
    </div>
    <!--登录按钮后面-->
    <div id="loginBottom">

        <!--协议-->
        <div id="agreenment">
            未注册手机验证后自动登录,注册即代表同意《知乎协议》《隐私保护指引》
        </div>
        <!--社交账号登录-->
        <div id="socMedAccount">
            <span class="smaText">社交账号登录</span>
            <span class="accountRight">
						<img src="../../img/微信.png">
						<span id="wechat">微信</span>
						<img src="../../img/qq.png">
						<span id="qq">QQ</span>
						<img src="../../img/微博.png">
						<span id="weibo">微博</span>
					</span>
        </div>
        <!--下载-->
        <div id="downloadAppDiv">
					<span class="agencyAccount">
						<img src="../../img/ok.png">
						<span>开通机构号</span>
					</span>
            <span class="downloadApp">
						<img src="../../img/favicon.ico">
						<span> 下载知乎 App</span>
					</span>
        </div><!--下载-->
    </div><!--登录按钮后面-->
</div><!--loginDiv-->

<div id="bottom">
    <div class="bottom1">知乎专栏 圆桌发现 移动应用 联系我们 来知乎工作 注册机构号</div>
    <div class="bottom2">? 2020 知乎京 ICP 证 110745 号京 ICP 备 13052560 号 - 1京公网安备 11010802010035 号出版物经营许可证</div>
    <div class="bottom3"> 侵权举报网上有害信息举报专区儿童色情信息举报专区违法和不良信息举报：010-82716601</div>
</div>
</body>
</html>