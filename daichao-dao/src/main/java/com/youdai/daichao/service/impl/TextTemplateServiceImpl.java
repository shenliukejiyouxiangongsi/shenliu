package com.youdai.daichao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.domain.TextTemplate;
import com.youdai.daichao.mapper.TextTemplateMapper;
import com.youdai.daichao.service.TextTemplateService;
import org.springframework.stereotype.Service;

/**
 * @version : Ver 1.0
 * @TextTemplateServiceImpl
 * @文本模板ServiceImpl
 */
@Service
public class TextTemplateServiceImpl extends ServiceImpl<TextTemplateMapper, TextTemplate> implements TextTemplateService {
}
