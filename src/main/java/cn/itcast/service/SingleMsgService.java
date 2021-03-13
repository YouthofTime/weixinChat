package cn.itcast.service;

import cn.itcast.domain.SingleMsg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SingleMsgService {
    /*根据俩个用户的id查询出他们的最后一条消息记录*/
    SingleMsg findLastSingleMsg(int sendUid,int receiveUid);
    /*保存消息*/
    void saveSingleMsg(SingleMsg singleMsg);
    /*根据sendUid和receiveUid查询出俩人得聊天记录*/
    List<SingleMsg> findByUser(int uid1,int uid2);
    /*查询在一个时间点之后的消息*/
    List<SingleMsg> findAfterTime(@Param("sendUid")int sendUid,@Param("receiveUid")int receiveUid,@Param("time") Date time);
}
