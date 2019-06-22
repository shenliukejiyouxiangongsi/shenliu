package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.UserRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户来源记录表 Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-06-04
 */
public interface UserRecordMapper extends BaseMapper<UserRecord> {
    List<UserRecord> selectUserRecords(Map<String,String > map);
}
