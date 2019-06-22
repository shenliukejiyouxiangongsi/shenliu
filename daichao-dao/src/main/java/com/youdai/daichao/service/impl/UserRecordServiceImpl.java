package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.mapper.UserRecordMapper;
import com.youdai.daichao.service.IUserRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户来源记录表 服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-06-04
 */
@Service
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecord> implements IUserRecordService {

    @Autowired
    private  UserRecordMapper userRecordMapper;

    @Override
    public List<UserRecord> selectUserRecords(Map<String,String > map) {
        return userRecordMapper.selectUserRecords(map);
    }
}
