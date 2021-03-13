package cn.itcast.dao;

import cn.itcast.domain.GroupSplitUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupSplitUserDao {
    @Insert("insert into groupsplituser(gsId,userid,remark,leaveTime) values(#{gsId},#{userid},#{remark},NOW())")
    void saveGroupChatUser(GroupSplitUser groupSplitUser);

    @Delete("delete from groupsplituser where gsId=#{gsId}")
    void deleteByGsid(String gsId);

    @Delete("delete from groupsplituser where gsId=#{gsId} and userid=#{uid}")
    void deleteByGcIdUid(@Param("gsId") String gsId, @Param("uid") int uid);

    @Select("select gsId from groupsplituser where userid=#{uid}")
    List<String> findByUid(int uid);

    @Select("select * from groupsplituser where gsId=#{gsId}")
    List<GroupSplitUser> findByGsid(String gsId);

    @Select("select * from groupsplituser where gsId=#{gsId} and userid=#{uid}")
    GroupSplitUser findByUidAndGsId(@Param("gsId") String gsId, @Param("uid") int uid);

    @Update("update groupsplituser set remark=#{remark} where gsId=#{gsId} and userid=#{userid}")
    void updateFriend(@Param("gsId") String gsId,@Param("userid") int userid,@Param("remark") String remark);

    @Update("update groupsplituser set leaveTime=NOW() where gsId=#{gsId} and userid=#{userid}")
    void updateLeaveTime(@Param("gsId") String gsId,@Param("userid") int userid);
}
