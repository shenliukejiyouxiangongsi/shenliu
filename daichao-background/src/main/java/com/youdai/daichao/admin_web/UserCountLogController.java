package com.youdai.daichao.admin_web;


import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.domain.UserCountLog;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IUserCountLogService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 注册数据统计
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/statistic")
public class UserCountLogController extends BaseController {
	
	@Autowired
	private IUserCountLogService userCountLogService;
	
	
	private String prefix="biz/statistic";
	/**
	 * 页面显示
	 */
	@RequiresPermissions("biz:userCountLog:view")
	@GetMapping("/registerCount")
    public String main()
    {
        return prefix+"/register_count";
    }
	
	/**
	 * 数据显示
	 */
	@RequiresPermissions("biz:userCountLog:view")
	@Log(title = "注册数据查询",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(@RequestParam Map<String, Object> map){
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("startTime", startTime);
//		map.put("endTime",endTime );
		startPage();
		List<UserCountLog> li = userCountLogService.selectUserCountList(map);
		return getDataTable(li);
	}
}
