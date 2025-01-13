package com.BlueStar.collection.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CollectionDto implements Serializable {

    /**
     * 收藏夹id
     */
    private Long id;
    /**
     * 收藏夹名
     */
    @NotBlank(message = "[收藏夹名]不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    @Length(max = 20, message = "编码长度不能超过20")
    private String name;
    /**
     * 收藏夹描述
     */
    @Size(max = 500, message = "编码长度不能超过500")
    @Length(max = 500, message = "编码长度不能超过500")
    private String description;
    /**
     * 封面图片
     */
    @Size(max = 500, message = "编码长度不能超过500")
    @Length(max = 500, message = "编码长度不能超过500")
    private String coverImg;
}
