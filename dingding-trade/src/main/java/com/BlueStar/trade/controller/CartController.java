package com.BlueStar.trade.controller;


import com.BlueStar.api.client.BookClient;
import com.BlueStar.api.domain.dto.BookDto;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.domain.Result;
import com.BlueStar.trade.domain.dto.CartDto;
import com.BlueStar.trade.domain.po.BookCart;
import com.BlueStar.trade.domain.vo.BookCartVO;
import com.BlueStar.trade.service.IBookCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final IBookCartService cartService;

    private final BookClient bookService;

    /**
     * 添加或减少书籍进购物车(number为负数就是减少,为null就设为默认1)
     *
     * @param cartDto
     * @return
     */
    @PutMapping("/add")
    public Result addBook(@RequestBody CartDto cartDto) {
        cartService.addBook(cartDto);
        return Result.success();
    }

    @DeleteMapping("/clear")
    public Result delAll() {
        //獲得当前用户的所有的购物车数据
        cartService.remove(new LambdaQueryWrapper<BookCart>().eq(BookCart::getUserId, BaseContext.getCurrentId()));
        return Result.success();
    }

    /**
     * 返回当前用户的所有的购物车数据
     *
     * @return
     */
    //todo
    @GetMapping("/list")
    public Result<List<BookCartVO>> list() {
        //查询用户的购物车数据
        List<BookCart> carts = cartService.lambdaQuery().eq(BookCart::getUserId, BaseContext.getCurrentId()).list();
        List<Long> ids = carts.stream().map(BookCart::getBookId).distinct().collect(Collectors.toList());
        List<BookCartVO> ret = getBookCartVOS(ids, carts);
        return Result.success(ret);
    }

    private List<BookCartVO> getBookCartVOS(List<Long> ids, List<BookCart> carts) {
        //查询对应的书籍信息
        List<BookDto> books = bookService.getListByIds(ids);
        List<BookCartVO> ret = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            //添加信息
            BookCart bookCart = carts.get(i);
            BookDto book = books.get(i);
            BookCartVO vo = BookCartVO.builder()
                    .bookName(book.getName())
                    .coverImg(book.getCoverImg())
                    .description(book.getDescription())
                    .number(bookCart.getNumber())
                    .sumPrice(book.getPrice() * bookCart.getNumber())
                    .build();
            ret.add(vo);
        }
        return ret;
    }
}
