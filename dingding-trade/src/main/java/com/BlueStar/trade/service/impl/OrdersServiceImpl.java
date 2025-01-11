package com.BlueStar.trade.service.impl;


import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.constant.PayConstant;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.exception.DataException;
import com.BlueStar.trade.domain.po.BookCart;
import com.BlueStar.trade.domain.po.OrderDetail;
import com.BlueStar.trade.domain.po.Orders;
import com.BlueStar.trade.domain.vo.OrderDetailVO;
import com.BlueStar.trade.mapper.OrdersMapper;
import com.BlueStar.trade.service.IBookCartService;
import com.BlueStar.trade.service.IOrderDetailService;
import com.BlueStar.trade.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    private final IBookCartService cartService;
    private final IBookService bookService;
    private final IOrderDetailService detailService;


    /**
     * 清除购物车并生成订单
     *
     * @return
     */
    @Override
//    @Transactional //todo 分布式事务待实现
    public Orders cleanCart() {
        //查询购物车数据
        List<BookCart> carts = cartService.lambdaQuery().eq(BookCart::getUserId, BaseContext.getCurrentId())
                .list();
        //清除购物车
        if (carts.size() <= 0) {
            throw new DataException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        cartService.removeBatchByIds(carts);
        //生成订单
        Orders orders = new Orders().setCreateTime(LocalDateTime.now())
                .setUserId(BaseContext.getCurrentId())
                .setStatus(PayConstant.NO_PAY);
        //保存
        save(orders);
        //查询订单是否生成成功
        orders = getById(orders);
        if (orders == null) {
            throw new DataException(MessageConstant.ORDER_NOT_FOUND);
        }
        //添加订单详细数据
        List<OrderDetail> details = new ArrayList<>();
        for (BookCart bookCart : carts) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orders.getId());
            detail.setBookId(bookCart.getBookId());
            detail.setNumber(bookCart.getNumber());
            detail.setCreateTime(LocalDateTime.now());
            detail.setUpdateTime(LocalDateTime.now());
            details.add(detail);
        }
        if (!detailService.saveBatch(details)) {
            throw new DataException(MessageConstant.UPDATE_FAILED);
        }
        return orders;
    }

    @Override
    public List<OrderDetailVO> getOrderDetail(Long orderId) {
        //只有当前用户才可以看自己的订单
        Long userId = getById(orderId).getUserId();
        if (userId != BaseContext.getCurrentId()) {
            throw new AccountException(MessageConstant.USER_NOT_PERMISSION);
        }
        //查询订单详情
        List<OrderDetail> details = detailService.lambdaQuery().eq(OrderDetail::getOrderId, orderId).list();
        //查询书籍信息
        Set<Long> bookIds = details.stream().map(OrderDetail::getBookId).collect(Collectors.toSet());
        List<Book> books = bookService.listByIds(bookIds);

        //构建订单信息
        List<OrderDetailVO> detailVOList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            Book book = books.get(i);
            OrderDetail detail = details.get(i);
            OrderDetailVO vo = new OrderDetailVO();
            vo = vo.setBookName(book.getName())
                    .setCoverImg(book.getCoverImg())
                    .setDescription(book.getDescription())
                    .setNumber(detail.getNumber())
                    .setSumPrice(detail.getNumber() * book.getPrice());
            detailVOList.add(vo);
        }
        return detailVOList;
    }
}
