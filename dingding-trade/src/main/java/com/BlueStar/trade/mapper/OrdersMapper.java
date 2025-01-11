package com.BlueStar.trade.mapper;


import com.BlueStar.trade.domain.po.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
