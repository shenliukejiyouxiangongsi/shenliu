package com.youdai.daichao.admin_web;


import com.youdai.daichao.domain.LandingPage;
import com.youdai.daichao.domain.UserRecord;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import com.youdai.daichao.service.IChannelService;
import com.youdai.daichao.service.IProductCountLogService;
import com.youdai.daichao.service.IUserRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/productView")
public class ProductViewController  extends BaseController {

    @Autowired
    private IUserRecordService userRecordService;

    @Autowired
    private IProductCountLogService productCountLogService;

    @Autowired
    private IChannelService channelService;

    private final  String prefix="biz/productView";


    @RequiresPermissions("biz:productView:view")
    @GetMapping()
    public String productView(ModelMap map){
        map.put("channels",channelService.selectAllChannel());
        return prefix +"/productView_list";
    }


    /**
     *
     * @param userPhone
     * @param channelId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequiresPermissions("biz:productView:view")
    @Log(title = "产品查看记录",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(String userPhone,Integer channelId ,String startTime,String endTime
            ,String udid,String ip){

        Map map =new HashMap<String,String>();
        map.put("udid",udid);
        map.put("ip",ip);
        map.put("userPhone",userPhone);
        map.put("channelId",channelId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        startPage();
        List<UserRecord> list=userRecordService.selectUserRecords(map);
        return getDataTable(list);
    }


    @GetMapping("/showDetail/{id}/{status}/{udid}")
    public  String detail(@PathVariable Long id,@PathVariable String udid,@PathVariable Integer status,ModelMap map){
        return prefix+"/show_detail";
    }

    @RequiresPermissions("biz:productView:detail")
    @Log(title = "产品查看详情记录",businessType = BusinessType.LIST )
    @PostMapping("/detail/list")
    @ResponseBody
    public  TableDataInfo showDetail(Long id,String udid,Integer status,String pName,Integer type ){
        Map map=new HashMap<String,Object>();

        map.put("deviceFlag", udid);
        map.put("pName",pName);
        map.put("type",type);

        //status为0，浏览记录，status为1，申请记录
        map.put("status",status);
        startPage();
        List<LandingPage> list =productCountLogService.selectListByDeviceFlag(map);
        return getDataTable(list);
    }

}
