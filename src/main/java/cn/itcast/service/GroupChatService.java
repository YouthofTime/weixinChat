package cn.itcast.service;

import cn.itcast.domain.GroupChat;
import cn.itcast.domain.GroupChatUser;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public interface GroupChatService {
    /*根据用户id查询出该用户加入的群聊*/
    List<GroupChat> joinGroups(int userid);

    /*根据群聊id查询出群聊对象*/
    GroupChat findByGcid(String gcId);

    /*根据群聊id和用户id删除群成员*/
    void delGroupMember(String gcId,int uid);

    /*修改群成员离开群聊聊天框的时间*/
    void updateLeaveTime(String gcId,int uid);

    /*获得群人员离开群聊天框的时间*/
    Date findLeaveTime(String gcId,int uid);

    /*添加群人员*/
    void addGroupMember(String gcId,int uid);

    /*根据id查询该用户创建的群聊数目*/
    int findCreateGroups(int uid);

    /*创建一个群聊对象*/
    void saveGroupChat(GroupChat groupChat);

    /*根据群聊名字查询出所有群对象*/
    List<GroupChat> findByName(String gcname);

    /*根据id查询出该用户创建的群聊*/
    LinkedList<GroupChat> findByUid(int uid);

    /*解散一个群聊*/
    void dissolve(String gcId);
    /*修改群聊头像,名字*/
    void updateGroupChat(GroupChat groupChat);
    /*查询群聊中的用户*/
    List<GroupChatUser> findGcUsers(String gcId);
}
