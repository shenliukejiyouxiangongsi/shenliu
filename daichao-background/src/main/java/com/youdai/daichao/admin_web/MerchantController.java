package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.Merchant;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IMerchantService;
import com.youdai.daichao.util.StringUtils;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Anthor: Administrator
 * @Date: 2019/5/20
 * @Description
 */

@Controller
@RequestMapping("/merchant")
public class MerchantController  extends BaseController {

    @Autowired
    private IMerchantService merchantService;

    private final  String prefix="biz/merchant";

    @RequiresPermissions("biz:merchant:view")
    @GetMapping()
    public  String merchant(){
        return prefix+"/merchant";
    }


    @RequiresPermissions("biz:merchant:view")
    @Log(title = "商品查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Merchant merchant){
        EntityWrapper wrapper =new EntityWrapper<Merchant>();
        if(StringUtils.isNotEmpty(merchant.getMercName())){
            wrapper.like("merc_name",merchant.getMercName());
        }
        if(merchant.getStatus()!=null) {
            wrapper.eq("status", merchant.getStatus());
        }

        List<Merchant> list=merchantService.selectList(wrapper);
        return  getDataTable(list);
    }

    @GetMapping("/add")
    public String add(){
        return  prefix +"/add";
    }



    @RequiresPermissions("biz:merchant:add")
    @Log(title = "商品添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Merchant merchant){
        return  toAjax(merchantService.insert(merchant));
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        map.put("merchant",merchantService.selectById(id));
        return prefix +"/edit";
    }


    @RequiresPermissions("biz:merchant:edit")
    @Log(title = "商品修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Merchant merchant){
        return  toAjax(merchantService.updateById(merchant));
    }


    @RequiresPermissions("biz:merchant:change")
    @Log(title = "商品修改",businessType = BusinessType.UPDATE )
    @PostMapping("/change/{id}/{setStatus}")
    @ResponseBody
    public AjaxResult change(@PathVariable Long id, @PathVariable Integer setStatus){
        return  toAjax(merchantService.changeStatus(id,setStatus));
    }
}
