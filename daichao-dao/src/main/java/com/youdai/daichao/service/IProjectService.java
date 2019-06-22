package com.youdai.daichao.service;

import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.domain.Project;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IProjectService extends IService<Project> {

    List<Project> selectAllList(Project project);
}
