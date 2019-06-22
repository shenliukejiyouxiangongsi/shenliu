package com.youdai.daichao.admin_web;



import com.alibaba.fastjson.JSONObject;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.page.PageDomain;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.framework.web.page.TableSupport;
import com.youdai.daichao.util.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 订单 信息操作处理
 * 
 * @author zhankui
 * @date 2019-05-28
 */
@Controller
@RequestMapping("/orders")
@Slf4j
public class OrdersController extends BaseController
{
    private String prefix = "biz/merchant/orders";
	
	@Value("${orderUrl}")
    private String orderUrl;


	@RequiresPermissions("biz:orders:view")
	@GetMapping()
	public String orders(ModelMap map)
	{
		map.put("channel","daichao");
		map.put("orderUrl",orderUrl);
	    return prefix + "/orders_list";
	}

	/**
     * 获取检测记录
	 * @param channel
     * @param status
     * @param idName
	 * @param phoneNo
	 * @param createTime
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("biz:orders:view")
	@Log(title = "检测记录查询",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(String channel, String status, String idName, String phoneNo, String createTime,
							  String createTimeStart, String createTimeEnd, String channelPhoneNo) throws Exception{

		System.out.println(channelPhoneNo);
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();

		Map<String,Object> map=new HashMap<>();

		map.put("channel",channel);
		map.put("status",status);
		map.put("idName",idName);
		map.put("phoneNo",phoneNo);
		map.put("createTime",createTime);
		map.put("createTimeStart",createTimeStart);
		map.put("createTimeEnd",createTimeEnd);
		map.put("channelPhoneNo",channelPhoneNo);
		map.put("pageNum",pageNum);
		map.put("pageSize",pageSize);




		JSONObject jsonObject=new JSONObject(map);
		String params= jsonObject.toJSONString();

		String url=orderUrl+"/api/riskModule/reports";

		log.info("请求url：{},传入参数：{}",url,params);
		String result= HttpClientUtil.postParameters(url,params);

		TableDataInfo data=JSONObject.parseObject(result,TableDataInfo.class);
		log.info("状态码：{},获取数据条数：{}",data.getCode(),data.getTotal());
		return  data;
	}


	/**
     * 查看报告详情
	 * @param reportNumber
     * @return
     * @throws Exception
	 */
	@RequiresPermissions("biz:orders:detail")
	@Log(title = "详情查看",businessType = BusinessType.LIST )
	@GetMapping("/detail/{reportNumber}")
	public String showDetail(@PathVariable String reportNumber,ModelMap mmap) throws  Exception{
		Map<String ,Object> map=new HashMap<>();
		map.put("reportNumber",reportNumber);
		String url=orderUrl+"/api/riskModule/selectReport";
		log.info("请求地址：{},报告编号：{}",url,reportNumber);

		String result= HttpClientUtil.postSSLParameters(url,map);
		mmap.put("result",result);
		return  prefix +"/detail";
	}
}
