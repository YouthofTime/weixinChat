package cn.itcast.service;

import cn.itcast.domain.GroupSplit;
import cn.itcast.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupSplitService {
    /*添加分组*/
    void saveGroupSplit(GroupSplit groupSplit);
    /*查询用户拥有的分组数*/
    int findCountByUid(int uid);
    /*删除分组*/
    void deleteByGsId(String gsId);
    /*修改分组*/
    void updateGroupSplit(String gsname, String gsId);
    /*查询用户拥有的分组*/
    List<GroupSplit> findSplits(int uid);
}
