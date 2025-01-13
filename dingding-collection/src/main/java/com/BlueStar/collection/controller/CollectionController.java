package com.BlueStar.collection.controller;


import com.BlueStar.collection.domain.dto.CollectionDto;
import com.BlueStar.collection.domain.po.Collection;
import com.BlueStar.collection.domain.vo.CollectionVO;
import com.BlueStar.collection.service.ICollectionService;
import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final ICollectionService collectionService;


    /**
     * 查询当前用户的所有的收藏夹
     */
    @GetMapping("/list")
    public Result<List<CollectionVO>> getCollection() {
        //从线程变量中获得用户id
        Long userId = BaseContext.getCurrentId();
        //从数据库查询当前用户的收藏夹信息,按照修改时间。
        List<Collection> list = collectionService.lambdaQuery()
                .eq(Collection::getUserId, BaseContext.getCurrentId())
                .list();
        List<CollectionVO> ret = new ArrayList<>();
        //转换到view对象。
        list.forEach(t -> {
            CollectionVO vo = new CollectionVO();
            BeanUtils.copyProperties(t, vo);
            ret.add(vo);
        });
        return Result.success(ret);
    }


    /**
     * 修改收藏夹
     *
     * @param dto
     * @return
     */
    @PostMapping
    public Result postCollection(@RequestBody Long id, @RequestBody CollectionDto dto) {
        Collection collection = new Collection();
        BeanUtils.copyProperties(dto, collection);

        collection.setId(id);
        collectionService.updateById(collection);
        return Result.success();
    }

    /**
     * 添加收藏夹
     *
     * @param dto
     * @return
     */
    @PutMapping
    public Result addCollection(@RequestBody CollectionDto dto) {
        Collection collection = new Collection();
        BeanUtils.copyProperties(dto, collection);
        //获得当前的用户的id
        collection.setUserId(BaseContext.getCurrentId());
        collectionService.save(collection);
        return Result.success();
    }


    /**
     * 获得收藏夹详细信息
     *
     * @param collectionId
     * @return
     */
    @GetMapping("/{collectionId}")
    public Result<Collection> getCollection(@PathVariable Long collectionId) {
        Collection byId = collectionService.getById(collectionId);
        return Result.success(byId);
    }

    /**
     * 根据收藏夹id删除收藏夹
     *
     * @param collectionId
     * @return
     */
    @DeleteMapping("/{collectionId}")
    public Result delCollection(@PathVariable Long collectionId) {
        //查询是否有存在
        if (collectionId == null || collectionService.getById(collectionId) == null) {
            return Result.error(MessageConstant.COL_NOT_FOUND);
        }
        Boolean del = collectionService.reById(collectionId);
        return Result.success();
    }


}
