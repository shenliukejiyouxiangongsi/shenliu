package com.youdai.daichao.project.monitor.druid;

import com.youdai.daichao.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 * 
 * @author user-xmp
 */
//@Controller
//@RequestMapping("/monitor/data")
//public class DruidController extends BaseController
//{
//    private String prefix = "/monitor/druid";
//
//    @RequiresPermissions("monitor:data:view")
//    @GetMapping()
//    public String index()
//    {
//        return redirect(prefix + "/index");
//    }
//}
