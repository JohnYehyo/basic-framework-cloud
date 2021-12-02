package com.rongji.rjsoft.system.controller.search;

import com.rongji.rjsoft.search.core.query.SearchMultiPageQuery;
import com.rongji.rjsoft.search.core.query.SearchPageQuery;
import com.rongji.rjsoft.search.core.query.SearchQuery;
import com.rongji.rjsoft.search.service.ISearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description: 搜索工具
 * @author: JohnYehyo
 * @create: 2021-10-15 12:51:55
 */
@Api(tags = "搜索引擎-搜索工具")
@RestController
@RequestMapping(value = "search")
public class SearchController {

    @Autowired
    private ISearchService esService;

    /**
     * 多条件搜索
     * @param searchPageQuery 条件对象
     * @return 分页结果
     */
    @ApiOperation(value = "多条件搜索")
    @GetMapping(value = "list")
    public Object list(@Valid SearchPageQuery searchPageQuery) {
        return esService.queryForlist(searchPageQuery);
    }

    /**
     * 详情
     * @param searchQuery 条件对象
     * @return 分页结果
     */
    @ApiOperation(value = "搜索详情")
    @GetMapping(value = "info")
    public Object list(@Valid SearchQuery searchQuery) {
        return esService.queryForEntity(searchQuery);
    }

    /**
     * 多字段包含关键字搜索
     * @param searchMultiPageQuery 条件对象
     * @return 分页结果
     */
    @ApiOperation(value = "多字段包含关键字搜索")
    @GetMapping(value = "multiSelect")
    public Object multiSelect(@Valid SearchMultiPageQuery searchMultiPageQuery) {
        return esService.multiSelect(searchMultiPageQuery);
    }
}
