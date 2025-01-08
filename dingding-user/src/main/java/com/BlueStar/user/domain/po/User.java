package com.BlueStar.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账号信息")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "用户密码md5加密")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "关联qq")
    @TableField("qq_id")
    private String qqId;

    @ApiModelProperty(value = "用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "头像照片链接")
    @TableField("avatar_img")
    private String avatarImg;

    @ApiModelProperty(value = "性别（男，女）")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户状态(1 启用 0 禁用)")
    @TableField("stauts")
    private Integer stauts;


}
