package cn.itcast.service.impl;

import cn.itcast.dao.GroupChatDao;
import cn.itcast.dao.GroupChatUserDao;
import cn.itcast.dao.GroupMsgDao;
import cn.itcast.domain.GroupChat;
import cn.itcast.domain.GroupChatUser;
import cn.itcast.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@Service("gcService")
public class GroupChatServiceImpl implements GroupChatService {
    @Autowired
    GroupChatDao gcdao;
    @Autowired
    GroupChatUserDao gcuserdao;
    @Autowired
    GroupMsgDao gmsgDao;
    @Override
    public List<GroupChat> joinGroups(int uid) {
        List<String> gcIds=gcuserdao.findByUid(uid);
        List<GroupChat> groupChats=new ArrayList<>();
        for(String gcId:gcIds){
            groupChats.add(gcdao.findByGcid(gcId));
        }
        return groupChats;
    }

    @Override
    public GroupChat findByGcid(String gcId) {
        return gcdao.findByGcid(gcId);
    }

    @Override
    public void delGroupMember(String gcId, int uid) {
        gcuserdao.deleteByGcIdUid(gcId,uid);
    }

    @Override
    public void updateLeaveTime(String gcId, int uid) {
        gcuserdao.updateByGcIdUid(gcId,uid);
    }

    @Override
    public Date findLeaveTime(String gcId, int uid) {
        return gcuserdao.findLeaveTime(gcId,uid);
    }

    @Override
    public void addGroupMember(String gcId, int uid) {
        GroupChatUser gcUser=new GroupChatUser();
        gcUser.setUserid(uid);
        gcUser.setGcId(gcId);
        gcuserdao.saveGroupChatUser(gcUser);
    }

    @Override
    public int findCreateGroups(int uid) {
        return gcdao.findCountByUid(uid);
    }

    @Override
    public void saveGroupChat(GroupChat groupChat) {
        gcdao.saveGroupChat(groupChat);
        // 同时将该群主加入该群聊
        GroupChatUser gcUser=new GroupChatUser();
        String gcId=groupChat.getGcId();
        int userid=groupChat.getBelongId();
        gcUser.setGcId(gcId);
        gcUser.setUserid(userid);
        gcuserdao.saveGroupChatUser(gcUser);

    }

    @Override
    public List<GroupChat> findByName(String gcname) {
        return gcdao.findByName(gcname);
    }

    @Override
    public LinkedList<GroupChat> findByUid(int uid) {
        return gcdao.findByUid(uid);
    }

    @Override
    public void dissolve(String gcId) {
        // 1.删除群聊天记录
        gmsgDao.deleteByGcId(gcId);
        // 2.删除群聊中的所有群成员
        gcuserdao.deleteByGcid(gcId);
        // 3.删除群聊记录
        gcdao.deleteByGcid(gcId);
    }

    @Override
    public void updateGroupChat(GroupChat groupChat) {
        gcdao.updateGroupChat(groupChat);
    }

    @Override
    public List<GroupChatUser> findGcUsers(String gcId) {
        return gcuserdao.findGcUsers(gcId);
    }
}
