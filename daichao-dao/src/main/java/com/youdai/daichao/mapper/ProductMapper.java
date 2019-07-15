package com.youdai.daichao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youdai.daichao.common.vo.ProductVo;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.domain.ProductShow;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询最新产品
     * @param page
     * @param map
     * @return
     */
    List<ProductVo> selectNewProduct(Page<ProductVo> page, Map<String, Object> map);

    /**
     * 查询最热产品
     * @param page
     * @param map
     * @return
     */
    List<ProductVo> selectHotProduct(Page<ProductVo> page, Map<String, Object> map);
    /**
     * 查询所有产品
     * @param page
     * @param map
     * @return
     */
    List<ProductVo> selectAllProduct(Page<ProductVo> page, Map<String, Object> map);

    List<ProductShow>  selectProductShowList(Product product);

    /**
     * 提交问题后推荐list
     * @return
     */
    List<ProductVo> selectMatchRecomList();
	/**
	 * 查询推荐表中所有未有的产品
	 */
    List<Product>  selectPdsNotInRec();

    List<Product>  selectPdsNotInMr();


    int updateOrderNum(int pid);

    int selectCountByName(String pName);

    Map<String, Object> getMinAndMax(int pId);

    //下线产品
    int changePdStatusByIds(Map<String, Object> map);

    Product selectByPId(int pId);

    public List<ProductVo> selectProducts(Page<ProductVo> page,Map map);
}
