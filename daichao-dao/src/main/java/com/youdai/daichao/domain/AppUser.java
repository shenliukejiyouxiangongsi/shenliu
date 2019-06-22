package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Data
@Accessors(chain = true)
@TableName("app_user")
@ToString
public class AppUser extends Model<AppUser> implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "a_uid",type = IdType.AUTO)
    private Long aUid;
    @TableField("a_uphone")
    private String aUphone;
    private String password;
    @TableField("channel_id")
    private int channelId;
    private int status;
    @TableField("pro_key")
    private String proKey;
    private String token;
    @TableField("create_time")
    private String createTime;
    @TableField("create_user")
    private String createUser;
    @TableField("update_time")
    private String updateTime;
    @TableField("equipment_flag")
    private int equipmentFlag;
    @TableField("is_show")
    private int isShow;

    @Override
    protected Serializable pkVal() {
        return this.aUid;
    }

}
