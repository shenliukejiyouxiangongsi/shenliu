package com.youdai.daichao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
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
    @TableField(value = "a_uphone",strategy = FieldStrategy.NOT_EMPTY)
    private String aUphone;
    private String password;
    @TableField("channel_id")
    private Integer channelId;
    private Integer status;
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
    private Integer equipmentFlag;
    @TableField("is_show")
    private Integer isShow;
    @TableField(exist=false)
    private String channelName;
    @TableField("userRecord_id")
    private Long userRecordId;

    @Override
    protected Serializable pkVal() {
        return this.aUid;
    }

}
