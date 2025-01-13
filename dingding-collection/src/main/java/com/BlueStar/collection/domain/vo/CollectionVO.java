package com.BlueStar.collection.domain.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CollectionVO {
    /**
     * 收藏夹id
     */
    @NotNull(message="[]不能为空")
    private Long id;
    /**
     * 收藏夹名
     */
    @NotBlank(message="[收藏夹名]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String name;
    /**
     * 收藏夹描述
     */
    @Size(max= 500,message="编码长度不能超过500")
    @Length(max= 500,message="编码长度不能超过500")
    private String description;
    /**
     * 封面图片
     */
    @Size(max= 500,message="编码长度不能超过500")
    @Length(max= 500,message="编码长度不能超过500")
    private String coverImg;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *更新时间
     */
    private Date updateTime;
}
