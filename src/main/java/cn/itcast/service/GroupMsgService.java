package cn.itcast.service;

import cn.itcast.domain.GroupMsg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GroupMsgService {
    /*根据群聊id查询群聊的最后一条消息*/
    GroupMsg findLastGroupMsg(String gcId);
    /*新增消息*/
    void saveGroupMsg(GroupMsg groupMsg);
    /*查询在一个时间点之后的消息*/
    List<GroupMsg> findAfterTime(Date time,String gcId);
    /*删除群聊天记录*/
    void deleteByGcId(String gcId);
}
