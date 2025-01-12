package com.BlueStar.api.domain.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookDto implements Serializable {
    @ApiModelProperty(value = "书籍的id")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "书籍的名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "书籍的封面图片URL或路径")
    @TableField("cover_img")
    private String coverImg;

    @ApiModelProperty(value = "书籍的详细描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "书籍的作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "出版社的名称")
    @TableField("publishing")
    private String publishing;

    @ApiModelProperty(value = "书籍的出版日期")
    @TableField("publishing_date")
    private LocalDateTime publishingDate;

    @ApiModelProperty(value = "记录创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "记录最后更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private Integer price;

    @ApiModelProperty(value = "书籍分类ID")
    @TableField("type_id")
    private Long typeId;

    @ApiModelProperty(value = "是否为新书（0：否，1：是）")
    @TableField("is_new")
    private Boolean isNew;

    @ApiModelProperty(value = "平均评分")
    @TableField("rating")
    @DecimalMin(value = "0", message = "评分不能小于0")
    @DecimalMax(value = "10", message = "评分不能超过10")
    private BigDecimal rating;
}
