package com.BlueStar.trade.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDetailVO {
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
