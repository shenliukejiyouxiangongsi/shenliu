package com.youdai.daichao.admin_web;

import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.domain.Channel;
import com.youdai.daichao.domain.ChannelCountLog;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.service.IChannelCountLogService;
import com.youdai.daichao.service.IChannelService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 渠道数据统计
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/channelCountLog")
public class ChannelCountLogController  extends BaseController {

	
	@Autowired
	private IChannelCountLogService channelCountLogService;

	@Autowired
	private IChannelService channelService;
	
	private final String prefix="biz/channel";

	/**
	 * 渠道统计列表
	 * @return
	 */
	@RequiresPermissions("biz:channelCountLog:view")
	@GetMapping()
	public String channelCountLog(ModelMap mmap){
		List<Channel> channels =channelService.selectAllChannel();
		mmap.put("channels",channels);
		return prefix+"/channel_statistic";
	}
	
	/**、
	 * 数据显示
	 */
	@RequiresPermissions("biz:channelCountLog:view")
	@Log(title = "渠道统计",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(@RequestParam Map<String, Object> map){
		Subject subject = SecurityUtils.getSubject();
		Object ob = subject.getPrincipal();
		startPage();
		List<ChannelCountLog> li = channelCountLogService.selectChannelCountList(map);
		return getDataTable(li);
	}


}
