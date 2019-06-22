package com.youdai.daichao.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youdai.daichao.common.vo.JsonResp;
import com.youdai.daichao.common.vo.ProductVo;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.domain.Question;
import com.youdai.daichao.domain.QuestionAnswerUser;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.service.IProductService;
import com.youdai.daichao.service.impl.QuestionAnswerUserServiceImpl;
import com.youdai.daichao.service.impl.QuestionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Anthor: zhankui
 * @Date: 2019/5/13
 * @Description     回答问题
 */
@RestController
@RequestMapping(value = "/api/question")
@Slf4j
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    QuestionAnswerUserServiceImpl questionAnswerUserService;
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IProductService productService;

    /**
     * 获取题目
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public JsonResp list(){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("is_delete",0);
        entityWrapper.orderBy("question_sort",false);
        List<Question> list=questionService.selectList(entityWrapper);
        return JsonResp.ok(list);
    }

    /**
     * 提交答案
     * @return
     */
    @RequestMapping(value = "putAnswer",method = RequestMethod.POST)
    public JsonResp putAnswer(@RequestBody List<QuestionAnswerUser> list){
        if (list.size()>0){
            Map<String,Object> map=new HashMap<>();
            int allScore=0;
            String userId="";
            AppUser user = userService.selectLoginUser();
            if(null!=user){
                userId=String.valueOf(user.getAUid());
            }
            for (int i = 0; i <list.size() ; i++) {
                allScore+=list.get(i).getScore();
                list.get(i).setUserId(userId);
            }
            questionAnswerUserService.insertBatch(list);
            map.put("allScore",allScore);
            List<ProductVo> productVoList=productService.selectMatchRecomList();
            map.put("matchList",productVoList);
            return JsonResp.ok(map);
        }
        return JsonResp.fa("答案不能为空");
    }

}
