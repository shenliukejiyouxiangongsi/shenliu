package com.youdai.daichao.admin_web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.ProductTags;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IProductTagsService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author zhankui
 * @since 2019-05-13
 */
@Controller
@RequestMapping("/product/tags")
public class ProductTagsController extends BaseController {

    @Autowired
    private IProductTagsService productTagsService;

    private String prefix="biz/product/tags";

    @RequiresPermissions("biz:tags:view")
    @GetMapping()
    public String tags()
    {
        return prefix+"/tags_list";
    }


    @RequiresPermissions("biz:tags:view")
    @Log(title = "产品标签查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProductTags tags){
        startPage();
        EntityWrapper wrapper=new EntityWrapper<ProductTags>();
        wrapper.like("tag_name",tags.getTagName());
        List<ProductTags> li =productTagsService.selectList(wrapper);
        return getDataTable(li);
    }

    @GetMapping("/add")
    public  String add(){
        return prefix +"/add";
    }


    @RequiresPermissions("biz:tags:add")
    @Log(title = "产品标签添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProductTags tags){
        EntityWrapper wrapper =new EntityWrapper<ProductTags>();
        wrapper.eq("tag_name",tags.getTagName());
        int i=productTagsService.selectCount(wrapper);
        if(i>0){
            return error("该便签已存在，请重新输入");
        }
        return toAjax(productTagsService.insert(tags));
    }

    @GetMapping("/edit/{tagId}")
    public  String edit(@PathVariable() Integer tagId , ModelMap map){
        map.put("tag",productTagsService.selectById(tagId));
        return prefix+ "/edit";
    }


    @RequiresPermissions("biz:tags:edit")
    @Log(title = "产品标签修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(ProductTags tags){
        ProductTags fdTag=productTagsService.selectById(tags.getTagId());
        if(!fdTag.getTagName().equals(tags.getTagName())){
            EntityWrapper wrapper =new EntityWrapper<ProductTags>();
            wrapper.eq("tag_name",tags.getTagName());
            int i=productTagsService.selectCount(wrapper);
            if(i>0){
                return error("该便签已存在，请重新输入");
            }
        }
        return toAjax(productTagsService.updateById(tags));
    }


    @RequiresPermissions("biz:tags:remove")
    @Log(title = "产品标签删除",businessType = BusinessType.DELETE )
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        return toAjax(productTagsService.deleteByIds(ids));
    }
}

