package cn.itcast.service.impl;

import cn.itcast.dao.GroupChatUserDao;
import cn.itcast.dao.GroupSplitUserDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.GroupChatUser;
import cn.itcast.domain.GroupSplitUser;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    GroupSplitUserDao gsudao;
    @Autowired
    UserDao userDao;
    @Autowired
    GroupChatUserDao gcudao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByid(int id) {
        return userDao.findByid(id);
    }

    @Override
    public User findUserByid(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public List<User> findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public User findByDsqId(String dsqId) {
        return userDao.findByDsqId(dsqId);
    }

    @Override
    public User findByBindEmail(String bind_email) {
        return userDao.findByBindEmail(bind_email);
    }

    @Override
    public User findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public List<User> findFriendByGsId(String gsId) {
        List<User> users=new ArrayList<>();
        List<Integer> Iusers=new ArrayList<>();
        for(GroupSplitUser gsu:gsudao.findByGsid(gsId))
            Iusers.add(gsu.getUserid());

        for(int id:Iusers){
            users.add(userDao.findByid(id));
        }
        return users;
    }

    @Override
    public List<User> findUsersByGcId(String gcId) {
        List<User> users=new ArrayList<>();
        List<Integer> Iusers=gcudao.findByGcid(gcId);
        for(int id:Iusers){
            users.add(userDao.findByid(id));
        }
        return users;
    }

    @Override
    public List<User> findFuzzyByName(String username) {
        return userDao.findFuzzyByName(username);
    }

    @Override
    public List<User> findFuzzyByDsqId(String dsqId) {
        return userDao.findFuzzyByDsqId(dsqId);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
