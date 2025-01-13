package com.BlueStar.collection.service.impl;

import com.BlueStar.collection.domain.po.BookCollection;
import com.BlueStar.collection.mapper.BookCollectionMapper;
import com.BlueStar.collection.service.IBookCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录了收藏集里的书 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Service
public class BookCollectionServiceImpl extends ServiceImpl<BookCollectionMapper, BookCollection> implements IBookCollectionService {

}
