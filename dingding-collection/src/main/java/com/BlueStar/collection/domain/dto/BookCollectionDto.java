package com.BlueStar.collection.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookCollectionDto {

    @ApiModelProperty(value = "书籍id")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty(value = "收藏夹id")
    @TableField("collection_id")
    private Long collectionId;

}
