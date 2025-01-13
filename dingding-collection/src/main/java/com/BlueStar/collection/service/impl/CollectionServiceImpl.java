package com.BlueStar.collection.service.impl;


import com.BlueStar.collection.domain.po.BookCollection;
import com.BlueStar.collection.domain.po.Collection;
import com.BlueStar.collection.mapper.BookCollectionMapper;
import com.BlueStar.collection.mapper.CollectionMapper;
import com.BlueStar.collection.service.ICollectionService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收藏夹表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements ICollectionService {

    @Autowired
    private BookCollectionMapper bookCollectionMapper;

    /**
     * 删除收藏夹
     *
     * @param collectionId
     * @return
     */
    @Override
    public Boolean reById(Long collectionId) {
        //删除收藏夹中的书籍
        LambdaQueryChainWrapper<BookCollection> BCwrapper = new LambdaQueryChainWrapper<>(bookCollectionMapper);
        BCwrapper.eq(BookCollection::getCollectionId, collectionId);
        int deleted = bookCollectionMapper.delete(BCwrapper);
        //删除收藏夹
        LambdaQueryChainWrapper<Collection> wrapper = lambdaQuery().eq(Collection::getId, collectionId);
        return remove(wrapper);
    }
}
