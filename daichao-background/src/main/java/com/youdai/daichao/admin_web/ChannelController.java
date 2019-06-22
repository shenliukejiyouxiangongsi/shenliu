package com.youdai.daichao.admin_web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.utils.poi.ExcelUtil;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.shiro.service.PasswordService;
import com.youdai.daichao.service.IChannelService;

import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道商 信息操作处理
 * 
 * @author zhuliangjie
 * @date 2019-01-26
 */
@Controller
@RequestMapping("/channel")
public class ChannelController extends BaseController
{
    private String prefix = "biz/channel";
	
	@Autowired
	private IChannelService channelService;

	@Autowired
	PasswordService passwordService;



	@RequiresPermissions("biz:channel:view")
	@GetMapping()
	public String channel()
	{
	    return prefix + "/channel";
	}
	
	/**
	 * 查询渠道商列表
	 */
	@RequiresPermissions("biz:channel:view")
	@Log(title = "渠道查询",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Channel channel)
	{
		startPage();
        List<Channel> list = channelService.selectChannelList(channel);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出渠道商列表
	 */
	@RequiresPermissions("biz:channel:export")
	@Log(title = "渠道导出",businessType = BusinessType.EXPORT )
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Channel channel)
    {
    	List<Channel> list = channelService.selectChannelList(channel);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        return util.exportExcel(list, "channel");
    }
	
	/**
	 * 新增渠道商
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	@Value("${system.domain.channalLinkBase}")
	private String channalLinkBase;


	private boolean isExistLoginName(Channel channel){
		boolean f=false;
		EntityWrapper wrapper=new EntityWrapper<Channel>();
		wrapper.eq("c_loginname",channel.getCLoginname());
		wrapper.eq("status",1);
		int i=channelService.selectCount(wrapper);
		if(i>0){
			f=true ;
		}
		return  f;
	}



	/**
	 * 新增保存渠道商
	 */
	@RequiresPermissions("biz:channel:add")
	@Log(title = "渠道添加",businessType = BusinessType.INSERT )
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Channel channel)
	{
		if(isExistLoginName(channel)){
			return error("渠道登入名已存在，请重新输入");
		}

		channel.setCPassword(passwordService.encryptPassword(channel.getCLoginname(), channel.getCPassword()));
		channel.setStatus(1);
		channel.setCUrl(channalLinkBase+channel.getCLoginname());
		channel.setCreateUser(getSysUser().getLoginName());
		return toAjax(channelService.insert(channel));
	}

	/**
	 * 修改渠道商
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Channel channel = channelService.selectById(id);
		mmap.put("channel", channel);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存渠道商
	 */
	@RequiresPermissions("biz:channel:edit")
	@Log(title = "渠道修改",businessType = BusinessType.UPDATE )
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Channel channel)
	{
		Channel fdChannel =channelService.selectById(channel.getChannelId());
		if(!fdChannel.getCLoginname().equals(channel.getCLoginname())){
			if(isExistLoginName(channel)){
				return error("渠道登入名已存在，请重新输入");
			}
		}
		channel.setCUrl(channalLinkBase + channel.getCLoginname());
		return toAjax(channelService.updateChannel(channel));
	}
	
	/**
	 * 删除渠道商
	 */
	@RequiresPermissions("biz:channel:remove")
	@Log(title = "渠道删除",businessType = BusinessType.DELETE )
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(channelService.deleteChannelByIds(ids));
	}

	
	/**
	 * 修改状态
	 * @return
	 */
	@RequiresPermissions("biz:channel:change")
	@Log(title = "渠道修改",businessType = BusinessType.UPDATE )
	@PostMapping("/change")
	@ResponseBody
	public AjaxResult change(Channel channel){
		return toAjax(channelService.changeStatus(channel));
	}


	@GetMapping("/resetPwd/{id}")
	public String resetPwd(@PathVariable("id") Long id, ModelMap mmap)
	{
		mmap.put("channel", channelService.selectById(id));
		return prefix + "/resetPwd";
	}


	@RequiresPermissions("biz:channel:change")
	@Log(title = "渠道修改",businessType = BusinessType.UPDATE )
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(Long id, String password)
	{
		Channel channel = channelService.selectById(id);
		channel.setCPassword(passwordService.encryptPassword(channel.getCLoginname(), password));
		return toAjax(channelService.updateById(channel));
	}

	
	

}
