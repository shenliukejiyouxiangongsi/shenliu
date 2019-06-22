package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.Project;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IProjectService;
import com.youdai.daichao.util.StringUtil;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/13
 * @Description
 */
@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController extends BaseController {
	

    private String prefix = "biz/project";

    @Autowired
    IProjectService projectService;


    @RequiresPermissions("biz:project:view")
    @GetMapping()
    public String channel()
    {
        return prefix + "/project";
    }


    /**
     * 查询产品列表
     */
    @RequiresPermissions("biz:project:view")
    @Log(title = "项目查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Project project)
    {
        startPage();
        List<Project> list = projectService.selectAllList(project);
        return getDataTable(list);
    }
    /**
     * 产品添加
     */
    @GetMapping("/add")
    public String add(){
    	return prefix+"/add";
    }
    
    /**
     * 产品保存添加
     */
    @RequiresPermissions("biz:project:add")
    @Log(title = "项目添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Project project){
        project.setProKey(StringUtil.getStringRandom(12));
    	return toAjax(projectService.insert(project));
    }
    
    /**
     * 产品修改
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,ModelMap map){
    	EntityWrapper<Project> entityWrapper = new EntityWrapper<Project>();
    	entityWrapper.eq("pro_id", id);
        Project project = projectService.selectOne(entityWrapper);
    	map.put("project", project);
    	return prefix+ "/edit";
    }
    
    /**
     * 产品保存修改
     */
    @RequiresPermissions("biz:project:edit")
    @Log(title = "项目修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Project project)
    {
        return toAjax(projectService.updateById(project));

    }


    

}
