package cn.itcast.dao;

import cn.itcast.domain.GroupMsg;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;

public interface GroupMsgDao {
    @Insert("insert into groupmsg(time,value,sendUid,receiveGcid) values(#{time},#{value},#{sendUid},#{receiveGcid})")
    void saveGroupMsg(GroupMsg groupMsg);

    @Results(id="groupMsgMap",value = {
            @Result(id=true,property = "gmId",column = "gmId"),
            @Result(property = "time",column = "time"),
            @Result(property = "value",column = "value"),
            @Result(property = "sendUid",column = "sendUid"),
            @Result(property = "receiveGcid",column = "receiveGcid"),
            @Result(property = "sendUser",column = "sendUid",
                    one = @One(select = "cn.itcast.dao.UserDao.findUserById",fetchType = FetchType.EAGER))
    })
    @Select("select * from groupmsg where receiveGcid=#{gcId} order by time")
    List<GroupMsg> findByGcId(String gcId);

    @ResultMap("groupMsgMap")
    @Select("select * from groupmsg where receiveGcid=#{gcId} and time between #{start} and #{end}")
    List<GroupMsg> findByGcTime(@Param("gcId")String gcId,@Param("start") Date start,@Param("end")Date end);

    @ResultMap("groupMsgMap")
    @Select("select * from groupmsg where time>#{time} and receiveGcid=#{receiveGcid}")
    List<GroupMsg> findAfterTime(@Param("time")Date time,@Param("receiveGcid")String gcId);

    @ResultMap("groupMsgMap")
    @Select("select * from groupmsg where gmId=#{gmId}")
    GroupMsg findByGmId(int gmId);

    @Select("select count(*) from groupmsg where receiveGcid=#{gcId}")
    int findCountByGcId(String gcId);

    @ResultMap("groupMsgMap")
    @Select("select * from groupmsg where receiveGcid=#{gcId} order by time limit #{end},#{end1} ")
    GroupMsg findLastGroupMsg(@Param("gcId") String gcId,@Param("end") int end,@Param("end1")int end1);

    @Delete("delete from groupmsg where receiveGcid=#{gcId}")
    void deleteByGcId(String gcId);
}
