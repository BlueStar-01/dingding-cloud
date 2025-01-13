package com.BlueStar.api.client;

import com.BlueStar.api.domain.dto.BookDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "dingding-book")
public interface BookClient {
    @ApiOperation("根据id批量查询书籍")
    @GetMapping("/book/list")
     List<BookDto> getListByIds(@RequestParam List<Long> ids);

    @ApiOperation("查找评分最高啊的10书籍")
    @GetMapping("/book/recommet")
    List<BookDto> recommendedBooks();
}
