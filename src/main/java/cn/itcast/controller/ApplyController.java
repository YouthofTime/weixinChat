package cn.itcast.controller;

import cn.itcast.domain.Apply;
import cn.itcast.domain.GroupChat;
import cn.itcast.domain.GroupSplitUser;
import cn.itcast.domain.User;
import cn.itcast.service.ApplyService;
import cn.itcast.service.GroupChatService;
import cn.itcast.service.GroupSplitUserService;
import cn.itcast.utils.CommonJudge;
import cn.itcast.utils.StateJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/apply")
@SessionAttributes({"user"})
public class ApplyController {
    @Autowired
    ApplyService applyService;
    @Autowired
    GroupSplitUserService gsuService;
    @Autowired
    GroupChatService gcService;

    /**
     * 添加一条申请记录
     * @param apply
     * @return
     */
    @RequestMapping("/addApply")
    public @ResponseBody StateJudge addApply(@RequestBody Apply apply){
        String sendId=apply.getSendUid();
        String receiveId=apply.getReceiveId();
        // 1.判断俩人是否为好友
        if(CommonJudge.isInteger(receiveId)&&CommonJudge.isInteger(sendId)){
            if(gsuService.isFriend(Integer.parseInt(receiveId),Integer.parseInt(sendId))){
                return StateJudge.error();
            }
        }
        // 2.判断该用户是否已经加入该群聊
        if(!CommonJudge.isInteger(receiveId)&&CommonJudge.isInteger(sendId)) {
            System.out.println(1);
            // 1.查询出这个被添加的用户加入的群聊
            List<GroupChat> joinChats=gcService.joinGroups(Integer.parseInt(sendId));
            // 2.判断gcId是否在其中
            for(GroupChat joinChat:joinChats){
                if(joinChat.getGcId().equals(receiveId)){
                    return StateJudge.error();
                }

            }
        }
        // 添加请求
        Apply apply1=applyService.findBySRId(apply.getSendUid(),apply.getReceiveId());
        // 删除之前的一条相同申请记录
        if(apply1!=null)
            applyService.deleteById(apply1.getApplyId());
        applyService.addApply(apply);
        return StateJudge.success();
    }
    @RequestMapping("/findAllApply")
    public @ResponseBody StateJudge findAllApply(String receiveId,ModelMap modelMap){
        User user=(User) modelMap.get("user");
        // 该用户拥有的群聊
        List<GroupChat> gcs=gcService.findByUid(user.getId());
        List<List<Apply>> lists=new ArrayList<>();
        for(GroupChat gc:gcs){
            lists.add(applyService.findAll(gc.getGcId()));
        }
        List<Apply> applies=applyService.findAll(receiveId);
        for(List<Apply> list1:lists)
            for(Apply apply:list1){
                applies.add(apply);
            }
        return StateJudge.success().setHashMap("applies",applies).setHashMap("size",applies.size()).setHashMap("gs",user.getGroupSplits()).setHashMap("gssize",user.getGroupSplits().size());

    }
    @RequestMapping("/joinGroupApply")
    public @ResponseBody StateJudge joinGroupApply(String sendUid,String receiveId){
        try {
            Apply apply=applyService.findBySRId(sendUid,receiveId);
            // 1.添加gcUser记录
            gcService.addGroupMember(sendUid,Integer.parseInt(receiveId));
            // 2.修改apply表的isApprove字段(修改申请状态)
            applyService.updateById(apply.getApplyId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.success();
    }
    @RequestMapping("/joinGroupApprove")
    public @ResponseBody StateJudge joinGroupApprove(String sendUid,String receiveId){
        try {
            Apply apply=applyService.findBySRId(sendUid,receiveId);
            // 1.添加gcUser记录
            gcService.addGroupMember(receiveId,Integer.parseInt(sendUid));
            // 2.修改apply表的isApprove字段(修改申请状态)
            applyService.updateById(apply.getApplyId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.success();
    }
    @RequestMapping("/approveApply")
    public @ResponseBody StateJudge approveApply(String sendUid,String receiveId){
        try {
            Apply apply=applyService.findBySRId(sendUid,receiveId);
            // 1.添加gsUser记录(添加好友)
            GroupSplitUser gsUser=new GroupSplitUser();
            gsUser.setGsId(apply.getGsId());
            gsUser.setRemark(apply.getRemark());
            gsUser.setUserid(Integer.parseInt(apply.getReceiveId()));
            gsuService.saveGroupChatUser(gsUser);
            // 2.修改apply表的isApprove字段(修改申请状态)
            applyService.updateById(apply.getApplyId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.success();

    }
    @RequestMapping("/beApprovedApply")
    public @ResponseBody StateJudge beApprovedApply(String uid,String gsId){
        GroupSplitUser gsUser=new GroupSplitUser();
        gsUser.setGsId(gsId);
        gsUser.setRemark("");
        gsUser.setUserid(Integer.parseInt(uid));
        gsuService.saveGroupChatUser(gsUser);
        return StateJudge.success();
    }
}
