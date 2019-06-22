package com.youdai.daichao.framework.aspectj.lang.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 * 
 * @author user-xmp
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * 表的别名
     */
    public String tableAlias() default "";
}
