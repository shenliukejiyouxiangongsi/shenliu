package com.youdai.daichao.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.common.vo.ProductVo;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.domain.ProductShow;
import com.youdai.daichao.mapper.ProductMapper;
import com.youdai.daichao.service.IProductService;
import com.youdai.daichao.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {


    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductVo> selectNewProduct(Page<ProductVo> page,String minMoney,String rate,String maxMoney,String moneyBegin,String moneyEnd,String outTime,String tags) {

        return productMapper.selectNewProduct(page,getParamer(page,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags));
    }

    @Override
    public List<ProductVo> selectHotProduct(Page<ProductVo> page,String minMoney,String rate,String maxMoney,String moneyBegin,String moneyEnd,String outTime,String tags) {

        return productMapper.selectHotProduct(page,getParamer(page,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags));
    }

    @Override
    public List<ProductVo> selectAllProduct(Page<ProductVo> page,String minMoney,String rate,String maxMoney,String moneyBegin,String moneyEnd,String outTime,String tags) {


        return productMapper.selectAllProduct(page,getParamer(page,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags));
    }

    @Override
    public Product selectByPId(int pId) {
        return productMapper.selectByPId(pId);
    }

    public Map<String,Object>  getParamer(Page<ProductVo> page,String minMoney,String rate,String maxMoney,String moneyBegin,String moneyEnd,String outTime,String tags){
        Map<String,Object> map=new HashMap<>();
        map.put("minMoney",minMoney);
        map.put("rate",rate);
        map.put("maxMoney",maxMoney);
        if (StringUtil.isNotEmpty(moneyBegin)){
            map.put("moneyBegin",Integer.parseInt(moneyBegin));
        }
        if (StringUtil.isNotEmpty(moneyEnd)){
            map.put("moneyEnd",Integer.parseInt(moneyEnd));
        }
        if (StringUtil.isNotEmpty(outTime)){
            map.put("outTime",Integer.parseInt(outTime));
        }
        map.put("tags",tags);

        return map;
    }

    @Override
    public List<ProductShow> selectProductShowList(Product product) {

        return productMapper.selectProductShowList(product);
    }

    @Override
    public List<ProductVo> selectMatchRecomList() {
        return productMapper.selectMatchRecomList();
    }


    @Override
	public List<Product> selectPdsNotInRec() {
		
		return productMapper.selectPdsNotInRec();
	}

    @Override
    public List<Product> selectPdsNotInMr() {
        return productMapper.selectPdsNotInMr();
    }


    @Override
    public int updateOrderNum(int pid) {
        return productMapper.updateOrderNum(pid);
    }


	@Override
    public  int selectCountByName(String pName){
        return  productMapper.selectCountByName(pName);
    }

    @Override
    public Map<String, Object> getMinAndMax(int psid) {
        return productMapper.getMinAndMax(psid);
    }

    @Override
    public int changeStatus(Map<String, Object> map) {
        return productMapper.changePdStatusByIds(map);
    }

}
