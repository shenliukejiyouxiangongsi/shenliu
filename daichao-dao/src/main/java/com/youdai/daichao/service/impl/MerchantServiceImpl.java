package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.Merchant;
import com.youdai.daichao.mapper.MerchantMapper;
import com.youdai.daichao.service.IMerchantService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-18
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;


    @Override
    public int changeStatus(Long id,Integer setStatus) {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("setStatus",setStatus);
        return merchantMapper.changeStatus(map);
    }
}
