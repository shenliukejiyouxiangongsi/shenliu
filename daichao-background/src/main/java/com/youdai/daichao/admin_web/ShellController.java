package com.youdai.daichao.admin_web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.Shell;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IShellService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhankui
 * @since 2019-04-04
 */
@Controller
@RequestMapping("/shell")
public class ShellController extends BaseController {

    @Autowired
    private IShellService shellService;

    private String prefix="biz/project/shell";

    @RequiresPermissions("biz:shell:view")
    @GetMapping()
    public String shell(){
        return prefix +"/shell";
    }


    @RequiresPermissions("biz:shell:view")
    @Log(title = "APP壳查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shell shell){
        EntityWrapper wrapper=new EntityWrapper<Shell>();
        return getDataTable(shellService.selectList(wrapper));
    }



    @GetMapping("/add")
    public String add(){
        return  prefix +"/add";
    }


    @RequiresPermissions("biz:shell:add")
    @Log(title = "APP壳添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Shell shell){
        return toAjax(shellService.insert(shell));
    }

    @GetMapping("/edit/{shellId}")
    public String edit(@PathVariable Integer shellId, ModelMap maap){
        Shell shell=shellService.selectById(shellId);
        maap.put("shell",shell);
        return  prefix +"/edit";
    }


    @RequiresPermissions("biz:shell:edit")
    @Log(title = "APP壳修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Shell shell){
        return toAjax(shellService.updateById(shell));
    }


    @RequiresPermissions("biz:shell:remove")
    @Log(title = "APP壳删除",businessType = BusinessType.DELETE )
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        return toAjax(shellService.deleteShellByIds(ids));
    }
}

