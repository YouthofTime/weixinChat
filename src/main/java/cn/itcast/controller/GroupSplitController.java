package cn.itcast.controller;

import cn.itcast.domain.GroupSplit;
import cn.itcast.domain.User;
import cn.itcast.service.GroupSplitService;
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
@RequestMapping("/gs")
@SessionAttributes({"user"})
public class GroupSplitController {
    @Autowired
    GroupSplitService gsService;
    @Autowired
    UserService userService;
    /**
     * 添加分组
     * @param gsName
     * @param model
     * @return
     */
    @RequestMapping("/addSplit")
    public @ResponseBody StateJudge addSplit(String gsName,ModelMap model){
        try {
            GroupSplit gsplit=new GroupSplit();
            User user=(User)model.get("user");
            int uid=user.getId();
            gsplit.setBelongId(uid);
            int lgsid=gsService.findCountByUid(uid);
            lgsid+=1;
            gsplit.setGsId("gs"+uid+"_"+lgsid);
            gsplit.setGsname(gsName);
            gsService.saveGroupSplit(gsplit);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return StateJudge.success();
    }

    /**
     * 删除分组
     * @param gsId
     * @param model
     * @return
     */
    @RequestMapping("/delSplit")
    public @ResponseBody StateJudge delSplit(String gsId,ModelMap model){
        // 1.查询该分组下是否还有用户
        List<User> users=userService.findFriendByGsId(gsId);
        if(users.size()!=0)
            return StateJudge.error();
        else {
            gsService.deleteByGsId(gsId);
            return StateJudge.success();
        }
    }
    @RequestMapping("/reSplitName")
    public @ResponseBody StateJudge reSplitName(String gsId,String gsName){
        gsService.updateGroupSplit(gsName,gsId);
        return StateJudge.success();
    }
}
