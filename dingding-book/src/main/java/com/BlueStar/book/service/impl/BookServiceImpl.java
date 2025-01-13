package com.BlueStar.book.service.impl;


import com.BlueStar.book.domain.dto.BookDto;
import com.BlueStar.book.domain.dto.BookPageQueryDto;
import com.BlueStar.book.domain.po.Book;
import com.BlueStar.book.mapper.BookMapper;
import com.BlueStar.book.service.IBookService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 书籍信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 书籍条件查询
     *
     * @param dto
     * @return
     */
    @Override
    public List listByBook(Book dto) {
        log.info("等待实现");
        return null;
    }

    /**
     * 添加书籍
     *
     * @param bookdto
     */
    @Transactional
    @Override
    public void addBook(BookDto bookdto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookdto, book);
        bookMapper.insert(book);
    }

    /**
     * 条件分页查询
     *
     * @param bookPageDto
     * @return
     */
    //todo 分页查询实现中
    @Override
    public Page<Book> bookPage(BookPageQueryDto bookPageDto) {
        //构建条件'
        Page<Book> page = this.lambdaQuery()
                //查询的字段
                //.select(Book::getName, Book::getAuthor, Book::getPrice)
                //姓名作者筛选
                .like(!StringUtils.isEmpty(bookPageDto.getName()), Book::getName, bookPageDto.getName())
                .like(!StringUtils.isEmpty(bookPageDto.getAuthor()), Book::getAuthor, bookPageDto.getAuthor())
                //创建时间范围筛选
                .gt(bookPageDto.getCreateStartTime() != null, Book::getCreateTime, bookPageDto.getCreateStartTime())
                .lt(bookPageDto.getCreateEndTime() != null, Book::getCreateTime, bookPageDto.getCreateEndTime())
                //更新时间范围筛选
                .gt(bookPageDto.getUpdateStartTime() != null, Book::getUpdateTime, bookPageDto.getUpdateStartTime())
                .lt(bookPageDto.getUpdateEndTime() != null, Book::getUpdateTime, bookPageDto.getUpdateEndTime())
                //价格范围筛选
                .gt(bookPageDto.getMinPrice() != null, Book::getPrice, bookPageDto.getMinPrice())
                .lt(bookPageDto.getMaxPrice() != null, Book::getPrice, bookPageDto.getMaxPrice())
                //排序
                .page(bookPageDto.toMpPageDefaultSortByCreateTimeDesc());
        if (page.getTotal() <= 20) {
            //太少就添加一些数据
            List<Book> list = this.page(Page.of(1, 20)).getRecords();
            List<Book> books = page.getRecords();
            if (books != null && !books.isEmpty()) {
                books.addAll(list);
                //去重
                books = books.stream().distinct().collect(Collectors.toList());
            } else {
                books = list;
            }
            page.setRecords(books);
            page.setTotal(page.getRecords().size());
        }
        return page;

    }

}
