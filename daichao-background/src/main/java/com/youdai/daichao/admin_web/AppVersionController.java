package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.AppVersion;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IAppVersionService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author  wangjiabin
 */

@Controller
@RequestMapping("/appVersion")
public class AppVersionController  extends BaseController {

    @Autowired
    private IAppVersionService appVersionService;


    private final String prefix="biz/project/appVersion";


    @RequiresPermissions("biz:appVersion:view")
    @GetMapping()
    public String appVersion(){
        return  prefix + "/appVersion";
    }

    /**
     * 列表数据
     */
    @RequiresPermissions("biz:appVersion:view")
    @Log(title = "App版本查看",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public  TableDataInfo   list(AppVersion appVersion){
        startPage();

        return getDataTable(appVersionService.selectAppVersionList(appVersion));
    }

    /**
     * 版本添加
     * @return
     */
    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }


    /**
     * 新增保存
     * @param appVersion
     * @return
     */
    @RequiresPermissions("biz:appVersion:add")
    @Log(title = "App版本添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AppVersion appVersion){
        appVersion.setCreatePeople(getSysUser().getLoginName());
        return  toAjax( appVersionService.insertAppVerion(appVersion));
    }

    /**
     * 修改也
     * @param id
     * @param mmap
     * @return
     */
    @GetMapping("/edit/{id}")
    public  String  edit(@PathVariable("id") int id, ModelMap mmap){
        EntityWrapper entityWrapper =new EntityWrapper<AppVersion>();
        entityWrapper.eq("id",id);
        mmap.put("appVersion",appVersionService.selectOne(entityWrapper)) ;
        return  prefix+"/edit";
    }


    /**
     * 修改保存
     * @param appVersion
     * @return
     */
    @RequiresPermissions("biz:appVersion:edit")
    @Log(title = "App版本修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AppVersion appVersion){
        appVersion.setUpdatePeople(getSysUser().getLoginName());
        return toAjax( appVersionService.updateAppVerionById(appVersion));
    }
}
