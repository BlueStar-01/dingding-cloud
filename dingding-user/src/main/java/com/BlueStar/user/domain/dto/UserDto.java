package com.BlueStar.user.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ApiModelProperty(value = "用户账号信息")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "用户密码")
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
}
