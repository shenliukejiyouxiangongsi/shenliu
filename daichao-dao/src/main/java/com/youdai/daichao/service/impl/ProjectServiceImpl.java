package com.youdai.daichao.service.impl;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.Project;
import com.youdai.daichao.mapper.ProjectMapper;
import com.youdai.daichao.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> selectAllList(Project project) {
        return projectMapper.selectAllList(project);
    }
}
