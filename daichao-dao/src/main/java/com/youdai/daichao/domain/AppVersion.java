package com.youdai.daichao.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.youdai.daichao.util.DateJsonDeserializer;
import com.youdai.daichao.util.DateJsonSerializer;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * app版本表
 * </p>
 *
 * @author zhankui
 * @since 2019-03-20
 */
@Data
@Accessors(chain = true)
@TableName("app_version")
public class AppVersion extends Model<AppVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 0代表IOS，1代表Android
     */
    @TableField("app_type")
    private Integer appType;
    /**
     * 版本号
     */
    @TableField("app_version")
    private String appVersion;
    /**
     * 是否需要更新  0代表强制更新  1代表不强制更新  2代表不更新
     */
    @TableField("need_updated")
    private String needUpdated;
    /**
     * 内容
     */
    @TableField("content")
    private String content;
    /**
     * 保存的url
     */
    @TableField("app_url")
    private String appUrl;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 发版本的人
     */
    @TableField("create_people")
    private String createPeople;
    /**
     * 发版本的时间 默认当前时间
     */
    @TableField("create_time")
    @JsonSerialize(using= DateJsonSerializer.class)
    @JsonDeserialize(using= DateJsonDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;
    /**
     * 修改版本需要强制更新的人
     */
    @TableField("update_people")
    private String updatePeople;
    /**
     * 修改时间：默认当前时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
