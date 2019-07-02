package com.youdai.daichao.admin_web;

import com.youdai.daichao.framework.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping()
@Slf4j
public class H5Controller extends BaseController {

    @RequestMapping("/h5")
    public String h5(Map map) {

        return "/register/index";
    }
}
