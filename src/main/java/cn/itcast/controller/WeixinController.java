package cn.itcast.controller;

import cn.itcast.domain.*;
import cn.itcast.service.*;
import cn.itcast.utils.DateFormat;
import cn.itcast.utils.FileUpload;
import cn.itcast.utils.Pingyin;
import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

// 还是没有真正导入进来,在服务器中有
@Controller
@RequestMapping("/weixin")
@SessionAttributes({"user","selectId","gs","gc","friends","groupmsg","singlemsg","time"})
public class WeixinController {
    @Autowired
    UserService userService;
    @Autowired
    GroupSplitService gsService;
    @Autowired
    GroupChatService gcService;
    @Autowired
    GroupMsgService gmService;
    @Autowired
    SingleMsgService smService;
    @Autowired
    GroupSplitUserService gsuService;

    @RequestMapping("/sendFile")
    public @ResponseBody StateJudge sendFile(HttpServletRequest request, HttpServletResponse response, MultipartFile upload,ModelMap modelMap) throws IOException {
        User user=(User)modelMap.get("user");
        int id=user.getId();
        String path=request.getSession().getServletContext().getRealPath("/uploads");
        String project=path.substring(0,path.indexOf("target"))+"src\\main\\webapp\\upload\\user"+id;
        String filename=upload.getOriginalFilename();
        System.out.print(filename);
        FileUpload.upload(filename,project,upload);
        response.sendRedirect("/weixin/main");
        return StateJudge.success();
    }
    @RequestMapping("/uploadImg")
    public @ResponseBody StateJudge uploaImg(HttpServletRequest request, HttpServletResponse response, MultipartFile upload,ModelMap modelMap) throws IOException {
        // 获取文件目录
        String path=request.getSession().getServletContext().getRealPath("/uploads");
        System.out.println(path);

        /* 在webapp目录下创建该照片*/
       String project=path.substring(0,path.indexOf("target"))+"src\\main\\webapp\\img\\headphoto";
        /*以war包的形式部署路径不一样*/
      // String project1=path.substring(0,path.indexOf("upload"))+"/img/headphoto";//war部署
       // File file=new File(path); // 上传到uploads下
        // 直接获得上传文件名字
        String filename=upload.getOriginalFilename();
        String extend=filename.substring(filename.lastIndexOf("."));
        filename=FileUpload.upload(filename,project,upload);
        if(extend.equals(".jpg")||extend.equals(".png")){
            // 1.更改用户信息
            User user=(User)modelMap.get("user");
            user.setHeadImg(filename);
            userService.updateUser(user);
            response.sendRedirect("/weixin/main");
            modelMap.addAttribute("headImg",filename);
        }
        return StateJudge.success();
    }
    @RequestMapping("/uploadGroupImg")
    public @ResponseBody StateJudge uploaGroupImg(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws IOException {
        // 获取文件目录
        String path=request.getSession().getServletContext().getRealPath("/uploads");
        /* 在webapp目录下创建该照片*/
        String project=path.substring(0,path.indexOf("target"))+"src\\main\\webapp\\img\\headphoto";
        /*以war包的形式部署路径不一样*/
       // String project1=path.substring(0,path.indexOf("upload"))+"/img/headphoto";//war部署(这个有亿点点坑)
        // File file=new File(path); // 上传到uploads下
        // 直接获得上传文件名字
        String filename=upload.getOriginalFilename();
        String extend=filename.substring(filename.lastIndexOf("."));
        filename=FileUpload.upload(filename,project,upload);
        if(extend.equals(".jpg")||extend.equals(".png")){
            // 1.更改群聊头像
            String gcId=request.getParameter("gcId");
            System.out.println(gcId);
            GroupChat gc=gcService.findByGcid(gcId);
            gc.setHeadImg(filename);
            gcService.updateGroupChat(gc);
            response.sendRedirect("/weixin/selectFriend");
        }
        return StateJudge.success();
    }
    @RequestMapping("/searchObject")
    public @ResponseBody StateJudge searchObject(String val,ModelMap modelMap){
        if(val=="")
            return StateJudge.error();
        //String CHINESE_REGEX = "[\\u4e00-\\u9fa5]+";// 纯中文匹配正则
        String LETTER_REGEX="[a-zA-Z]+";  // 纯英文
        User user=(User)modelMap.get("user");
        // 一.查询出该用户的所有好友
        List<User> friends=new ArrayList<>();       // 好友用户表
        Map<User,String> remarkMap=new HashMap<>(); // 好友备注表
        for(GroupSplit gs:user.getGroupSplits()){
            List<User> friend=userService.findFriendByGsId(gs.getGsId());
            for(User u:friend){
                String remark=gsuService.findFriendRemark(gs.getGsId(),u.getId());
                remarkMap.put(u,remark);
            }
            friends.addAll(friend);
        }
        // 二.开始搜索
        Map<Integer,User> userMap=new HashMap<>(); // 最终返还给前端的数据(还要处理一下)
        String regex=".*"+val+".*";
        //1.根据名字和dsqId进行模糊查询
        for(User friend:friends){
            String name=friend.getUsername();
            String dsqId=friend.getDsqId();
            String pingyinName= Pingyin.convertHanzi2Pinyin(name,true).toLowerCase();
            String pingyinFirst=Pingyin.convertFirstHanzi(name,true).toLowerCase();
            // 如果输入的全是英文字母
            if(val.matches(LETTER_REGEX)){
                val=val.toLowerCase();
                regex=".*"+val+".*"; // 别忘了
            }

            if(name.matches(regex)){
                userMap.put(friend.getId(),friend);
            }
            else if(dsqId.matches(regex)){
                userMap.put(friend.getId(),friend);
            }
            // 拼音匹配上(首字母,全部字母)
            else if(val.matches(LETTER_REGEX)&&(pingyinName.matches(regex)||pingyinFirst.matches(regex))){
                userMap.put(friend.getId(),friend);
            }
        }
        // 2.备注模糊查询
        for(User u:remarkMap.keySet()){
            String s=remarkMap.get(u);
            String pingyinName=Pingyin.convertHanzi2Pinyin(s,true).toLowerCase();
            String pingyinFirst=Pingyin.convertFirstHanzi(s,true).toLowerCase();
            // 如果输入的全是英文字母转换成小写
            if(val.matches(LETTER_REGEX)){
                val=val.toLowerCase();
                regex=".*"+val+".*"; // 别忘了
            }
            if(s.matches(regex)){
                userMap.put(u.getId(),u);
            }else if(val.matches(LETTER_REGEX)&&(pingyinName.matches(regex)||pingyinFirst.matches(regex))){
                userMap.put(u.getId(),u);
            }
        }
        // 2.获得
        List<User> users=new ArrayList<>(); // 查询出的好友
        List<String> remarks=new ArrayList<>();// 查询出的好友对应的备注
        for(User friend:userMap.values()){
            users.add(friend);
            remarks.add(remarkMap.get(friend));
        }
        return StateJudge.success().setHashMap("users",users).setHashMap("remarks",remarks);
    }
    /*添加群聊,好友聊天的最后一条聊天记录到session中,对应的时间
     *@Date 20/12/28 16:17
     */
    public void addLastMsg(ModelMap model, User user){
        List<GroupChat> groupChats= (List<GroupChat>) model.get("gc"); // 用户加入的群聊
        List<User> friends= (List<User>) model.get("friends"); // 用户的好友
        // 最后一条消息(键为群聊id,值为群聊的最后一条聊天记录)
        // 消息对应的时间格式化
        Map<Integer,String> time=new HashMap<>();
        Map<String, GroupMsg> groupmsg=new HashMap<>();
        for(GroupChat gc:groupChats){
            GroupMsg gMsg=gmService.findLastGroupMsg(gc.getGcId());
            if(gMsg==null)
                continue;
            // 群聊消息对应的时间格式化
            Date date=gMsg.getTime();
            String str= DateFormat.formatNow(date);
            time.put(gMsg.getGmId(),str);
            gMsg.getSendUser().setUsername(gMsg.getSendUser().getUsername()+":");
            groupmsg.put(gc.getGcId(),gMsg);
        }
        // 健为好友id,值为俩人的最后一条聊天记录
        Map<Integer, SingleMsg> singlemsg=new HashMap<>();
        for(User user2:friends){
            SingleMsg msg;
            SingleMsg msg1=smService.findLastSingleMsg(user.getId(),user2.getId());
            SingleMsg msg2=smService.findLastSingleMsg(user2.getId(),user.getId());
            if(msg1==null&&msg2==null)
                continue;
            else if(msg1==null&&msg2!=null)
                msg=msg2;
            else if(msg1!=null&&msg2==null)
                msg=msg1;
            else if(DateFormat.less(msg1.getTime(),msg2.getTime()))
                msg=msg2;
            else
                msg=msg1;
            // 好友消息对应的时间格式化
            Date date=msg.getTime();
            String str=DateFormat.formatNow(date);
            time.put(msg.getSingleId(),str);
            msg.getSendUser().setUsername(msg.getSendUser().getUsername()+":");
            singlemsg.put(user2.getId(),msg);
        }
        model.addAttribute("groupmsg",groupmsg);
        model.addAttribute("singlemsg",singlemsg);
        model.addAttribute("time",time);
    }
    /*添加用户加入的群聊到session中
     *
     */
    public void addGroupChat(ModelMap model, User user){
        // 添加加入的群聊
        List<GroupChat> groupChats=gcService.joinGroups(user.getId());
        // 添加好友
        List<User> friends=new ArrayList<>();
        for(GroupSplit gs:user.getGroupSplits()){
            List<User> users=userService.findFriendByGsId(gs.getGsId());
            for(User user3:users)
                friends.add(user3);
        }
        model.addAttribute("gc",groupChats);
        model.addAttribute("friends",friends);

    }
    /*添加用户的分组信息到session中
     *
     */
    public void addGroupSplit(ModelMap model,User user){
        // 添加分组的session
        Map<String,List<User>> map=new HashMap<>();
            for(GroupSplit gs:user.getGroupSplits()){
            List<User> users=userService.findFriendByGsId(gs.getGsId());
            // 分组id做键,用户做值
            map.put(gs.getGsId(),users);
        }
        // 想到了俩种思路${${}}(弊端太多,session名字要一一添加,而且不支持嵌套) ,还有map集合(el支持${map[]})
        model.addAttribute("gs",map);
    }

    @RequestMapping("/main")
    public String main(ModelMap model){
        User user= (User) model.get("user");
        user=userService.findByid(user.getId());
        // 添加到session(最后一条群聊,好友消息,拥有的好友,加入的群聊,拥有的分组)
        addGroupSplit(model,user);
        addGroupChat(model,user);
        addLastMsg(model,user);
        model.addAttribute("user",user);
        return "weixinMain";
    }
    @RequestMapping("/chat")
    public String chat(){
        return "chatRoom";
    }

    /**
     * 跳转到朋友栏
     * @return
     */
    @RequestMapping("/selectFriend")
    public String selectFriend(ModelMap model){
        User user= (User) model.get("user");
        user=userService.findByid(user.getId());
        // 添加到session(最后一条群聊,好友消息,拥有的好友,加入的群聊,拥有的分组)
        addGroupSplit(model,user);
        addGroupChat(model,user);
        model.addAttribute("user",user);
        return "weixinFriend";
    }

    /**
     * 跳转到收藏栏
     * @return
     */
    @RequestMapping("/selectCollect")
    public String selectCollect(){return "weixinCollect";}

    /**
     * 从朋友栏跳转到聊天栏(更新聊天记录选中框的session)
     * @param gcid
     * @param uid
     * @param model
     * @return
     */
    @RequestMapping("/selectChat")
    public String selectChat(String gcid,Integer uid,ModelMap model){

        // 更新选中框的session
        if(!"undefined".equals(gcid)){
            model.addAttribute("selectId",gcid);
        }

        else if(!"undefined".equals(uid))
            model.addAttribute("selectId",uid);
        return "weixinMain";
    }
}
