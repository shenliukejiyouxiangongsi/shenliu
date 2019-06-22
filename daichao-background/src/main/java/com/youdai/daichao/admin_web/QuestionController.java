package com.youdai.daichao.admin_web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.framework.web.controller.BaseController;
import com.youdai.daichao.common.vo.AjaxResult;
import com.youdai.daichao.domain.Question;
import com.youdai.daichao.framework.aspectj.lang.annotation.Log;
import com.youdai.daichao.framework.aspectj.lang.enums.BusinessType;
import com.youdai.daichao.service.IQuestionService;
import com.youdai.daichao.framework.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController  extends BaseController {

    @Autowired
    private IQuestionService questionService;

    private final  String  prefix="biz/question";


    @RequiresPermissions("biz:question:view")
    @GetMapping()
    public String question(){
        return prefix +"/question_list";
    }


    @RequiresPermissions("biz:question:view")
    @Log(title = "问题查询",businessType = BusinessType.LIST )
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Question question){
        startPage();
        EntityWrapper wrapper=new EntityWrapper<Question>();
        if(!"".equals(question.getDescription())) {
            wrapper.like("description", question.getDescription());
        }
        if(question.getDegree()!=null) {
            wrapper.eq("degree", question.getDegree());
        }

        wrapper.eq("is_delete",0);

        List<Question> list =questionService.selectList(wrapper);
        return getDataTable(list);
    }

    @GetMapping("/add")
    public String add(){
        return  prefix +"/add";
    }


    @RequiresPermissions("biz:question:add")
    @Log(title = "问题添加",businessType = BusinessType.INSERT )
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Question question){
        question.setCreateUserId((long) getSysUser().getUserId());
        question.setCreateTime(new Date());
        return  toAjax(questionService.insert(question));
    }

    @GetMapping("/edit/{questionId}")
    public  String  edit(@PathVariable Integer questionId, ModelMap map){
        map.put("question",questionService.selectById(questionId));
        return prefix +"/edit";
    }


    @RequiresPermissions("biz:question:edit")
    @Log(title = "问题修改",businessType = BusinessType.UPDATE )
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Question question){
        System.out.println(question);
        question.setModifyUserId((long) getSysUser().getUserId());
        return  toAjax(questionService.updateById(question));
    }

    @RequiresPermissions("biz:question:remove")
    @Log(title = "问题删除",businessType = BusinessType.DELETE )
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        return toAjax(questionService.setDeleteByIds(ids));
    }


    @RequiresPermissions("biz:question:detail")
    @Log(title = "问题详情查看",businessType = BusinessType.LIST )
    @GetMapping("/showDetail/{questionId}")
    public  String  showDetail(@PathVariable Integer questionId, ModelMap map){
        map.put("question",questionService.selectById(questionId));
        return prefix +"/question_detail";
    }

}
