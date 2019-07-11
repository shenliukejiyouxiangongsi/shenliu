package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.youdai.daichao.common.enums.ProductTypeEnum;
import com.youdai.daichao.domain.Product;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.oss.OssUtils;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.common.vo.File;
import com.youdai.daichao.domain.Advertisement;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IAdvertisementService;
import com.youdai.daichao.service.IProductService;
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
import java.util.List;

/**
 * banner图业务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/advertisement")
@Slf4j
public class AdvertisementController extends BaseController {

	
    @Autowired
    OssUtils ossUtils;

    @Value("${banner_folder}")
    String folder;
	
	@Autowired
	private IAdvertisementService advertisementService;
	@Autowired
	private IProductService productService;
	
	
	private final String prefix="biz/advertisement";

	@RequiresPermissions("biz:advertisement:view")
	@GetMapping()
	public String advertisement(){
		return prefix+"/advertisement";
	}
	
//	/**
//	 * 列表显示
//	 */
//	@RequiresPermissions("biz:advertisement:view")
//	@Log(title = "banner查看",businessType = BusinessType.LIST )
//	@PostMapping("/list")
//	@ResponseBody
//	public TableDataInfo list(Advertisement advertisement){
//		startPage();
//		List<Advertisement> li = advertisementService.selectAdvertisementList(advertisement);
//		return getDataTable(li);
//	}
//
//
//
//	/**
//	 * 添加banner图
//	 */
//	@GetMapping("/add")
//	public String add(){
//		return prefix+"/add";
//	}
//
//	/**
//	 * 保存添加
//	 *
//	 */
//	@RequiresPermissions("biz:advertisement:add")
//	@Log(title = "banner添加",businessType = BusinessType.INSERT )
//	@PostMapping("/add")
//	@ResponseBody
//	public AjaxResult addSave(Advertisement advertisement){
//		if(advertisement.getAdUrl()==null || "".equals(advertisement.getAdUrl())){
//			return  AjaxResult.error("必须上传图片");
//		}
//		advertisement.setCreateUser(getSysUser().getLoginName());
//		advertisement.setStatus(1);
//		return toAjax(advertisementService.insert(advertisement));
//	}
//
//	/**
//	 * 修改
//	 */
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") int id ,ModelMap map){
//		EntityWrapper<Advertisement> entityWrapper = new EntityWrapper<Advertisement>();
//		entityWrapper.eq("ad_id", id);
//		Advertisement ad = advertisementService.selectOne(entityWrapper);
//		map.put("advertisement", ad);
//		return prefix+"/edit";
//	}
//
//
//	/**
//	 * 修改保存
//	 */
//	@RequiresPermissions("biz:advertisement:edit")
//	@Log(title = "banner修改",businessType = BusinessType.UPDATE )
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(Advertisement advertisement){
//		if("".equals(advertisement.getAdUrl())){
//			advertisement.setAdUrl(null);
//		}
//		return toAjax(advertisementService.updateById(advertisement));
//	}
//
//	/**
//	 * 删除banner图
//	 */
//	@RequiresPermissions("biz:advertisement:remove")
//	@Log(title = "banner删除",businessType = BusinessType.DELETE )
//	@PostMapping("/remove")
//	@ResponseBody
//	public AjaxResult remove(String ids){
//		return toAjax(advertisementService.deleteAdByIds(ids));
//	}


    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upImg", method = RequestMethod.POST)
	@Log(title = "banner图上传",businessType = BusinessType.INSERT )
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
	 * 保存添加
	 *
	 */
	@RequiresPermissions("biz:advertisement:add")
	@Log(title = "banner添加",businessType = BusinessType.INSERT )
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Product product){
		if(product.getLogoUrl()==null || "".equals(product.getLogoUrl())){
			return  AjaxResult.error("必须上传图片");
		}
		product.setPType(ProductTypeEnum.BANNER.getCode());
		product.setCreateUser(getSysUser().getLoginName());
		product.setStatus(1);
		return toAjax(productService.insert(product));
	}

	/**
	 * 列表显示
	 */
	@RequiresPermissions("biz:advertisement:view")
	@Log(title = "banner查看",businessType = BusinessType.LIST )
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Product product){
		startPage();
		product.setPType(ProductTypeEnum.BANNER.getCode());
		Wrapper wrapper = new EntityWrapper(product);
		List<Product> li = productService.selectList(wrapper);
		return getDataTable(li);
	}



	/**
	 * 添加banner图
	 */
	@GetMapping("/add")
	public String add(){
		return prefix+"/add";
	}

	/**
	 * 修改
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id ,ModelMap map){
		EntityWrapper<Product> entityWrapper = new EntityWrapper<Product>();
		entityWrapper.eq("p_id", id);
		Product product = productService.selectOne(entityWrapper);
		map.put("advertisement", product);
		return prefix+"/edit";
	}


	/**
	 * 修改保存
	 */
	@RequiresPermissions("biz:advertisement:edit")
	@Log(title = "banner修改",businessType = BusinessType.UPDATE )
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Product product){
		if("".equals(product.getLinkUrl())){
			product.setLinkUrl(null);
		}
		return toAjax(productService.updateById(product));
	}

	/**
	 * 删除banner图
	 */
	@RequiresPermissions("biz:advertisement:remove")
	@Log(title = "banner删除",businessType = BusinessType.DELETE )
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		return toAjax(productService.deleteBatchIds(Arrays.asList(ids.split(","))));
	}

}
