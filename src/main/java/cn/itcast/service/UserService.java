package cn.itcast.service;

import cn.itcast.domain.User;

import java.util.List;


/*
*@Date 2020/12/26 15:55
 */
public interface UserService {
    /*查询所有账号*/
    List<User> findAll();
    /*根据id查询账号*/
    User findByid(int id);
    /*只查询User原有属性 */
    User findUserByid(int id);
    /*保存账号*/
    void saveUser(User user);
    /*根据用户名查询账号*/
    List<User> findByName(String username);
    /*根据dsqId查询账号*/
    User findByDsqId(String dsqId);
    /*根据绑定邮箱查询账号*/
    User findByBindEmail(String bind_email);
    /*根据手机号码查询账户*/
    User findByPhone(String phone);
    /*根据分组id查询出分组下的用户*/
    List<User> findFriendByGsId(String gsId);
    /*根据群聊id查询出群聊下的用户*/
    List<User> findUsersByGcId(String gcId);
    /*根据用户名模糊查询账号*/
    List<User> findFuzzyByName(String username);
    /*根据dsqId模糊查询账号*/
    List<User> findFuzzyByDsqId(String dsqId);
    /*修改用户信息*/
    void updateUser(User user);
}
