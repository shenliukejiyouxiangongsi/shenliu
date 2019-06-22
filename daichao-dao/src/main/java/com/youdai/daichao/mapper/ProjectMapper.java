package com.youdai.daichao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youdai.daichao.domain.Project;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface ProjectMapper extends BaseMapper<Project> {

    List<Project>  selectAllList(Project project);
}
