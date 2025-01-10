package com.BlueStar.book.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CatalogVO {
    @ApiModelProperty(value = "章节ID")
    @NotNull(message = "章节ID不为空")
    private Long catalogId;

    @ApiModelProperty(value = "章节标题")
    @NotNull(message = "章节标题不为空")
    private String title;

    @ApiModelProperty(value = "章节顺序")
    @NotNull(message = "章节顺序不为空")
    private Integer orderNum;
}
