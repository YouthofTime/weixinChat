package cn.itcast.dao;

import cn.itcast.domain.GroupChat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.LinkedList;
import java.util.List;

public interface GroupChatDao {
    @Insert("insert into groupchat(gcId,gcname,buildtime,belongId,headImg) values(#{gcId},#{gcname},#{buildtime},#{belongId},#{headImg})")
    void saveGroupChat(GroupChat groupChat);

    @Select("select * from groupchat where belongId=#{uid}")
    @Results(id="groupchatMap",value = {
            @Result(id=true,column = "gcId",property = "gcId"),
            @Result(column = "gcname",property = "gcname"),
            @Result(column = "buildtime",property = "buildtime"),
            @Result(column = "belongId",property = "belongId"),
            @Result(column = "headImg",property = "headImg"),
            @Result(property = "groupMsgs",column = "gcId",
            many = @Many(select = "cn.itcast.dao.GroupMsgDao.findByGcId",fetchType = FetchType.EAGER))
    })
    LinkedList<GroupChat> findByUid(int uid);

    @Update("update groupchat set gcname=#{gcname},headImg=#{headImg} where gcId=#{gcId}")
    void updateGroupChat(GroupChat groupChat);

    @Delete("delete from groupchat where gcId=#{gcId}")
    void deleteByGcid(String gcId);

    @ResultMap("groupchatMap")
    @Select("select * from groupchat where gcId=#{gcId}")
    GroupChat findByGcid(String gcId);

    @Select("select count(*) from groupchat where belongId=#{uid}")
    int findCountByUid(int uid);

    @Select("select * from groupchat where gcname=#{gcname}")
    List<GroupChat> findByName(String gcname);
}
