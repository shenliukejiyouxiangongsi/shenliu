package com.youdai.daichao.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.youdai.daichao.common.vo.ProductVo;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.domain.ProductShow;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface IProductService extends IService<Product> {

        List<ProductVo> selectNewProduct(Page<ProductVo> page, String minMoney, String rate, String maxMoney, String moneyBegin, String moneyEnd, String outTime, String tags);

        List<ProductVo> selectHotProduct(Page<ProductVo> page, String minMoney, String rate, String maxMoney, String moneyBegin, String moneyEnd, String outTime, String tags);

        List<ProductVo> selectAllProduct(Page<ProductVo> page, String minMoney, String rate, String maxMoney, String moneyBegin, String moneyEnd, String outTime, String tags);

        Product selectByPId(int pId);

        List<ProductShow>  selectProductShowList(Product product);

        List<ProductVo>  selectMatchRecomList();

        List<Product>  selectPdsNotInRec();

        List<Product>  selectPdsNotInMr();

        int updateOrderNum(int pid);

        int selectCountByName(String pName);

        Map<String,Object> getMinAndMax(int psid);

        int changeStatus(Map<String, Object> map);
}
