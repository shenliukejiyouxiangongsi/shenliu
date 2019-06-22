package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.common.vo.PageDto;
import com.youdai.daichao.common.vo.ProductVo;
import com.youdai.daichao.domain.*;
import com.youdai.daichao.service.*;
import com.youdai.daichao.util.DateUtils;
import com.youdai.daichao.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/12
 * @Description  产品控制器
 */
@RestController
@RequestMapping(value = "/api/project")
@Slf4j
public class ProjectController {

    @Autowired
    private IProductService productService;
    @Autowired
    IUserCountLogService userCountLogService;
    @Autowired
    IProductCountLogService productCountLogService;
    @Autowired
    IProductTagsService tagsService;
    @Autowired
    IUserRecordService userRecordService;
    @Autowired
    IAppUserService userService;
    @Autowired
    RedisCache redisCache;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public JsonResp list(Integer pageNo, Integer pageSize,String minMoney,String rate, String maxMoney, String moneyBegin,String moneyEnd, String outTime, String tags,String type){
        log.info("通过筛选条件展示产品");
        if(null!=type&&!"".equals(type)){
            if(type.equals("1")){
                Page hotPage = new Page<ProductVo>(pageNo, pageSize);
                List<ProductVo> list=productService.selectHotProduct(hotPage,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags);
                return JsonResp.ok(new PageDto(pageNo, pageSize, list, list.size()));
            }
            if(type.equals("2")){
                Page newPage = new Page<ProductVo>(pageNo, pageSize);
                List<ProductVo> list=productService.selectNewProduct(newPage,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags);
                return JsonResp.ok(new PageDto(pageNo, pageSize, list, list.size()));
            }
        }
        Page page = new Page<ProductVo>(pageNo, pageSize);
        List<ProductVo> list=productService.selectAllProduct(page,minMoney,rate,maxMoney,moneyBegin,moneyEnd,outTime,tags);
        return JsonResp.ok(new PageDto(pageNo, pageSize, list, list.size()));
    }

    /**查询产品详情页
     * @param o
     * @return
     */
    @RequestMapping( value = "/selectOne", method = RequestMethod.POST )
    public JsonResp selectOne(@RequestBody  Map<String,Object> o, HttpServletRequest request){
        int psid=Integer.parseInt((String) o.get("proId"));
        String type= (String) o.get("type");
        String  deviceFlag=request.getHeader("deviceFlag");
        System.out.println("查看产品详情页面"+deviceFlag);
        Product product=productService.selectByPId(psid);
        if(null!=product){
            Map<String, Object> map=productService.getMinAndMax(psid);
            int minId=((BigDecimal) map.get("minId")).intValue();
            int maxId= ((BigDecimal) map.get("maxId")).intValue();
            int lastId= ((BigDecimal) map.get("lastId")).intValue();
            int nextId= ((BigDecimal)  map.get("nextId")).intValue();
            //只有一条数据
            if (psid==minId&&psid==maxId){
                product.setLastInt(0);
                product.setNextInt(0);
            }
            //有两条数据
            if(minId==lastId&&psid!=lastId){
                product.setLastInt(lastId);
                product.setNextInt(lastId);
            }
            if(maxId==nextId&&psid!=nextId){
                product.setLastInt(nextId);
                product.setNextInt(nextId);
            }
            //大于三条数据
            if (psid==minId&&psid!=maxId&&maxId!=nextId){
                product.setLastInt(maxId);
                product.setNextInt(nextId);
            }
             if (psid==maxId&&psid!=minId&&minId!=lastId){
                product.setLastInt(lastId);
                product.setNextInt(minId);
            }if(psid!=minId&&psid!=maxId) {
                product.setLastInt(lastId);
                product.setNextInt(nextId);
            }

           String device=getRecordId(request);
            //插入日志表
            UserCountLog userCountLog=new UserCountLog();
            userCountLog.setViewProductNum(1);
            userCountLog.setDeviceFlag(device);
            userCountLogService.insert(userCountLog);

            ProductCountLog productCountLog=new ProductCountLog();
            productCountLog.setType(type);
            EntityWrapper productWrapper=new EntityWrapper();
            productWrapper.eq("device_flag",device);
            productWrapper.eq("p_id",psid);
            productWrapper.eq("first_view_num",1);
            productWrapper.gt("create_time",DateUtils.getDayStartDate(new Date()));
            ProductCountLog oldProductCountLog =  productCountLogService.selectOne(productWrapper);
            if(null==oldProductCountLog){
                productCountLog.setFirstViewNum(1);
                productCountLog.setFirstUserNum(1);
                productCountLog.setPId(product.getPId());
                productCountLog.setDeviceFlag(device);
                productCountLogService.insert(productCountLog);
            }else {
                productCountLog.setFirstViewNum(1);
                productCountLog.setPId(product.getPId());
                productCountLog.setDeviceFlag(device);
                productCountLogService.insert(productCountLog);
            }

        }
        return JsonResp.ok(product);
    }

