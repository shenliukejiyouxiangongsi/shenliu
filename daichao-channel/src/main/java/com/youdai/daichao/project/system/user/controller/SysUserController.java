package com.youdai.daichao.project.system.user.controller;

import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.framework.shiro.service.PasswordService;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.project.system.user.domain.SysUser;
import com.youdai.daichao.project.system.user.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/channel")
public class SysUserController extends BaseController {
    private String prefix = "";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    PasswordService passwordService;


    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password)
    {
        SysUser user = getSysUser();
        String encrypt = new Md5Hash(user.getUserName() + password ).toHex().toString();
        if (user.getPassword().equals(encrypt))
        {
            return true;
        }
        return false;
    }


    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", sysUserService.selectUserByid(userId));
        return  "resetPwd";
    }



    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(SysUser user)
    {
        System.out.println(user);
        int rows = sysUserService.resetUserPwd(user);
        if (rows > 0)
        {
            setSysUser(sysUserService.selectUserByid(user.getUserId()));
            return success();
        }
        return error();
    }





}
