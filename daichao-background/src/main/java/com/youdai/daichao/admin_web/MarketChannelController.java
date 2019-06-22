package com.youdai.daichao.admin_web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.MarketChannel;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IMarketChannelService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  市场渠道控制
 * </p>
 *
 * @author wangjiabin
 * @since 2019-04-01
 */
@Controller
@RequestMapping("/marketChannel")
public class MarketChannelController extends BaseController {

    @Autowired
    private IMarketChannelService marketChannelService;

    private String prefix="biz/project/market";

    @RequiresPermissions("biz:marketChannel:view")
    @GetMapping()
    public String marketChannel(){
        return prefix +"/marketChannel";
    }


    /**
     * 列表数据获取
     * @param marketChannel
     * @return
     */
    @RequiresPermissions("biz:marketChannel:view")
    @Log(title = "市场渠道查询",businessType = BusinessType.LIST )
    @PostMapping("list")
    @ResponseBody
    public TableDataInfo  list(MarketChannel marketChannel){
        startPage();
        EntityWrapper wrapper=new EntityWrapper<MarketChannel>();
        String market=marketChannel.getMarketName();
        if(!"".equals(market)){
            wrapper.eq("market_name",market);
        }
        Integer type=marketChannel.getMarketType();
        if(type !=null){
            wrapper.eq("market_type",marketChannel.getMarketType());
        }
        return  getDataTable(marketChannelService.selectList(wrapper));
    }

    /**
     * 添加
     * @return
     */
    @GetMapping("/add")
    public  String add(){
        return prefix +"/add";
    }


    /**
     * 添加保存
     * @param marketChannel
     * @return
     */
    @RequiresPermissions("biz:marketChannel:add")
    @Log(title = "市场渠道添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MarketChannel marketChannel){
        return toAjax(marketChannelService.insert(marketChannel));
    }

    /**
     * 修改
     * @param marketId
     * @param maap
     * @return
     */
    @GetMapping("/edit/{marketId}")
    public  String edit(@PathVariable Integer marketId , ModelMap maap){
        MarketChannel market=marketChannelService.selectById(marketId);
        maap.put("marketChannel",market);
        return prefix +"/edit";
    }


    /**
     * 修改保存
     * @param marketChannel
     * @return
     */
    @RequiresPermissions("biz:marketChannel:edit")
    @Log(title = "市场渠道修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MarketChannel marketChannel){

        return  toAjax(marketChannelService.updateById(marketChannel));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequiresPermissions("biz:marketChannel:remove")
    @Log(title = "市场渠道删除",businessType = BusinessType.DELETE )
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        if(!("".equals(ids) || ids == null)){
            return toAjax(marketChannelService.deleteMarketByIds(ids));
        }else{
            return error("传参错误");
        }
    }


}

