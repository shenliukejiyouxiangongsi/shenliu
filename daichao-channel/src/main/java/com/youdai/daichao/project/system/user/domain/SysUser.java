package com.youdai.daichao.project.system.user.domain;

import com.youdai.daichao.framework.aspectj.lang.annotation.Excel;
import com.youdai.daichao.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 用户对象 sys_user
 * 
 * @author zhuliangjie
 */
@Getter
@Setter
@ToString
@Data
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号")
    private Long userId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 密码 */
    private String password;



}
