package com.youdai.daichao.service.impl;

import com.youdai.daichao.domain.Orders;
import com.youdai.daichao.mapper.OrdersMapper;
import com.youdai.daichao.service.IOrdersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-05-15
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
