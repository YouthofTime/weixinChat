package cn.itcast.dao;

import cn.itcast.domain.GroupSplit;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupSplitDao {
    @Insert("insert into groupsplit(gsId,belongId,gsname) values(#{gsId},#{belongId},#{gsname})")
    void saveGroupSplit(GroupSplit groupSplit);

    @Delete("delete from groupsplit where gsId=#{gsId}")
    void deleteByGsId(String gsId);

    @Select("select * from groupsplit where belongId=#{belongId}")
    List<GroupSplit> findByUid(int belongId);

    @Select("select count(*) from groupsplit where belongId=#{uid}")
    int findCountByUid(int uid);

    @Update("update groupsplit set gsname=#{gsname} where gsId=#{gsId}")
    void updateGroupSplit(@Param("gsname")String gsname,@Param("gsId")String gsId);

}
