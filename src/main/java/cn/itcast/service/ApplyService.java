package cn.itcast.service;

import cn.itcast.domain.Apply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyService {
    void addApply(Apply apply);
    List<Apply> findAll(String receiveId);
    void deleteById(int applyId);
    Apply findBySRId(String sendUid,String receiveId);
    void updateById(int applyId);
}
