package com.BlueStar.trade.service;


import com.BlueStar.trade.domain.dto.CartDto;
import com.BlueStar.trade.domain.po.BookCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物内容表 服务类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
public interface IBookCartService extends IService<BookCart> {

    void addBook(CartDto cartDto);
}
