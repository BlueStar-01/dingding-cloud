package com.BlueStar.collection.controller;

import com.BlueStar.api.client.BookClient;
import com.BlueStar.api.domain.dto.BookDto;
import com.BlueStar.api.domain.po.Book;
import com.BlueStar.collection.domain.dto.BookCollectionDto;
import com.BlueStar.collection.domain.po.BookCollection;
import com.BlueStar.collection.domain.po.Collection;
import com.BlueStar.collection.service.IBookCollectionService;
import com.BlueStar.collection.service.ICollectionService;
import com.BlueStar.dingding.constant.CollectionConstant;
import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.domain.Result;
import com.BlueStar.dingding.exception.DataException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/BookCollection")
public class BookCollectionController {

    private static final Logger log = LoggerFactory.getLogger(BookCollectionController.class);
    private final IBookCollectionService bookCollectionService;

    private final BookClient bookService;

    private final ICollectionService collectionService;

    /**
     * 获得推荐书籍
     *
     * @return
     */
    //todo 简易版本 找出评分最高的10本书籍
    @GetMapping("/recommendedBooks")
    public Result<List<BookDto>> recommendedBooks() {
        List<BookDto> books = bookService.recommendedBooks();
        return Result.success("简易版本 找出评分最高的10本书籍", books);
    }

    /**
     * 获得收藏夹中的所有书籍
     *
     * @param collectionId
     * @return
     */
    @GetMapping("/list")
    public Result<List<BookDto>> getList(@RequestParam Long collectionId) {
        //检查ID问题
        collectionId = checkOrCreatDeflateCollection(collectionId);
        //查询所有的数据
        List<BookCollection> list = bookCollectionService.lambdaQuery()
                .eq(BookCollection::getCollectionId, collectionId)
                .list();
        //获得所有的书籍id
        List<Long> bookIds = new ArrayList<>();
        list.forEach(s -> bookIds.add(s.getBookId()));
        //查询书籍
        if (bookIds.isEmpty()) {
            return Result.success(MessageConstant.COL_NOT_FOUND, null);
        }
        List<BookDto> books = bookService.getListByIds(bookIds);
        return Result.success(books);
    }

    /**
     * 检查收藏夹id，创建默认收藏夹
     *
     * @param collectionId
     * @return
     */
    private Long checkOrCreatDeflateCollection(Long collectionId) {
        Collection byId = collectionService.getById(collectionId);
        //如果为空，或者没有权限，就查找默认收藏夹
        if (byId == null || !Objects.equals(byId.getUserId(), BaseContext.getCurrentId())) {
            Collection collection = null;
            try {
                collection = collectionService.lambdaQuery().eq(Collection::getUserId, BaseContext.getCurrentId()).list().get(0);
                collectionId = collection.getId();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                //无默认就创建一个
                Collection temp = new Collection();
                temp.setUserId(BaseContext.getCurrentId())
                        .setName(CollectionConstant.DEFAULT_COLLECTION_NAME)
                        .setCreateTime(LocalDateTime.now())
                        .setUpdateTime(LocalDateTime.now())
                        .setDescription(CollectionConstant.DEFAULT_DESCRIPTION);
                boolean save = collectionService.save(temp);
                if (!save) {
                    throw new DataException(MessageConstant.CREATE_DEFAULT_COLLECTION_FAILED);
                }
                collectionId = temp.getId();
            }
        }
        return collectionId;
    }


    /**
     * 把书籍添加进收藏夹
     *
     * @param bookCollectionDto
     * @return
     */
    @PutMapping("/add")
    public Result addBookCollection(@RequestBody BookCollectionDto bookCollectionDto) {
        //检查收藏夹问题
        bookCollectionDto.setCollectionId(checkOrCreatDeflateCollection(bookCollectionDto.getCollectionId()));

        BookCollection controller = new BookCollection();
        BeanUtils.copyProperties(bookCollectionDto, controller);
        //检查书籍和收藏夹是否存在
        List<BookDto> bookCount = bookService.getListByIds(List.of(bookCollectionDto.getBookId()));
        Long collectionCount = Long.valueOf(collectionService.lambdaQuery().eq(Collection::getId, bookCollectionDto.getCollectionId()).count());
        //添加进数据库
        if (bookCount.isEmpty() || collectionCount <= 0) {
            return Result.error(MessageConstant.BOOK_OR_COLLECTION_NOT_FOUND);
        }
        //检查是否重复添加
        Long cowCount = Long.valueOf(bookCollectionService.lambdaQuery()
                .eq(BookCollection::getBookId, bookCollectionDto.getBookId())
                .eq(BookCollection::getCollectionId, bookCollectionDto.getCollectionId())
                .count());
        if (cowCount != 0) {
            return Result.error(MessageConstant.COL_ALREADY_EXISTS);
        }
        bookCollectionService.save(controller);
        return Result.success();
    }

    /**
     * 把书籍移除收藏夹
     *
     * @param bookCollectionDto
     * @return
     */
    // @Transactional
    @DeleteMapping("/del")
    public Result delBookCollection(@RequestBody BookCollectionDto bookCollectionDto) {
        //检查收藏夹问题
        bookCollectionDto.setCollectionId(checkOrCreatDeflateCollection(bookCollectionDto.getCollectionId()));

        //查询id
        List<BookCollection> list = bookCollectionService.lambdaQuery()
                .eq(BookCollection::getCollectionId, bookCollectionDto.getCollectionId())
                .eq(BookCollection::getBookId, bookCollectionDto.getBookId())
                .list();
        log.info("删除实体:{}", list);
        bookCollectionService.removeByIds(list.stream().map(BookCollection::getId).collect(Collectors.toList()));
        return Result.success();
    }

}
