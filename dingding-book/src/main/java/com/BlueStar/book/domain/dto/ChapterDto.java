package com.BlueStar.book.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChapterDto {

    @ApiModelProperty(value = "书籍ID")
    @NotNull(message = "书籍ID不为空")
    private Long bookId;

    @ApiModelProperty(value = "章节标题")
    @NotNull(message = "章节标题不为空")
    private String title;

    @ApiModelProperty(value = "章节内容")
    @NotNull(message = "章节内容不为空")
    private String content;

    @ApiModelProperty(value = "章节顺序")
    private Integer orderNum;

}
