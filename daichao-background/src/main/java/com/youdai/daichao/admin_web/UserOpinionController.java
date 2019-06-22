package com.youdai.daichao.admin_web;

import com.youdai.daichao.common.utils.poi.ExcelUtil;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.UserOpinion;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.service.UserOpinionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/biz/userOpinion")
public class UserOpinionController extends BaseController
{
    private String prefix = "biz/user";

    @Autowired
    private UserOpinionService userOpinionService;

    @RequiresPermissions("biz:userOpinion:view")
    @GetMapping()
    public String userOpinion()
    {
        return prefix + "/userOpinion";
    }

    /**
     * 查询用户反馈列表
     */
    @PostMapping("/list")
    @ResponseBody
    @Log(title = "用户反馈", businessType = BusinessType.LIST)
    public TableDataInfo list(UserOpinion userOpinion)
    {
        startPage();
        List<UserOpinion> list = userOpinionService.selectUserOpinionList(userOpinion);
        return getDataTable(list);
    }


    /**
     * 导出用户反馈列表
     */
    @RequiresPermissions("biz:userOpinion:export")
    @PostMapping("/export")
    @Log(title = "用户反馈", businessType = BusinessType.EXPORT)
    @ResponseBody
    public AjaxResult export(UserOpinion userOpinion)
    {
        List<UserOpinion> list = userOpinionService.selectUserOpinionList(userOpinion);
        ExcelUtil<UserOpinion> util = new ExcelUtil<UserOpinion>(UserOpinion.class);
        return util.exportExcel(list, "userOpinion");
    }

    /**
     * 新增用户反馈
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户反馈
     */
    @RequiresPermissions("biz:userOpinion:add")
    @Log(title = "用户反馈", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserOpinion userOpinion)
    {
        return toAjax(userOpinionService.insert(userOpinion));
    }

    /**
     * 修改用户反馈
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        UserOpinion userOpinion = userOpinionService.selectById(id);
        mmap.put("userOpinion", userOpinion);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户反馈
     */
    @RequiresPermissions("biz:userOpinion:edit")
    @Log(title = "用户反馈", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserOpinion userOpinion)
    {
        return toAjax(userOpinionService.updateById(userOpinion));
    }

    /**
     * 删除用户反馈
     */
    @RequiresPermissions("biz:userOpinion:remove")
    @Log(title = "用户反馈", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(userOpinionService.deleteUserOpinionByIds(ids));
    }

}