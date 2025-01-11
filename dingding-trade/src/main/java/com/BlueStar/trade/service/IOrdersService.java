package com.BlueStar.trade.service;


import com.BlueStar.trade.domain.po.Orders;
import com.BlueStar.trade.domain.vo.OrderDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
public interface IOrdersService extends IService<Orders> {

    Orders cleanCart();

    List<OrderDetailVO> getOrderDetail(Long orderId);
}
