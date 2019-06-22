package com.youdai.daichao.service;

import com.youdai.daichao.domain.UserRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户来源记录表 服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-06-04
 */
public interface IUserRecordService extends IService<UserRecord> {
    List<UserRecord> selectUserRecords(Map<String,String > map);
}
