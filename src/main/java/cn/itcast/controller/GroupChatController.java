package cn.itcast.controller;

import cn.itcast.domain.GroupChat;
import cn.itcast.domain.GroupChatUser;
import cn.itcast.domain.User;
import cn.itcast.service.GroupChatService;
import cn.itcast.service.UserService;
import cn.itcast.utils.DateFormat;
import cn.itcast.utils.StateJudge;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @CreatTime 2021/2/3 15:30
 */
@Controller
@RequestMapping("/gc")
@SessionAttributes({"gc","gcUsers","user"})
public class GroupChatController {
    @Autowired
    UserService userService;
    @Autowired
    GroupChatService gcService;

    /**
     * 解散一个群聊
     * @param gcId
     * @return
     */
    @RequestMapping("/delGroup")
    public @ResponseBody StateJudge delGroup(String gcId){
        gcService.dissolve(gcId);
        return StateJudge.success();
    }
    /**
     * 判断一个用户是否在群聊中
     * @param gcId
     * @param uid
     * @return
     */
    @RequestMapping("/isInGroup")
    public @ResponseBody StateJudge isInGroup(String gcId,int uid){
        // 1.查询出这个被添加的用户加入的群聊
        List<GroupChat> joinChats=gcService.joinGroups(uid);
        // 2.判断gcId是否在其中
        for(GroupChat joinChat:joinChats){
            if(joinChat.getGcId().equals(gcId))
                return StateJudge.error();
        }
        return StateJudge.success();
    }

    /**
     * 是否为群主,是success
     * @param gcId
     * @param modelMap
     * @return
     */
    @RequestMapping("/isGroupOwner")
    public @ResponseBody StateJudge isGroupOwner(String gcId,ModelMap modelMap){
        // 1.获得群聊对象和用户
        User user=(User)modelMap.get("user");
        GroupChat gc=gcService.findByGcid(gcId);
        if(gc.getBelongId()==user.getId())
            return StateJudge.success();
        else
            return StateJudge.error();
    }
    /**
     * 添加群聊id到session中
     * @param gcId
     * @param model
     * @return
     */
    @RequestMapping("/groupManage")
    public String groupManage(String gcId, Model model){
        GroupChat gc=gcService.findByGcid(gcId);
        model.addAttribute("gc",gc);
        return "/group/groupManage";
    }

    /**
     * 根据群聊id或者群聊名字查询群对象,要具体判断
     * @param gcIdName
     * @return
     */
    @RequestMapping("/findGroups")
    public @ResponseBody StateJudge findGroups(String gcIdName){
        try {
            GroupChat gc=gcService.findByGcid(gcIdName);
            List<GroupChat> gcs=gcService.findByName(gcIdName);
            if(gc!=null){
                // 查询出该群下的用户数目
                int number=userService.findUsersByGcId(gc.getGcId()).size();
                return StateJudge.success().setHashMap("gc",gc).setHashMap("gcusers",number);
            }
            else if(gcs.size()!=0){
                List<Integer> numbers=new ArrayList<>();
                // 统计每个群下的用户数
                for(GroupChat group:gcs){
                    int gcusers=userService.findUsersByGcId(group.getGcId()).size();
                    numbers.add(gcusers);
                }
                return StateJudge.success().setHashMap("gcs",gcs).setHashMap("numbers",numbers);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.success();
    }

    /**
     * 根据群聊id查询出群对象,并将群主排在最前面
     * @param gcId
     * @param model
     * @return
     */
    @RequestMapping("/findGroup")
    public @ResponseBody StateJudge findGroup(String gcId, ModelMap model){
        // 1.查询出群聊中的用户
        List<User> gcUsers=userService.findUsersByGcId(gcId);
        // 排序,群主排最前面
        User loginUser=(User)model.get("user");
        for(int i=0;i<gcUsers.size();i++){
            if(gcUsers.get(i).getId()==loginUser.getId())
            {
                gcUsers.remove(i);
                gcUsers.add(0,loginUser);
            }
        }
        // 2.查询出群聊对象
        GroupChat group=gcService.findByGcid(gcId);
        List<GroupChatUser> gcUsers1=gcService.findGcUsers(gcId);
        List<String> EnterTimes=new ArrayList<>();
        List<String> LeaveTimes=new ArrayList<>();
        for(GroupChatUser gcUser:gcUsers1){
            EnterTimes.add(DateFormat.format(gcUser.getEnterTime(),DateFormat.sdfDate));
            LeaveTimes.add(DateFormat.format(gcUser.getLeaveTime(),DateFormat.sdfDate));
        }
        return StateJudge.success().setHashMap("gcUsers",gcUsers).setHashMap("group",group).setHashMap("EnterTimes",EnterTimes).setHashMap("LeaveTimes",LeaveTimes);
    }

    /**
     * 用户退出群聊
     * @param gcId 群聊号
     * @param uid  用户
     * @return
     */
    @RequestMapping("/delGcUser")
    public @ResponseBody StateJudge delGcUser(String gcId,int uid){
        gcService.delGroupMember(gcId,uid);
        return StateJudge.success();
    }

    /**
     * 创建群聊
     * @param belongId 群主id
     * @param gcname   群名字
     * @return
     */
    @RequestMapping("/addGroup")
    public @ResponseBody StateJudge addGroup(int belongId,String gcname){
        GroupChat g=new GroupChat();
        int uid=belongId;
        LinkedList<GroupChat> gcs= (LinkedList<GroupChat>) gcService.findByUid(uid);
        GroupChat last=gcs.getLast();
        String lastGcId=last.getGcId();
        int postfix=Integer.parseInt(last.getGcId().substring(lastGcId.length()-1))+1;
        String gcid="gc"+uid+"_"+postfix;
        g.setBelongId(uid);
        g.setGcId(gcid);
        g.setBuildtime(new Date());
        g.setGcname(gcname);
        int i= (int)(Math.random()*3+1);
        String headImg="group/头像"+i+".jpg";
        g.setHeadImg(headImg);
        gcService.saveGroupChat(g);
        return StateJudge.success().setHashMap("gc",g);
    }
}
