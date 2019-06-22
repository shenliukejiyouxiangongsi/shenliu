package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.Project;
import com.youdai.daichao.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/16
 * @Description   项目
 */

@RestController
@RequestMapping(value = "/api/product")
@Slf4j
public class ProductController {

    @Autowired
    IProjectService projectService;

    @GetMapping(value = "getMessage")
    public JsonResp getMessage(String proKey){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("pro_key",proKey);
        Project project=projectService.selectOne(entityWrapper);
        return JsonResp.ok(project);
    }
}
