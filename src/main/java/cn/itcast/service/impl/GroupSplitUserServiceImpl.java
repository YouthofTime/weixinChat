package cn.itcast.service.impl;

import cn.itcast.dao.GroupSplitDao;
import cn.itcast.dao.GroupSplitUserDao;
import cn.itcast.domain.GroupSplit;
import cn.itcast.domain.GroupSplitUser;
import cn.itcast.domain.User;
import cn.itcast.service.GroupSplitUserService;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("gsuService")
public class GroupSplitUserServiceImpl implements GroupSplitUserService {
    @Autowired
    GroupSplitUserDao gsuDao;
    @Autowired
    GroupSplitDao gsDao;
    @Autowired
    UserService userService;
    @Override
    public String findFriendRemark(String gsId, int uid) {
        GroupSplitUser gsUser=gsuDao.findByUidAndGsId(gsId,uid);
        return gsUser.getRemark();
    }

    @Override
    public void saveGroupChatUser(GroupSplitUser groupSplitUser) {
        gsuDao.saveGroupChatUser(groupSplitUser);
    }

    @Override
    public void deleteByGcIdUid(String gsId, int uid) {
        gsuDao.deleteByGcIdUid(gsId,uid);
    }

    @Override
    public void updateFriend(String gsId, int uid, String remark) {
        gsuDao.updateFriend(gsId,uid,remark);
    }

    @Override
    public void updateLeaveTime(String gsId, int userid) {
        gsuDao.updateLeaveTime(gsId,userid);
    }

    @Override
    public List<GroupSplitUser> findFriends(String gsId) {
        return gsuDao.findByGsid(gsId);
    }

    @Override
    public List<String> findByUid(int uid) {
        return gsuDao.findByUid(uid);
    }

    @Override
    public boolean isFriend(int userId,int friendId) {
        // 1.获得用户的分组
        List<GroupSplit> splits=gsDao.findByUid(userId);
        // 2.获得分组下的所有用户
        List<User> friends=new ArrayList<>();
        for(GroupSplit gs:splits){
            String gsId=gs.getGsId();
            List<User> users=userService.findFriendByGsId(gsId);
            for(User user:users)
                friends.add(user);
        }
        // 3.判断friendId是否在friends中
        for(User user:friends){
            if(user.getId()==friendId)
                return true;
        }
        return false;
    }
}
