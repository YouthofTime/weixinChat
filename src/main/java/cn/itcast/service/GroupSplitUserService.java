package cn.itcast.service;

import cn.itcast.domain.GroupSplitUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupSplitUserService {
    /*获得好友的备注*/
    String findFriendRemark(String gsId,int uid);
    /*增加好友*/
    void saveGroupChatUser(GroupSplitUser groupSplitUser);
    /*删除好友*/
    void deleteByGcIdUid(String gsId, int uid);
    /*修改好友*/
    void updateFriend(String gsId,int uid,String remark);
    /*修改离开好友聊天框的时间*/
    void updateLeaveTime(String gsId,int userid);
    /*获得某个分组下的所有好友*/
    List<GroupSplitUser> findFriends(String gsId);
    /*根据uid查询gsId*/
    List<String> findByUid(int uid);
    /*判断俩人是否为好友*/
    boolean isFriend(int userId,int friendId);
}
