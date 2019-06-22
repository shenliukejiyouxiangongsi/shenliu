package com.youdai.daichao.admin_web;

import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IProductCountLogService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 落地页统计
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/productCount")
public class ProductCountLogController  extends BaseController {
	
	@Autowired
	private IProductCountLogService productCountLogService;
	
	private final String prefix ="biz/product";

	@RequiresPermissions("biz:productCount:view")
	@GetMapping()
	public String productCountLog(){
		return prefix +"/product_count";
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("biz:productCount:view")
	@Log(title = "产品统计查询",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(@RequestParam Map<String, Object> map){
		startPage();
		return getDataTable(productCountLogService.selectProdctCountList(map));
	}
	
}
