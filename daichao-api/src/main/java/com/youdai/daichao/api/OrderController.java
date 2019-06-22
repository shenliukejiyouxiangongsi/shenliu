package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Merchant;
import com.youdai.daichao.domain.Orders;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IMerchantService;
import com.youdai.daichao.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13
 * @Description 下单控制器
 */
@RestController
@RequestMapping(value = "/api/order")
@Slf4j
public class OrderController {

    @Autowired
    IOrderService orderService;
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IMerchantService merchantService;


    /**
     * 获取商品信息
     * @return
     */
    @RequestMapping(value = "/getMerchant", method = RequestMethod.GET)
    public JsonResp getMerchant(String type){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("type",type);
        entityWrapper.eq("status",1);
        Merchant merchant=merchantService.selectOne(entityWrapper);
        return JsonResp.ok(merchant);
    }



    /**
     * 统一下单接口
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public JsonResp pay(@RequestBody Orders order, HttpServletRequest request){
        String  type=request.getHeader("type");
        AppUser user = userService.findLoginUser();
        if(null!=user) {
            order.setUserId(user.getAUid());
            if ("1".equals(type)) {
                order.setPhoneType("ph_ios");
            }
            if ("2".equals(type)) {
                order.setPhoneType("ph_and");
            }
        }
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("id",order.getMercId());
        entityWrapper.eq("status",1);
        Merchant merchant=merchantService.selectOne(entityWrapper);
        order.setMount(merchant.getDiscountPrice());
        order.setMercName(merchant.getMercName());
        return orderService.pay(order);
    }

}
