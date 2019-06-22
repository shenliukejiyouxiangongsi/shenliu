package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.Shell;
import com.youdai.daichao.mapper.ShellMapper;
import com.youdai.daichao.service.IShellService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangjiabin
 * @since 2019-04-04
 */
@Service
public class ShellServiceImpl extends ServiceImpl<ShellMapper, Shell> implements IShellService {

    @Autowired
    private ShellMapper shellMapper;

    @Override
    public int deleteShellByIds(String ids) {
        String[] idss=ids.split(",");
        return shellMapper.deleteShellByIds(idss);
    }
}
