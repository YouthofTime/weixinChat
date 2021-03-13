package cn.itcast.dao;

import cn.itcast.domain.GroupMsg;
import cn.itcast.domain.SingleMsg;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;

public interface SingleMsgDao {
    @Insert("insert into singlemsg(time,value,sendUid,receiveUid) values(#{time},#{value},#{sendUid},#{receiveUid})")
    void saveSingleMsg(SingleMsg singleMsg);

    @Results(id="singleMsgMap",value = {
            @Result(id=true,column ="singleId",property = "singleId"),
            @Result(column = "time",property = "time"),
            @Result(column = "value",property = "value"),
            @Result(column = "sendUid",property = "sendUid"),
            @Result(column = "receiveUid",property = "receiveUid"),
            @Result(property = "sendUser",column = "sendUid",
            one = @One(select = "cn.itcast.dao.UserDao.findUserById",fetchType = FetchType.EAGER))
    })
    @Select("select * from singlemsg where sendUid=#{sendUid} and receiveUid=#{receiveUid} or sendUid=#{receiveUid} and receiveUid=#{sendUid}")
    List<SingleMsg> findByUser(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid);

    @ResultMap("singleMsgMap")
    @Select("select * from singlemsg where time between #{startTime} and #{endTime} and sendUid=#{sendUid} and receiveUid=#{receiveUid}")
    List<SingleMsg> findByUserTime(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    @ResultMap("singleMsgMap")
    @Select("select * from singlemsg where time>#{time} and sendUid=#{sendUid} and receiveUid=#{receiveUid}")
    List<SingleMsg> findAfterTime(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid,@Param("time") Date time);

    @ResultMap("singleMsgMap")
    @Select("select * from singlemsg where singleId=#{singleId}")
    SingleMsg findBySingleId(int singleId);

    @Select("select count(*) from singlemsg where sendUid=#{sendUid} and receiveUid=#{receiveUid}")
    int findCountByUid(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid);

    @ResultMap("singleMsgMap")
    @Select("select * from singlemsg where sendUid=#{sendUid} and receiveUid=#{receiveUid} order by time limit #{end},#{end1} ")
    SingleMsg findLastSingleMsg(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid,@Param("end") int end,@Param("end1")int end1);
}
