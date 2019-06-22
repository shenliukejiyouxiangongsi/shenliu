package com.youdai.daichao.service;

import com.youdai.daichao.domain.Merchant;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-18
 */
public interface IMerchantService extends IService<Merchant> {
    int changeStatus(Long id, Integer setStatus);
}
