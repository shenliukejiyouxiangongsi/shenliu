package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.common.vo.PageDto;
import com.youdai.daichao.service.IAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/20
 * @Description 轮播图管理
 */
@RestController
@RequestMapping(value = "/api/ad")
@CrossOrigin
@Slf4j
public class AdvertisementController {

    @Autowired
    IAdvertisementService advertisementService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public JsonResp list(Integer pageNo, Integer pageSize){
        log.info("展示所有banner图");
        Page page = new Page(pageNo, pageSize);
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("status",1);
        entityWrapper.orderBy("sort",true);
        page=advertisementService.selectPage(page,entityWrapper);
        return JsonResp.ok(new PageDto(pageNo, pageSize, page.getRecords(), page.getTotal()));
    }

}
