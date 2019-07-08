package com.youdai.daichao.admin_web;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.utils.poi.ExcelUtil;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.service.IAppUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户 信息操作处理
 * 
 * @author zhankui
 * @date 2019-07-05
 */
@Controller
@RequestMapping("/biz/appUser")
public class AppUserController extends BaseController
{
    private String prefix = "biz/appUser";
	
	@Autowired
	private IAppUserService appUserService;
	
	@RequiresPermissions("biz:appUser:view")
	@GetMapping()
	public String appUser()
	{
	    return prefix + "/appUser";
	}
	
	/**
	 * 查询用户列表
	 */
	@PostMapping("/list")
	@ResponseBody
	@Log(title = "用户", businessType = BusinessType.LIST)
	public TableDataInfo list(AppUser appUser)
	{
		startPage();
        List<AppUser> list = appUserService.selectUserAndChannelNameList(appUser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出用户列表
	 */
	@RequiresPermissions("biz:appUser:export")
    @PostMapping("/export")
	@Log(title = "用户", businessType = BusinessType.EXPORT)
    @ResponseBody
    public AjaxResult export(AppUser appUser)
    {
		Wrapper wrapper = new EntityWrapper(appUser);
    	List<AppUser> list = appUserService.selectList(wrapper);
        ExcelUtil<AppUser> util = new ExcelUtil<AppUser>(AppUser.class);
        return util.exportExcel(list, "appUser");
    }
	
	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("biz:appUser:add")
	@Log(title = "用户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AppUser appUser)
	{		
		return toAjax(appUserService.insert(appUser));
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{aUid}")
	public String edit(@PathVariable("aUid") Long aUid, ModelMap mmap)
	{
		AppUser appUser = appUserService.selectById(aUid);
		mmap.put("appUser", appUser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("biz:appUser:edit")
	@Log(title = "用户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AppUser appUser)
	{		
		return toAjax(appUserService.updateById(appUser));
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("biz:appUser:remove")
	@Log(title = "用户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(appUserService.deleteBatchIds(Arrays.asList(ids.split(","))));
	}
	
}
