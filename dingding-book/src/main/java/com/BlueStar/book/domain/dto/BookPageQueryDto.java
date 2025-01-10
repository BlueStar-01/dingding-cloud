package com.BlueStar.book.domain.dto;

import com.BlueStar.dingding.domain.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "书籍分页查询条件")
public class BookPageQueryDto  extends PageQuery {

    @ApiModelProperty(value = "书名关键词匹配")
    private String name;

    @ApiModelProperty(value = "作者匹配")
    private String author;

    @ApiModelProperty(value = "创建开始时间")
    private LocalDateTime createStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDateTime createEndTime;

    @ApiModelProperty(value = "更新开始时间")
    private LocalDateTime updateStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDateTime updateEndTime;

    @ApiModelProperty(value = "最小价格")
    private Integer minPrice;

    @ApiModelProperty(value = "最大价格")
    private Integer maxPrice;

    @ApiModelProperty(value = "书籍分类")
    private Long typeId;


}
