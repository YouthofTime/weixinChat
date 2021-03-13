package cn.itcast.dao;

import cn.itcast.domain.Apply;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ApplyDao {
    @Insert("insert into apply(username,textConfirm,sendUid,receiveId,remark,gsId,isApprove,headImg) value(#{username},#{textConfirm},#{sendUid},#{receiveId},#{remark},#{gsId},#{isApprove},#{headImg})")
    void addApply(Apply apply);

    @Select("select * from apply where receiveId=#{receiveId}")
    List<Apply> findAll(String receiveId);

    @Delete("delete from apply where applyId=#{applyId}")
    void deleteById(int applyId);

    @Select("select * from apply where receiveId=#{receiveId} and sendUid=#{sendUid}")
    Apply findBySRId(@Param("sendUid") String sendUid,@Param("receiveId") String receiveId);

    @Update("update apply set isApprove=1 where applyId=#{applyId}")
    void updateById(int applyId);
}
