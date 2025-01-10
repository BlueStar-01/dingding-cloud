package com.BlueStar.book.service;


import com.BlueStar.book.domain.dto.BookDto;
import com.BlueStar.book.domain.dto.BookPageQueryDto;
import com.BlueStar.book.domain.po.Book;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 * 书籍信息表 服务类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
public interface IBookService extends IService<Book> {


    List listByBook(Book dto);

    void addBook(BookDto book);

    Page<Book> bookPage(BookPageQueryDto bookPageDto);
}
