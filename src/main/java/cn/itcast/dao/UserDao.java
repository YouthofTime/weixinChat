package cn.itcast.dao;

import cn.itcast.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserDao {
    @Results(id="userMap",value = {
            @Result(id=true,column ="id",property = "id"),
            @Result(column = "phone",property = "phone"),
            @Result(column = "password",property = "password"),
            @Result(column = "username",property = "username"),
            @Result(column = "bind_email",property = "bind_email"),
            @Result(column = "address",property = "address"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "dsqId",property = "dsqId"),
            @Result(column = "headImg",property = "headImg"),
            @Result(property = "groupChats",column = "id",
                    many = @Many(select = "cn.itcast.dao.GroupChatDao.findByUid",fetchType = FetchType.EAGER)),
            @Result(property = "groupSplits",column = "id",
                    many = @Many(select = "cn.itcast.dao.GroupSplitDao.findByUid",fetchType = FetchType.EAGER))
    })
    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id=#{id}")
    User findUserById(int id);

    @ResultMap("userMap")
    @Select("select * from user where id=#{id}")
    User findByid(int id);

    @Insert("insert into user(phone,password,username,bind_email,dsqId,headImg,sex,address) values(#{phone},#{password},#{username},#{bind_email},#{dsqId},#{headImg},#{sex},#{address})")
    void saveUser(User user);

    @Update("update user set phone=#{phone},password=#{password},username=#{username},bind_email=#{bind_email},address=#{address},sex=#{sex},dsqId=#{dsqId},headImg=#{headImg} where id=#{id}")
    void updateUser(User user);

    @ResultMap("userMap")
    @Select("select * from user where username=#{username}")
    List<User> findByName(String username);

    @ResultMap("userMap")
    @Select("select * from user where username like #{username}")
    List<User> findFuzzyByName(String username);

    @ResultMap("userMap")
    @Select("select * from user where dsqId like #{dsqId}")
    List<User> findFuzzyByDsqId(String dsqId);

    @ResultMap("userMap")
    @Select("select * from user where dsqId=#{dsqId}")
    User findByDsqId(String dsqId);

    @ResultMap("userMap")
    @Select("select * from user where bind_email=#{bind_email}")
    User findByBindEmail(String bind_email);

    @ResultMap("userMap")
    @Select("select * from user where phone=#{phone}")
    User findByPhone(String phone);
}
