package com.youdai.daichao.admin_web;

import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.MatchRecommend;
import com.youdai.daichao.domain.PdRecommend;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IMatchRecommendService;
import com.youdai.daichao.service.IProductService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 匹配产品推荐
 */

@Controller
@RequestMapping("/product/matchRecommend")
public class MatchRecommendController extends BaseController {

    @Autowired
    private IMatchRecommendService matchRecommendService;

    @Autowired
    private IProductService productService;

    private final String prefix="biz/product/matchRecommend";

    @RequiresPermissions("biz:matchRecommend:view")
    @GetMapping()
    public  String match(){
        return prefix +"/matchRecommend_list";
    }


    @RequiresPermissions("biz:matchRecommend:view")
    @Log(title = "匹配推荐查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(String pName){
        startPage();
        return getDataTable(matchRecommendService.selectMatchRecommendList(pName));
    }

    /**
     * 新增产品推荐
     */
    @GetMapping("/add")
    public String add( ModelMap mmap){
        mmap.put("products", productService.selectPdsNotInMr());
        return prefix +"/add";
    }

    /**
     * 新增产品推荐保存
     */
    @RequiresPermissions("biz:matchRecommend:add")
    @Log(title = "匹配推荐添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MatchRecommend matchRecommend){

        return toAjax(matchRecommendService.insert(matchRecommend));
    }

    /**
     * 修改产品推荐
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap mmap)
    {
        PdRecommend PdRecommend=matchRecommendService.selectOneByMrid(id);
        mmap.put("matchRecommend", PdRecommend);
        return prefix + "/edit";
    }


    /**
     * 修改保存产品推荐
     */
    @RequiresPermissions("biz:matchRecommend:edit")
    @Log(title = "匹配推荐修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MatchRecommend matchRecommend)
    {
        System.out.println(matchRecommend);
        return toAjax(matchRecommendService.updateById(matchRecommend));
    }


    /**
     * 删除产品推荐
     */
    @RequiresPermissions("biz:matchRecommend:remove")
    @Log(title = "匹配推荐删除",businessType = BusinessType.DELETE )
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(matchRecommendService.deleteReByIds(ids));
    }


}
