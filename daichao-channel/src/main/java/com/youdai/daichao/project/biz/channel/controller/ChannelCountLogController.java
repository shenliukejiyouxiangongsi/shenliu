package com.youdai.daichao.project.biz.channel.controller;

import com.youdai.daichao.common.controller.BaseController;

import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.project.biz.channel.domain.ChannelCountLog;
import com.youdai.daichao.project.biz.channel.service.IChannelCountLogService;
import com.youdai.daichao.project.system.user.domain.SysUser;
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
public class ChannelCountLogController extends BaseController {

	
	@Autowired
	private IChannelCountLogService channelCountLogService;


	


	/**
	 * 渠道统计列表
	 * @return
	 */
	@GetMapping()
	public String channelCountLog(){

		return "channel_statistic";
	}
	
	/**、
	 * 数据显示
	 */
	
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(@RequestParam Map<String, Object> map){

		startPage();
		SysUser user=getSysUser();
		map.put("cLoginname",user.getUserName());
		List<ChannelCountLog> li = channelCountLogService.selectChannelCountList(map);
		return getDataTable(li);
	}


}
