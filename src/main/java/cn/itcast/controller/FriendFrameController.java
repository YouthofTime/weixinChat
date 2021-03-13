package cn.itcast.controller;

import cn.itcast.domain.GroupChat;
import cn.itcast.domain.GroupSplit;
import cn.itcast.domain.User;
import cn.itcast.service.GroupSplitService;
import cn.itcast.service.GroupSplitUserService;
import cn.itcast.service.UserService;
import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/friend")
@SessionAttributes({"user"})
public class FriendFrameController {
    @Autowired
    UserService userService;
    @Autowired
    GroupSplitUserService gsuService;

    /**
     * 获取好友的个人信息(用户信息,备注)
     * @param uid   当前在线用户的id
     * @param gsId   好友所在的分组id
     * @return
     */
    @RequestMapping("/showFriend")
    public @ResponseBody StateJudge showFriend(String uid, String gsId){
        // 更新右边显示框的信息
        if(!"undefined".equals(uid))
        {
            int id= Integer.parseInt(uid);
            // 1.查询出好友的用户信息
            User friend=userService.findByid(id);
            // 2.查询出好友的备注
            String remark=gsuService.findFriendRemark(gsId,id);
            return StateJudge.success().setHashMap("user",friend).setHashMap("remark",remark);
        }
        return StateJudge.error();
    }

    /**
     * 删除好友
     * @param uid  好友id
     * @param gsId 好友所在分组
     * @return
     */
    @RequestMapping("/deleteFriend")
    public @ResponseBody StateJudge deleteFriend(int uid, String gsId, ModelMap modelMap){
        User loginUser= (User) modelMap.get("user");
        User splitUser=userService.findByid(uid);
        String matchGsid="";
        try {
            // 1.查询被删除用户的所拥有的分组
            List<GroupSplit> uidInGsIds=splitUser.getGroupSplits();
            // 2.对当前登录用户的所有被分组
            List<String> splits=gsuService.findByUid(loginUser.getId());
            for(GroupSplit uidInGsId:uidInGsIds){
                String gsid1=uidInGsId.getGsId();
                for(String gsid2:splits){
                    if(gsid1.equals(gsid2))
                        matchGsid=gsid1;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        gsuService.deleteByGcIdUid(matchGsid,loginUser.getId());
        gsuService.deleteByGcIdUid(gsId,uid);
        return StateJudge.success();
    }
    @RequestMapping("/updateFriend")
    public @ResponseBody StateJudge updateFriend(String remark,String gsId,int uid){
        gsuService.updateFriend(gsId,uid,remark);
        return StateJudge.success();
    }
}
