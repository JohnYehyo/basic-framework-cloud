package com.rongji.rjsoft.search.core.query;

import lombok.Data;

import java.util.List;

/**
 * @description: 搜索查询
 * @author: JohnYehyo
 * @create: 2021-10-12 15:33:41
 */
@Data
public class SearchQuery<T> {

    /**
     * 索引
     */
    private String indexName;

    /**
     * 类型
     */
    private String indexType;

    /**
     * 搜索对象
     */
    private List<SearchBaseQuery> param;

    /**
     * 泛型
     */
    private Class<T> clazz;
}
