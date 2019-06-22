package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.AppSwitch;
import com.youdai.daichao.domain.MarketChannel;
import com.youdai.daichao.domain.Shell;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IAppSwitchService;
import com.youdai.daichao.service.IMarketChannelService;
import com.youdai.daichao.service.IShellService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * app审核
 * @author Administrator
 */

@Controller
@RequestMapping("/appSwitch")
public class AppSwitchController extends BaseController {

    @Autowired
    private IAppSwitchService appSwitchService;

    @Autowired
    private IMarketChannelService marketChannelService;


    @Autowired
    private IShellService shellService;


    private  String prefix="biz/project/market/appSwitch";

    @GetMapping()
    @RequiresPermissions("biz:appSwitch:view")
    public  String appSwitch(ModelMap maap){
        EntityWrapper wrapper=new EntityWrapper<MarketChannel>();
        List<MarketChannel> li=marketChannelService.selectList(wrapper);
        maap.put("marketChannels",li);
        return prefix +"/appSwitch";
    }

    /**
     * 列表显示
     * @param appSwitch
     * @return
     */
    @RequiresPermissions("biz:appSwitch:view")
    @PostMapping("/list")
    @Log(title = "App选择列表",businessType = BusinessType.LIST )
    @ResponseBody
    public TableDataInfo list(AppSwitch appSwitch){
        startPage();
        return getDataTable(appSwitchService.selectAppSwitch(appSwitch));
    }


    /**
     * 渠道app列表
     * @param marketId
     * @return
     */
    @PostMapping("/list/{marketId}")
    @ResponseBody
    public  TableDataInfo listByMarketId(@PathVariable Integer marketId){
        AppSwitch as= new AppSwitch();
        as.setMarketId(marketId);
        return getDataTable(appSwitchService.selectAppSwitch(as));
    }

    /**
     * 添加
     */
    @GetMapping("/add")
    public String add(ModelMap maap){
        EntityWrapper wrapper=new EntityWrapper<MarketChannel>();
        List<MarketChannel> marketChannels= marketChannelService.selectList(wrapper);

        maap.put("marketChannels",marketChannels);

        return prefix +"/add";
    }


    @GetMapping("/add/{marketId}")
    public String addByMarket(@PathVariable Integer marketId,ModelMap maap){
        MarketChannel marketChannel=marketChannelService.selectById(marketId);
        maap.put("marketChannel",marketChannel);

        EntityWrapper wrapper=new EntityWrapper<Shell>();
        List<Shell> shells=shellService.selectList(wrapper);
        maap.put("shells",shells);

        return prefix+"/add";
    }

    /**
     * 添加保存
     * @param appSwitch
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    @RequiresPermissions("biz:appSwitch:add")
    @Log(title = "App选择添加",businessType = BusinessType.INSERT )
    public AjaxResult addSave(AppSwitch appSwitch){
        //加默认switching
//        appSwitch.setStatus(1);
        return toAjax(appSwitchService.insert(appSwitch));
    }



    /**
     * 修改
     * @param id
     * @param maap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id ,ModelMap maap){
        EntityWrapper wrapper=new EntityWrapper<MarketChannel>();
        List<MarketChannel> marketChannels= marketChannelService.selectList(wrapper);
        maap.put("marketChannels",marketChannels);
        EntityWrapper entityWrapper =new EntityWrapper<AppSwitch>();
        entityWrapper.eq("id",id);
        AppSwitch appSwitch = appSwitchService.selectOne(entityWrapper);
        maap.put("appSwitch",appSwitch);
        return  prefix+"/edit";
    }

    /**
     * 修改保存
     * @param appSwitch
     * @return
     */
    @PostMapping("/edit")
    @RequiresPermissions("biz:appSwitch:edit")
    @Log(title = "App选择修改",businessType = BusinessType.UPDATE )
    @ResponseBody
    public AjaxResult editSave(AppSwitch appSwitch){
        return  toAjax(appSwitchService.updateById(appSwitch));
    }



    /**
     * 修改状态
     * @return
     */
    @RequiresPermissions("biz:appSwitch:change")
    @Log(title = "App选择修改",businessType = BusinessType.UPDATE )
    @PostMapping("/change")
    @ResponseBody
    public AjaxResult change(AppSwitch appSwitch){
        return toAjax(appSwitchService.changeStatus(appSwitch));
    }


    /**
     * app审核添加检查版本
     * @param appSwitch
     * @return 1表示存在该版本，0表示不存在该版本，不重名返回error（）
     *
     */
    @PostMapping("/checkAppVersion")
    @ResponseBody
    public AjaxResult checkAppVersion(AppSwitch appSwitch){
       EntityWrapper wrapper=new EntityWrapper<AppSwitch>();
       wrapper.eq("app_version",appSwitch.getAppVersion());
       wrapper.eq("market_id",appSwitch.getMarketId());
       wrapper.eq("app_type",appSwitch.getAppType());
       int result=appSwitchService.selectCount(wrapper);
       return toAjax(result);
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer ids){
        return toAjax(appSwitchService.deleteById(ids));
    }


    /**
     *
     * @param marketId
     * @param maap
     * @return
     */
    @RequiresPermissions("biz:appSwitch:all")
    @GetMapping("/searchAllAppSwitch/{marketId}")
    public  String show(@PathVariable String marketId,ModelMap maap){
        maap.put("marketId",marketId);
        return prefix +"/appSwitch";
    }
}
