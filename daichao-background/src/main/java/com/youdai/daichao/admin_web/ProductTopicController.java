package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.enums.ChannelStatusEnum;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.domain.Recommend;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.service.IProductService;
import com.youdai.daichao.service.IRecommendService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品推荐
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/product/topic")
public class ProductTopicController extends BaseController {

	@Autowired
	private IRecommendService recommendService;
	
	@Autowired
	private IProductService productService;
	
	private final String  prefix="biz/product/topic";


	@RequiresPermissions("biz:topic:view")
	@GetMapping()
	public String recommend(ModelMap mmap){
        mmap.put("rtypes",recommendService.selectTopic(null));
		return prefix+"/recommend_list";	
	}


	@RequiresPermissions("biz:topic:view")
	@Log(title = "产品主题查询",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(@RequestParam String rType){
		startPage();
		return getDataTable(recommendService.selectTopic(rType));
	}
	
	/**
	 * 新增产品主题
	 */
	@GetMapping("/add")
	public String add( ModelMap mmap){
		mmap.put("products", productService.selectPdsNotInRec());
		return prefix +"/add";
	}


	/**
	 * 检查该类型产品推荐是否添加
	 * @param recommend
	 * @return 1：标示已存在 返回success（） code 0 0：不存在 返回error（） code 500
	 */
	@PostMapping("/checkRecommend")
	@ResponseBody
	public AjaxResult checkRecommend(Recommend recommend){
		EntityWrapper entityWrapper =new EntityWrapper<Recommend>();
		entityWrapper.eq("p_id",recommend.getPId());
		entityWrapper.eq("r_type",recommend.getRType());
		int result=recommendService.selectCount(entityWrapper);
		return toAjax(result);
	}


	/**
	 * 新增产品推荐保存
	 */
	@RequiresPermissions("biz:topic:add")
	@Log(title = "产品添加",businessType = BusinessType.INSERT )
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Recommend recommend){
		Product product = new Product();
		product.setPId(recommend.getPId());
		product.setSort(recommend.getRSort());
		productService.updateById(product);
		return toAjax(recommendService.insert(recommend));
	}

	/**
	 * 修改产品推荐
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, ModelMap mmap)
	{
		PdRecommend pdRecommend = recommendService.selectOneByRid(id);
		mmap.put("pdRecommend", pdRecommend);		
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存产品推荐
	 */
	@RequiresPermissions("biz:topic:edit")
	@Log(title = "产品推荐修改",businessType = BusinessType.UPDATE )
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Recommend recommend)
	{
		return toAjax(recommendService.updateById(recommend));
	}
	
	/**
	 * 删除产品推荐
	 */
	@RequiresPermissions("biz:topic:remove")
	@Log(title = "产品推荐删除",businessType = BusinessType.DELETE )
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(recommendService.deleteReByIds(ids));
	}

	@PostMapping("/getType/{pId}")
	@ResponseBody
	public AjaxResult getType(@PathVariable Integer pId){
		EntityWrapper wrapper =new EntityWrapper<Recommend>();
		wrapper.eq("p_id",pId);
		List<Recommend> li=recommendService.selectList(wrapper);
		List<Integer> lis=new ArrayList<>();
		if(!li.isEmpty()){
			for (Recommend re:li) {
				lis.add(re.getRType());
			}
		}
		return success(lis);
	}

	@GetMapping("/prodList/{rtype}")
	public String prodList(@PathVariable("rtype") int rtype, ModelMap mmap){
		mmap.put("rtype",rtype);
		return prefix+"/prod_list";
	}

	@Log(title = "产品推荐查询",businessType = BusinessType.LIST )
	@PostMapping("/productList/{rtype}")
	@ResponseBody
	public TableDataInfo prodList(@PathVariable("rtype") String rtype){
		startPage();
		return getDataTable(recommendService.selectRecommendByType(rtype+""));
	}

	/**
	 * 新增产品主题
	 */
	@GetMapping("/toAddTopicProd/{rtype}")
	public String addTopicProd(@PathVariable("rtype") String rtype, ModelMap mmap){
		Map<String,Object> map = new HashMap<>();
		map.put("rtype",rtype);
		mmap.put("products", productService.selectTopicProduct(map));
		mmap.put("rtype",rtype);
		return prefix +"/add";
	}

	/**
	 * 新增产品主题保存
	 */
	@RequiresPermissions("biz:topic:add")
	@Log(title = "产品主题添加",businessType = BusinessType.INSERT )
	@PostMapping("/addTopicProd")
	@ResponseBody
	public AjaxResult addTopicProd(Recommend recommend){
		Product product = new Product();
		product.setPId(recommend.getPId());
		product.setSort(recommend.getRSort());
		productService.updateById(product);
		return toAjax(recommendService.insert(recommend));
	}

    /**
     * 删除产品推荐
     */
    @RequiresPermissions("biz:topic:remove")
    @Log(title = "产品推荐删除",businessType = BusinessType.DELETE )
    @PostMapping( "/removeTopic")
    @ResponseBody
    public AjaxResult removeTopic(String ids)
    {
        Wrapper wrapper = new EntityWrapper();
        wrapper.in("r_type",ids.split(","));
        return toAjax(recommendService.delete(wrapper));
    }

}