    /**
     * 立即申请
     * @return
     */
    @RequestMapping(value = "/toApply", method = RequestMethod.GET)
    public JsonResp toApply(String pId,String type, HttpServletRequest request){
        AppUser user = userService.findLoginUser();
        String  deviceFlag=request.getHeader("deviceFlag");
        log.info("立即申请");
        int pid=Integer.parseInt(pId);
        int result=productService.updateOrderNum(pid);
        if(result==1){
            EntityWrapper userRecord=new EntityWrapper();
            userRecord.eq("udid",deviceFlag);
            UserRecord entity=userRecordService.selectOne(userRecord);
            String device="";
            if(null!=entity){
                device=String.valueOf(entity.getId());
            }
            ProductCountLog productCountLog=new ProductCountLog();
            productCountLog.setType(type);
            EntityWrapper productWrapper=new EntityWrapper();
            productWrapper.eq("device_flag",device);
            productWrapper.eq("p_id",Integer.parseInt(pId));
            productWrapper.eq("second_user_num",1);
            productWrapper.gt("create_time",DateUtils.getDayStartDate(new Date()));
            ProductCountLog oldProductCountLog =  productCountLogService.selectOne(productWrapper);
            if(null==oldProductCountLog){
                productCountLog.setSecondViewNum(1);
                productCountLog.setSecondUserNum(1);
                productCountLog.setPId(pid);
                productCountLog.setDeviceFlag(device);
                productCountLogService.insert(productCountLog);
            }else {
                productCountLog.setSecondViewNum(1);
                productCountLog.setPId(pid);
                productCountLog.setDeviceFlag(device);
                productCountLogService.insert(productCountLog);
            }
        }
        return JsonResp.ok("成功");
    }



    /**
     * 获取标签
     * @return
     */
    @RequestMapping(value = "getLabel",method = RequestMethod.GET)
    public JsonResp getLabel(){
        List<ProductTags>  list=tagsService.selectList(null);
        return JsonResp.ok(list);
    }


    public  String getRecordId(HttpServletRequest request){

        String recordId="";
        UserRecord userRecord=new UserRecord();
        String  deviceFlag=request.getHeader("deviceFlag");
        String  type=request.getHeader("type");
        String ip =RequestUtil.getIpAddr(request);
        //移动设备
        if(null!=type&&!"".equals(type)){
            userRecord=redisCache.getCache(deviceFlag+ip);
            if(userRecord==null){
                EntityWrapper entityWrapper=new EntityWrapper();
                entityWrapper.eq("type",type);
                entityWrapper.eq("udid",deviceFlag);
                entityWrapper.eq("ip",ip);
                userRecord=userRecordService.selectOne(entityWrapper);
            }
        }else {
            String userAgent= request.getHeader("user-agent");
            userRecord=redisCache.getCache(ip+userAgent);
            if(userRecord==null){
                EntityWrapper entityWrapper=new EntityWrapper();
                entityWrapper.eq("user_agent",userAgent);
                entityWrapper.eq("ip",ip);
                userRecord= userRecordService.selectOne(entityWrapper);
            }
        }
        recordId=String.valueOf(userRecord.getId());
        return recordId;
    }

    /**
     * 查询最新产品
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "selectNewList", method = RequestMethod.GET)
    public JsonResp selectNewList(Integer pageNo, Integer pageSize){
        log.info("展示最新产品");
        Page page = new Page<ProductVo>(pageNo, pageSize);
        List<ProductVo> list=productService.selectNewProduct(page,null,null,null,null,null,null,null);
        return JsonResp.ok(new PageDto(pageNo, pageSize, list, list.size()));
    }

}
