package com.youdai.daichao.service;

import com.youdai.daichao.domain.Shell;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjiabin
 * @since 2019-04-04
 */
public interface IShellService extends IService<Shell> {
    int deleteShellByIds(String ids);
}
