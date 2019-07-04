package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.oss.OssUtils;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.common.vo.File;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.domain.ProductShow;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IProductService;
import com.youdai.daichao.service.IProductTagsService;
import com.youdai.daichao.util.StringUtils;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/13
 * @Description
 */
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {
	
    @Autowired
    OssUtils ossUtils;

    @Value("${product_folder}")
    String folder;

    private String prefix = "biz/product";


    @Autowired
    IProductService productService;

    @Autowired
    private IProductTagsService productTagsService;

   private  final  String reg="^\\d{1,}(\\.\\d{1,})?(w|W|万)$";

    @RequiresPermissions("biz:product:view")
    @GetMapping()
    public String channel()
    {
        return prefix + "/product";
    }


    /**
     * 转换金额
     * @param product
     * @return
     */
    private boolean changeMoney(Product product){
        String minMoney=product.getMinMoney();
        String maxMoney=product.getMaxMoney();
        try {
            if(Pattern.matches(reg,minMoney)){
                minMoney=minMoney.substring(0,(minMoney.length()-1));
                Double min= (Double.parseDouble(minMoney)*10000);
                product.setMinMoney(min.toString());
            }
            if(Pattern.matches(reg,maxMoney)){
                maxMoney=maxMoney.substring(0,(maxMoney.length()-1));
                Double max= (Double.parseDouble(maxMoney)*10000);
                product.setMaxMoney(max.toString());
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            return  false;
        }
        log.info("最小金额：{},最大金额:{}",product.getMinMoney(),product.getMaxMoney());
        return  true;
    }


    /**
     * 查询产品列表
     */
    @RequiresPermissions("biz:product:view")
    @Log(title = "产品查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Product product)
    {
        startPage();
        List<ProductShow> list = productService.selectProductShowList(product);
        return getDataTable(list);
    }


    /**
     * 产品添加
     */
    @GetMapping("/add")
    public String add(ModelMap map){
        map.put("tags",productTagsService.selectList(null));
    	return prefix+"/add";
    }
    
    /**
     * 产品保存添加
     */
    @RequiresPermissions("biz:product:add")
    @Log(title = "产品添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Product product){
    	product.setCreateUser(getSysUser().getLoginName());
    	product.setStatus(1);
        if(!changeMoney(product)){
           return error("输入金额错误");
        }
    	return toAjax(productService.insert(product));
    }

    /**
     * 产品修改
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,ModelMap map){
    	EntityWrapper<Product> entityWrapper = new EntityWrapper<Product>();
    	entityWrapper.eq("p_id", id);
    	Product product = productService.selectOne(entityWrapper);
    	//金额大于万转换为数字中文，例1万；
        String minMoney=product.getMinMoney();
        String maxMoney=product.getMaxMoney();

        try {
            Double min= Double.parseDouble(minMoney);
            if(min>=10000){
                Double m=(double)min/10000;
                product.setMinMoney(m.toString()+"万");
            }
            Double max= Double.parseDouble(maxMoney);
            if(max>=10000){
                Double n=(double)max/10000;
                product.setMaxMoney(n.toString()+"万");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    	map.put("product", product);
        map.put("tags",productTagsService.selectList(null));
    	return prefix+ "/edit";
    }
    
    /**
     * 产品保存修改
     */
    @RequiresPermissions("biz:product:edit")
    @Log(title = "产品修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Product product)
    {
        //金额转换为数字
        if(!changeMoney(product)){
            return error("输入金额错误");
        }
    	if("".equals(product.getLogoUrl())){
    		product.setLogoUrl(null);
    	}
    	product.setStatus(1);
    	return toAjax(productService.updateById(product));

    }

    @RequiresPermissions("biz:product:change")
    @Log(title = "产品状态修改",businessType = BusinessType.UPDATE )
    @PostMapping("/change/{pId}/{setStatus}")
    @ResponseBody
    public AjaxResult change(@PathVariable Long pId, @PathVariable Integer setStatus){
        Map<String ,Object> map=new HashMap<>();
        map.put("pId",pId);
        map.put("status",setStatus);
        return  toAjax(productService.changeStatus(map));
    }

    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upImg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult fileUpload(@RequestParam("img") MultipartFile file) throws IOException {
        File fileT = new File();
        fileT.setUploadStatus(0);
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String extensionName = StringUtils.substringAfter(fileName, ".");
            String newFileName = System.currentTimeMillis() + "." + extensionName;
            ossUtils.putFile(newFileName, file,folder);
            fileT.setFilePath(ossUtils.getFilePath()+folder+"/"+newFileName);
            fileT.setUploadStatus(1);
            log.info("图片地址"+fileT.getFilePath());
        }
        return AjaxResult.success(fileT);
    }

    /**
     * 重名检查
     * @param pName
     * @return
     */
    @PostMapping("/checkName")
    @ResponseBody
    public AjaxResult checkName(@RequestParam("pName") String pName){
        int result=productService.selectCountByName(pName);
        return toAjax(result);
    }

    /**
     * 删除渠道商
     */
    @RequiresPermissions("biz:product:remove")
    @Log(title = "产品删除",businessType = BusinessType.DELETE )
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productService.deleteBatchIds(Arrays.asList(ids.split(","))));
    }


}
