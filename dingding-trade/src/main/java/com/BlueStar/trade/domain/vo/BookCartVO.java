package com.BlueStar.trade.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCartVO {

    @ApiModelProperty("书名")
    private String bookName;

    @ApiModelProperty("书籍封面链接")
    private String coverImg;

    @ApiModelProperty("书籍描述")
    private String description;

    @ApiModelProperty("购物车中的数量")
    private Integer number;

    @ApiModelProperty("总价格")
    private Integer sumPrice;
}
