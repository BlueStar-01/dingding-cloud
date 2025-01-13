package com.BlueStar.collection.service;


import com.BlueStar.collection.domain.po.Collection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收藏夹表 服务类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
public interface ICollectionService extends IService<Collection> {

    Boolean reById(Long collectionId);
}
