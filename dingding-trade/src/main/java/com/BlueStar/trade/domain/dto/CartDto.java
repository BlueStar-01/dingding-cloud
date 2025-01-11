package com.BlueStar.trade.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    @ApiModelProperty("添加的书籍ID")
    private Long bookId;
    @ApiModelProperty("添加的书籍数量")
    private Integer number;
}
