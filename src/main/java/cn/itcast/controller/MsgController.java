package cn.itcast.controller;


import cn.itcast.domain.*;
import cn.itcast.service.*;
import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;


@Controller
@RequestMapping("/msg")
@SessionAttributes({"groupmsg","user"})
public class MsgController {
    @Autowired
    GroupMsgService gmService;
    @Autowired
    SingleMsgService smService;
    @Autowired
    UserService userService;
    @Autowired
    GroupSplitUserService gsuService;
    @Autowired
    GroupChatService gcService;

    /*保存群聊消息
    *@Date 20/12/26 19:48
    *
     */
    @RequestMapping("/saveGroup")
    public @ResponseBody StateJudge groupMsg(@RequestBody GroupMsg groupMsg){
        gmService.saveGroupMsg(groupMsg);
        return StateJudge.success();
    }
    /*保存单个消息
     *@Date 20/12/26 22:00
     *
     */
    @RequestMapping("/saveSingle")
    public @ResponseBody StateJudge singleMsg(@RequestBody SingleMsg singleMsg){
        smService.saveSingleMsg(singleMsg);
        return StateJudge.success();
    }
    @RequestMapping("/unRead")
    public @ResponseBody StateJudge unRread(ModelMap modelMap){
        try {
            // String代表聊天室的名字(3-2就是用户3和用户2,3-gc31用户3和群聊gc31),Integer代表未读消息的数目
            Map<String,Integer> unreads=new HashMap<>();
            // 获取当前登录用户
            User user=(User)modelMap.get("user");
            // 一.好友框未读消息(完成了)
            // 1.获取这个user对象的所有好友
            List<GroupSplit> splits=user.getGroupSplits();
            List<GroupSplitUser> friends=new ArrayList<>();// 登录用户的所有好友
            // 2.遍历,获得当前用户的好友所在的分组
            for(GroupSplit split:splits)
                friends.addAll(gsuService.findFriends(split.getGsId()));
            for(GroupSplitUser gs:friends){
                int receiveUid=gs.getUserid();
                // 3.查询出俩人的聊天记录的最后一条消息
                SingleMsg msg=smService.findLastSingleMsg(user.getId(),receiveUid);
                SingleMsg msg1=smService.findLastSingleMsg(receiveUid,user.getId());
                SingleMsg lastmsg=null;
                // 都不为空
                if(msg!=null&&msg1!=null){
                    if(msg.getTime().compareTo(msg1.getTime())<0)
                        lastmsg=msg1;
                    else
                        lastmsg=msg;
                }
                // 收到的消息不为空
                else if(msg1!=null&&msg==null)
                    lastmsg=msg1;
                else if(msg1==null&&msg!=null)
                    lastmsg=msg;
                // 4.最后一条消息是本人发的,就没有未读消息(或者是为null,也要设置为0)
                if(lastmsg==null||lastmsg==msg){
                    unreads.put(user.getId()+"-"+gs.getUserid(),0);
                }
                // 5.不是,离开好友框的时间到最后一条消息这个时间段的消息数量
                else if(lastmsg==msg1){
                    Date leaveTime=gs.getLeaveTime();
                    List<SingleMsg> singleMsgs=smService.findAfterTime(gs.getUserid(),user.getId(),leaveTime);
                    unreads.put(user.getId()+"-"+gs.getUserid(),singleMsgs.size());
                }
            }
            //二.群聊框未读消息
            // 1.获得该用户加入的所有群聊
            List<GroupChat> joins=gcService.joinGroups(user.getId());
            // 2.查询群聊的最后一条消息
            for(GroupChat join:joins){
                GroupMsg gLastMsg=gmService.findLastGroupMsg(join.getGcId());
                // 该群聊没发送过消息
                if(gLastMsg==null)
                    unreads.put(join.getGcId()+"-"+user.getId(),0);
                else {
                    // 1.最后一条消息是本人发送的
                    if(gLastMsg.getSendUid()==user.getId())
                        unreads.put(join.getGcId()+"-"+user.getId(),0);
                        // 2.不是,离开好友框的时间到最后一条消息这个时间段的消息数量
                    else {
                        Date leaveTime=gcService.findLeaveTime(join.getGcId(),user.getId());
                        List<GroupMsg> groupMsgs=gmService.findAfterTime(leaveTime,join.getGcId());
                        unreads.put(join.getGcId()+"-"+user.getId(),groupMsgs.size());
                    }
                }
            }
//            for(String key:unreads.keySet()){
//                System.out.println(key+":"+unreads.get(key));
//            }
            return StateJudge.success().setHashMap("unread",unreads);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.error();

    }
}
