package com.youdai.daichao.project.system.user.controller;

import com.youdai.daichao.framework.config.DaichaoConfig;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.project.system.menu.domain.Menu;
import com.youdai.daichao.project.system.menu.service.IMenuService;
import com.youdai.daichao.project.system.user.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * 首页 业务处理
 * 
 * @author user-xmp
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private DaichaoConfig daichaoConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", daichaoConfig.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", daichaoConfig.getVersion());
        return "main";
    }
}
