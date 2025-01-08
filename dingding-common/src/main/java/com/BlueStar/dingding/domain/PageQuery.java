package com.BlueStar.dingding.domain;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel(description = "分页查询对象基类")
//开启链式set变量风格
@Accessors(chain = true)
public class PageQuery implements Serializable {
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    @ApiModelProperty("页码")
    private Integer pageNo = DEFAULT_PAGE_NUM;
    @ApiModelProperty("查询的大小")
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    @ApiModelProperty("排序字段名称")
    private String orderBy;
    @ApiModelProperty("是否升序排序")
    private Boolean isAsc = true;

    //返回分页的起始下标
    public Integer getFromIndex() {
        return (pageNo - 1) * pageSize;
    }

    public <T> Page<T> toMapPage(OrderItem... orderItems) {
        Page<T> page = new Page<>(pageNo, pageSize);
        //判断是否指定的排序字段
        if (orderItems != null && orderItems.length > 0) {
            page.addOrder(orderItems);
        }
        // 判断是否有排序字段
        if (StrUtil.isNotEmpty(orderBy)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(isAsc);
            orderItem.setColumn(orderBy);
            page.addOrder(orderItem);
        }
        return page;
    }


    public <T> Page<T> toMapPage(String orderByField, Boolean isAsc) {
        if (StringUtils.isBlank(orderBy)) {
            orderBy = orderByField;
            this.isAsc = isAsc != null ? isAsc : false;
        }
        Page<T> page = new Page<>(pageNo, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(orderBy);
        orderItem.setAsc(this.isAsc);
        page.addOrder(orderItem);
        return page;
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMapPage("create_time", false);
    }
}
