package cn.itcast.controller;

import cn.itcast.domain.*;
import cn.itcast.service.*;
import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
@SessionAttributes({"user","selectId"})
/*登录注册页面*/
public class ChatFrameController {
    @Autowired
    UserService userService;
    @Autowired
    GroupChatService gcService;
    @Autowired
    GroupSplitUserService gsuService;
    @Autowired
    SingleMsgService smService;
    // 获得上一个被选中的id,并修改它的离开时间 instanceof
    public void leaveTimeUpdate(ModelMap model){
        if(model.get("selectId")!=null){
            // 上一个是群聊id
            if(model.get("selectId") instanceof String==true){
                User user=(User)model.get("user");
                String priorGcId=(String)model.get("selectId");
                gcService.updateLeaveTime(priorGcId,user.getId());
            }
            // 上一个是uid
            else {
                int friendId=(int)model.get("selectId");
                String gsId = null;
                User user=(User)model.get("user");
                // 获得当前登录用户的分组信息
                List<GroupSplit> splits=user.getGroupSplits();
                List<GroupSplitUser> friends=new ArrayList<>();// 登录用户的所有好友
                // 遍历,获得当前用户的好友所在的分组
                for(GroupSplit split:splits)
                    friends.addAll(gsuService.findFriends(split.getGsId()));
                // 找到好友所在的分组
                for(GroupSplitUser gsu:friends){
                    if(friendId==gsu.getUserid()){
                        gsId=gsu.getGsId();
                        break;
                    }
                }
                // 更改离开好友聊天框的时间
                gsuService.updateLeaveTime(gsId,friendId);
            }
        }
    }
    /**获取群信息(群中的用户,群聊对象(包含群聊天记录))
     * @Date 2020/12/26 12:10
     */
    @RequestMapping("/group")
    public @ResponseBody StateJudge groupFrame(String gcId, ModelMap model){
        leaveTimeUpdate(model);
        // 添加聊天记录界面当前选择的id到session
        model.addAttribute("selectId",gcId);
        // 查询出该群下的用户个数,群名字
        List<User> users=userService.findUsersByGcId(gcId);
        GroupChat groupchat=gcService.findByGcid(gcId);
        return StateJudge.success().setHashMap("users",users).setHashMap("groupchat",groupchat).setHashMap("gsize",users.size());
    }
    /**选择的好友聊天记录框(俩个人的聊天记录,好友个人信息)
     * @Date 20/12/27 12:05
     */
    @RequestMapping("/friend")
    public @ResponseBody StateJudge friendFrame(int uid,ModelMap modelMap){
        leaveTimeUpdate(modelMap);
        // 添加聊天记录界面当前选择的id到session
        modelMap.addAttribute("selectId",uid);
        User loginuser= (User) modelMap.get("user");
        // 根据uid查询出用户对象
        User user=userService.findUserByid(uid);
        // 查询出俩人的聊天记录
        List<SingleMsg> singleMsgs=smService.findByUser(user.getId(),loginuser.getId());

        return StateJudge.success().setHashMap("user",user).setHashMap("msgs",singleMsgs);
    }


}
