package com.BlueStar.book.controller;


import com.BlueStar.book.domain.dto.BookDto;
import com.BlueStar.book.domain.dto.BookPageQueryDto;
import com.BlueStar.book.domain.po.Book;
import com.BlueStar.book.service.IBookService;
import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.domain.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final IBookService bookService;

    @ApiOperation("查找评分最高啊的10书籍")
    @GetMapping("/recommet")
    List<BookDto> recommendedBooks() {
        Page<Book> page = bookService.page(Page.of(1, 10));
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : page.getRecords()) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    /**
     * 根据ID批量获得书籍
     *
     * @param ids
     * @return
     */
    @ApiOperation("根据ID批量获得书籍")
    @GetMapping("/list")
    public List<BookDto> getListByIds(@RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        List<Book> books = bookService.listByIds(ids);
        List<BookDto> res = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            res.add(bookDto);
        }
        return res;
    }

    /**
     * 添加书籍
     *
     * @param book
     * @return
     */
    @PutMapping("/add")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Result addBook(@RequestBody BookDto book) {
        log.info("添加书籍：{}", book);
        bookService.addBook(book);
        return Result.success();
    }

    /**
     * 条件分页查询书籍
     *
     * @param bookPageDto
     * @return
     */
    @PostMapping("/page")
    public Result<Page<Book>> page(@RequestBody BookPageQueryDto bookPageDto) {
        Page<Book> books = bookService.bookPage(bookPageDto);
        return Result.success(books);
    }

    /**
     * 根据id查询书籍详情
     *
     * @param bookId
     * @return
     */
    @GetMapping("/{bookId}")
    public Result<Book> getById(@PathVariable Long bookId) {
        if (bookId == null) {
            return Result.error("ID为空");
        }
        Book book = bookService.getById(bookId);
        return book != null ? Result.success(book) : Result.error("不存在的书籍");
    }

    /**
     * 根据id删除书籍
     *
     * @param bookId
     * @return
     */
    @DeleteMapping("/{bookId}")
    public Result deleteById(@PathVariable Long bookId) {
        bookService.removeById(bookId);
        return Result.success();
    }

    /**
     * 根据ids批量删除书籍
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result deleteByIds(@RequestBody List<Long> ids) {
        bookService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改书籍
     *
     * @param
     * @return
     */
    @PostMapping
    public Result updateBook(@RequestBody BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        boolean updated = bookService.updateById(book);
        if (!updated) {
            return Result.error(MessageConstant.UPDATE_FAILED);
        }
        return Result.success();
    }
}
