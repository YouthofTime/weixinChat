package cn.itcast.dao;

import cn.itcast.domain.GroupChatUser;
import cn.itcast.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface GroupChatUserDao {
    @Insert("insert into groupchatuser(gcId,userid,enterTime) values(#{gcId},#{userid},NOW())")
    void saveGroupChatUser(GroupChatUser groupChatUser);

    @Delete("delete from groupchatuser where gcId=#{gcId}")
    void deleteByGcid(String gcId);

    @Delete("delete from groupchatuser where gcId=#{gcId} and userid=#{uid}")
    void deleteByGcIdUid(@Param("gcId") String gcId, @Param("uid") int uid);

    @Select("select gcId from groupchatuser where userid=#{uid}")
    List<String> findByUid(int uid);

    @Select("select userid from groupchatuser where gcId=#{gcId}")
    List<Integer> findByGcid(String gcId);

    @Select("select * from groupchatuser where gcId=#{gcId}")
    List<GroupChatUser> findGcUsers(String gcId);

    @Update("update groupchatuser set leaveTime=NOW() where gcId=#{gcId} and userid=#{uid}")
    void updateByGcIdUid(@Param("gcId") String gcId, @Param("uid") int uid);

    @Select("select leaveTime from groupchatuser where gcId=#{gcId} and userid=#{uid}")
    Date findLeaveTime(@Param("gcId") String gcId, @Param("uid") int uid);
}
