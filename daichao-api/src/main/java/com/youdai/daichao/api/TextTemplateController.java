package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.domain.TextTemplate;
import com.youdai.daichao.service.TextTemplateService;
import com.youdai.daichao.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version : Ver 1.0
 * @TextTemplateController
 * @文本模板Controller
 */
@RestController
@RequestMapping(value = "/api/textTemplate")
@Transactional
@CrossOrigin
@Slf4j
public class TextTemplateController {
    @Autowired
    private TextTemplateService textTemplateService;

    /**
     * @param textTemplate
     * @return 返回值JsonResp
     * @添加文本模板
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResp addTextTemplate(@RequestBody TextTemplate textTemplate) {
        log.debug("添加文本模板");
        textTemplateService.insert(textTemplate);
        return JsonResp.ok(textTemplate);
    }

    /**
     * @param textTemplate
     * @return 返回值JsonResp
     * @修改文本模板
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResp updateTextTemplate(@RequestBody TextTemplate textTemplate) {
        log.debug("修改文本模板");
        textTemplateService.updateById(textTemplate);
        return JsonResp.ok(textTemplate);
    }


    /**
     * @param type
     * @return 返回值JsonResp
     * @根据类型查找
     */
    @RequestMapping(value = "/selectOneByType", method = RequestMethod.GET)
    public JsonResp selectMsgModelByType(Integer type, String title) {
        log.debug("根据类型查找");
        EntityWrapper<TextTemplate> ew = new EntityWrapper();
        ew.eq("type", type);
        if (StringUtil.isNotEmpty(title)) {
            ew.eq("title", title);
        }
        ew.eq("status", 1);
        List<TextTemplate> textTemplates = textTemplateService.selectList(ew);
        return JsonResp.ok(textTemplates);
    }

    /**
     * @param id
     * @return 返回值JsonResp
     * @根据id查找文本模板
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public JsonResp selectTextTemplate(Long id) {
        log.debug("查找文本模板");
        TextTemplate textTemplate = textTemplateService.selectById(id);
        return JsonResp.ok(textTemplate);
    }

    @RequestMapping(value = "/selectOneByType1", method = RequestMethod.GET)
    public JsonResp selectOneByType1(Integer type) {
        log.debug("根据类型查找文本");

        EntityWrapper<TextTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("type", type);

        TextTemplate textTemplate = textTemplateService.selectOne(wrapper);
        return JsonResp.ok(textTemplate);
    }

}
