package com.youdai.daichao.api;

import com.youdai.daichao.common.oss.OssUtils;
import com.youdai.daichao.common.vo.FailException;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.common.vo.PageDto;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.UserOpinion;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.UserOpinionService;
import com.youdai.daichao.util.ArrayUtils;
import com.youdai.daichao.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version : Ver 1.0
 * @UserOpinionController
 * @用户意见Controller
 */
@RestController
@RequestMapping(value = "/api/userOpinion")
@Transactional
@CrossOrigin
@Slf4j
public class UserOpinionController {


    @Autowired
    OssUtils ossUtils;

    @Value("${product_folder}")
    String folder;
    @Autowired
    private UserOpinionService userOpinionService;
    @Autowired
    private IAppUserService userService;

    /**
     * @param userOpinion
     * @return 返回值JsonResp
     * @添加用户意见
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResp addUserOpinion(@RequestBody UserOpinion userOpinion) {
        log.debug("添加用户意见");
        userOpinionService.insert(userOpinion);
        return JsonResp.ok(userOpinion);
    }

    /**
     * @param userOpinion
     * @return 返回值JsonResp
     * @修改用户意见
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResp updateUserOpinion(@RequestBody UserOpinion userOpinion) {
        log.debug("修改用户意见");
        userOpinionService.updateById(userOpinion);
        return JsonResp.ok(userOpinion);
    }

    /**
     * @param id
     * @return 返回值JsonResp
     * @根据id查找用户意见
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public JsonResp selectUserOpinion(Long id) {
        log.debug("查找用户意见");
        UserOpinion userOpinion = userOpinionService.selectById(id);
        return JsonResp.ok(userOpinion);
    }


    /**
     * @param userOpinion
     * @return 返回值JsonResp
     * @添加
     */
    @RequestMapping(value = "/addAppFeedback", method = RequestMethod.POST)
    public JsonResp addAppFeedback(@RequestBody UserOpinion userOpinion) {
        log.debug("添加意见");
        AppUser user = userService.findLoginUser();
        userOpinion.setUserId((long) user.getAUid());
        userOpinion.setUserPhone(user.getAUphone());
        userOpinionService.insert(userOpinion);
        return JsonResp.ok(userOpinion);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonResp<String[]> fileUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        if (ArrayUtils.isEmpty(files)) {
            throw new FailException("文件至少要有一个");
        }
        String[] urls = new String[files.length];
        for (int i = 0; i <urls.length ; i++) {
            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();
            String extensionName = StringUtils.substringAfter(fileName, ".");
            String newFileName = System.currentTimeMillis() + "." + extensionName;
            ossUtils.putFile(newFileName, file,folder);
            urls[i]=ossUtils.getFilePath()+folder+"/"+newFileName;
        }
        return new JsonResp<String[]>().data(urls);
    }

    /*
     * @param
     * @return 返回值JsonResp
     * @显示列表
     */
    @RequestMapping(value = "/findUserList", method = RequestMethod.GET)
    public JsonResp findAll(Integer pageNo, Integer pageSize, Integer type) {
        log.debug("显示列表");

        Map<String, Object> map = new HashMap();
        map.put("type", type);
        map.put("pageNo", (pageNo - 1) * 10);
        map.put("pageSize", 10);
        List<UserOpinion> list = userOpinionService.findAll(map);
        Integer total = userOpinionService.findAllTotal(map);
        return JsonResp.ok(new PageDto(pageNo, 10, list, total));
    }

}
