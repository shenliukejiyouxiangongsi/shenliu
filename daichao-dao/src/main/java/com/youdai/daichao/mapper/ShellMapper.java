package com.youdai.daichao.mapper;

import com.youdai.daichao.domain.Shell;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjiabin
 * @since 2019-04-04
 */
public interface ShellMapper extends BaseMapper<Shell> {
    int deleteShellByIds(String[] ids);
}
