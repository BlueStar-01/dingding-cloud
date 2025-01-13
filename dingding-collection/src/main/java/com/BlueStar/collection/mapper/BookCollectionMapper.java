package com.BlueStar.collection.mapper;


import com.BlueStar.collection.domain.po.BookCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 记录了收藏集里的书 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Mapper
public interface BookCollectionMapper extends BaseMapper<BookCollection> {

}
