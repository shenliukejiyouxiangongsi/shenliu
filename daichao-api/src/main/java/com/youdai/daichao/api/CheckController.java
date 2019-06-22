//package com.youdai.daichao.api;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.youdai.daichao.common.vo.CheckBody;
//import com.youdai.daichao.common.vo.JsonResp;
//import com.youdai.daichao.domain.AppUser;
//import com.youdai.daichao.domain.Orders;
//import com.youdai.daichao.service.IAppUserService;
//import com.youdai.daichao.service.IOrdersService;
//import com.youdai.daichao.util.HttpClientUtil;
//import com.youdai.daichao.util.WXParse;
//import com.youdai.daichao.util.XMLToMapToBean;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Anthor: zhankui
// * @Date: 2019/5/17
// * @Description 黑网检测
// */
//@RestController
//@RequestMapping(value = "/api/check")
//@Slf4j
//@CrossOrigin
//public class CheckController {
//
//    @Value("${jump_url}")
//    String jump_url;
//    @Value("${select_report_url}")
//    String select_report_url;
//    @Value("${select_check_record_url}")
//    String select_check_record_url;
//    @Autowired
//    private IAppUserService userService;
//    @Autowired
//    IOrdersService ordersService;
//
//
//    @RequestMapping(value = "getCheckResult",method = RequestMethod.GET)
//    public JsonResp getCheckResult(String orderNo){
//        Map<String,Object> map=new HashMap<>();
//        EntityWrapper entityWrapper=new EntityWrapper();
//        entityWrapper.eq("order_no",orderNo);
//        Orders orders=ordersService.selectOne(entityWrapper);
//        map.put("orders",orders);
//        if(orders.getReportStatus()==1){
//                map.put("jumpUrl",jump_url);
//        }
//        return JsonResp.ok(map);
//    }
//
//    /**
//     * 获取认证报告
//     * @return
//     */
//    @RequestMapping(value = "selectReport")
//    public String selectReport(@RequestBody CheckBody entity){
//        Map<String,Object> params = XMLToMapToBean.BeanToMap(entity);
//        String sign=entity.getSign();
//        try {
//            Boolean flag= WXParse.getReqSign(params,"youdai").equals(sign)? true:false;
//            //验签成功,请求认证报告信息
//            if(flag){
//                JSONObject param=new JSONObject();
//                param.put("reportNumber",params.get("reportNumber"));
//                String result = HttpClientUtil.postParameters(select_report_url, JSONObject.toJSONString(param));
//                return result;
//            } else {
//                return JsonResp.fa("验签失败").toJson();
//            }
//        } catch (Exception e) {
//            log.error("获取认证报告异常:{}",e.getMessage());
//            return JsonResp.fa("获取认证报告异常").toJson();
//        }
//    }
//
//    /**
//     * 检测记录结果
//     * @return
//     */
//    @RequestMapping(value = "selectCheckRecord",method = RequestMethod.GET)
//    public String selectCheckRecord(int status){
//        AppUser user=userService.findLoginUser();
//        JSONObject param=new JSONObject();
//        param.put("channel","daichao");
//        param.put("status",status);
//        param.put("channelUserId",user.getAUid());
//        try {
//            String result = HttpClientUtil.postParameters(select_check_record_url, JSONObject.toJSONString(param));
//            return result;
//        } catch (Exception e) {
//            log.error("获取检测记录异常:{}",e.getMessage());
//            return JsonResp.fa("获取检测记录异常").toJson();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        Map<String,Object>  map=new HashMap<>();
//        map.put("reportNumber","2019052018510001");
//        System.out.println(WXParse.getReqSign(map,"youdai"));
//    }
//}
