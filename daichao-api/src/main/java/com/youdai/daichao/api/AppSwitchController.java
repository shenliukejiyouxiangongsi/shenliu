package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.AppSwitch;
import com.youdai.daichao.domain.AppVersion;
import com.youdai.daichao.domain.MarketChannel;
import com.youdai.daichao.service.IAppSwitchService;
import com.youdai.daichao.service.IAppVersionService;
import com.youdai.daichao.service.IMarketChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/20
 * @Description
 */
@RestController
@RequestMapping(value = "/api/appSwitch")
@CrossOrigin
@Slf4j
public class AppSwitchController {

    @Autowired
    IAppSwitchService appSwitchService;
    @Autowired
    IAppVersionService appVersionService;
    @Autowired
    IMarketChannelService marketChannelService;

    @RequestMapping(value = "getVersion",method = RequestMethod.GET)
    public JsonResp getAppSwitch(int appType,String version,String marketCode){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("app_version",version);
        entityWrapper.eq("app_type",appType);
        if(null!=marketCode&&!"".equals(marketCode)){
            EntityWrapper wrapper=new EntityWrapper();
            wrapper.eq("market_code",marketCode);
            MarketChannel marketChannel=marketChannelService.selectOne(wrapper);
            if(null!=marketChannel){
                entityWrapper.eq("market_id",marketChannel.getMarketId());
            }
        }
        AppSwitch appSwitch=appSwitchService.selectOne(entityWrapper);
        if(null==appSwitch){
            return JsonResp.fa("没有此版本");
        }
        return JsonResp.ok(appSwitch);
    }

    /**
     * 检查更新
     * @param appType
     * @return
     */
    @RequestMapping(value = "getPass",method = RequestMethod.GET)
    public JsonResp getPass(int appType,String version){
        AppVersion version1=new AppVersion();
        version1.setAppType(appType);
        version1.setAppVersion(version);
        List<AppVersion> appVersionList=appVersionService.selectNewVersionList(version1);
        if(appVersionList.size()>0){
            for (int i = 0; i <appVersionList.size() ; i++) {
                if(null!=appVersionList.get(i).getNeedUpdated()&&appVersionList.get(i).getNeedUpdated().equals("1")){
                    return  JsonResp.ok(appVersionList.get(i));
                }
            }
            return JsonResp.ok(appVersionList.get(0));
        }
        return JsonResp.fa("没有此版本");
    }
}
