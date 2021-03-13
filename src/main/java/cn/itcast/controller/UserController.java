package cn.itcast.controller;

import cn.itcast.domain.*;
import cn.itcast.service.*;

import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/user")
@SessionAttributes({"user"})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    GroupChatService gcService;
    @Autowired
    GroupSplitService gsService;

    /**
     * 判断当前用户是否已经登录,直接跳转主页
     * @param model
     * @return
     */
    @RequestMapping("/skipMain")
    public @ResponseBody StateJudge skipMain(HttpServletRequest request,ModelMap model){
        User user=null;
        Cookie[] cookies  = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("id")){
                int id=Integer.parseInt(cookie.getValue());
                user=userService.findByid(id);
                // 添加session
                model.addAttribute("user",user);
            }
        }
        if(user!=null)
            return StateJudge.success();
        else
            return StateJudge.error();
    }
    /**
     * 1.@ModelAttribute:补全密码
     * 2.@RequestBody:处理json数据,转换成User对象(注意前端数据对不全不会报错)
     * 3.架包忘记导入了
     * 注册,登录
     * @return
     */

    @RequestMapping("/register")
    public @ResponseBody StateJudge register(@RequestBody User user, ModelMap model,HttpServletResponse response){
        // 1.register如果用户不存在,就保存到数据库中并设置默认密码
        boolean flag=false;
        if(userService.findByPhone(user.getPhone())==null) {
            user.setPassword("123456");
            user.setUsername("知乎用户");
            user.setBind_email("");
            user.setSex("");
            user.setAddress("");
            String headImg="";
            int i= (int)(Math.random()*4+1);
            headImg="user/头像"+i+".jpg";
            user.setHeadImg(headImg);
            user.setDsqId(user.getPhone());
            userService.saveUser(user);
            user=userService.findByPhone(user.getPhone());
            // 创建一个群聊
            GroupChat gc=new GroupChat();
            int j= (int)(Math.random()*3+1);
            String headImg1="/group/头像"+j+".jpg";
            gc.setHeadImg(headImg1);
            gc.setGcname("工具群");
            gc.setGcId("gc"+user.getId()+"_1");
            gc.setBuildtime(new Date());
            gc.setBelongId(user.getId());
            gcService.saveGroupChat(gc);
            // 创建一个分组
            GroupSplit gs=new GroupSplit();
            gs.setGsId("gs"+user.getId()+"_1");
            gs.setGsname("我的好友");
            gs.setBelongId(user.getId());
            gsService.saveGroupSplit(gs);
            flag=true;

            // 添加session
            model.addAttribute("user",user);

        }
        // 登录传来的只有电话号码,而这个数据又要传到微信页面
        user=userService.findByPhone(user.getPhone());
        // 添加cookie
        Cookie c = new Cookie("id", user.getId()+"");
        c.setMaxAge(60 * 24 * 60);
        c.setPath("/");
        response.addCookie(c);

        // 2.判断是新注册用户还是已经注册了的用户
        // (位置啊,反了就是空指针异常了)
        try {
            if (flag==true)
                return StateJudge.success().setHashMap("success", "注册成功,默认密码为123456");
                // 2.已经注册过用户
            else
                return StateJudge.success().setHashMap("success", "登录成功");
        }
        catch (Exception e)
        {
            return StateJudge.error().setHashMap("err","发生异常,请联系管理员");
        }
    }

    /**
     * 处理邮箱登录和手机号登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public @ResponseBody StateJudge login(@RequestBody User user,ModelMap model,HttpServletResponse response){
        // 1.邮箱登录(即此时user的手机号码肯定为空,万一手机号码登录也是查询为空,提示消息就错了)
        if(user.getPhone()==null||"".equals(user.getPhone())) {
            User user1=userService.findByBindEmail(user.getBind_email());
            if (user1 == null) {
                return StateJudge.error().setHashMap("emailerr", "该邮箱尚未注册");
            } else if (!user.getPassword().equals(user1.getPassword())) {
                return StateJudge.error().setHashMap("passerr", "密码错误");
            }
            // 添加User session
            model.addAttribute("user",user1);
        }
        // 2.手机号登录(即此时user的绑定邮箱肯定为空)
        else {
            User user2 = userService.findByPhone(user.getPhone());
            if (user2 == null) {
                return StateJudge.error().setHashMap("phoneerr", "手机号码尚未注册");
            } else if (!user.getPassword().equals(user2.getPassword())) {
                return StateJudge.error().setHashMap("passerr", "密码错误");
            }
            // 添加session
            model.addAttribute("user",user2);

        }
        user=(User)model.get("user");
        Cookie c = new Cookie("id", user.getId()+"");
        c.setMaxAge(60 * 24 * 60);
        c.setPath("/");
        response.addCookie(c);

        return StateJudge.success();
    }
    /*快速登录*/
    @RequestMapping("/quicklogin")
    public @ResponseBody StateJudge quicklogin(@RequestBody User user, HttpServletResponse response, ModelMap model){


        // 添加session
        if(user!=null) {
            user=userService.findByPhone(user.getPhone());
            Cookie c = new Cookie("id", user.getId()+"");
            c.setMaxAge(60 * 24 * 60);
            c.setPath("/");
            response.addCookie(c);
            model.addAttribute("user", user);
            return StateJudge.success();
        }
        else return StateJudge.error();
    }
    /*退出登录*/
    @RequestMapping("/backLogin")
    public @ResponseBody StateJudge backLogin(SessionStatus status,HttpServletRequest request,HttpServletResponse response){
        status.setComplete();
        Cookie[]cookies=request.getCookies();
        for(Cookie cookie : cookies){
            //如果找到同名cookie，就将value设置为null，将存活时间设置为0，再替换掉原cookie，这样就相当于删除了。
            if(cookie.getName().equals("id")){
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        return StateJudge.success();
   }
    /**
     * 根据输入的昵称或者dsqId查找用户
     * @param dsqIdName
     * @return
     */
    @RequestMapping("/findUsers")
    public @ResponseBody StateJudge findUsers(String dsqIdName){

        String IdName=dsqIdName;
        User user=userService.findByDsqId(dsqIdName);
        List<User> users=userService.findByName(dsqIdName);
        if(user!=null){
            return StateJudge.success().setHashMap("user",user);
        }
        else if (users.size()!=0){
            return StateJudge.success().setHashMap("users",users).setHashMap("size",users.size());
        }
        else
            return StateJudge.error();
    }

    /**
     * 根据id查找用户,用户的分组
     * @param uid
     * @param modelMap
     * @return
     */
    @RequestMapping("/findUser")
    public @ResponseBody StateJudge findByUid(String uid, ModelMap modelMap){
        User user=userService.findByid(Integer.parseInt(uid));
        // 登录用户拥有的分组
        User loginUser= (User) modelMap.get("user");
        loginUser=userService.findByid(loginUser.getId());
        List<User> users=userService.findAll();
        return StateJudge.success().setHashMap("user",user).setHashMap("size",loginUser.getGroupSplits().size()).setHashMap("gs",loginUser.getGroupSplits()).setHashMap("users",users);
    }

    /**
     * 更改User的信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public @ResponseBody StateJudge updateUser(@RequestBody User user){
        userService.updateUser(user);
        return StateJudge.success();
    }
}
