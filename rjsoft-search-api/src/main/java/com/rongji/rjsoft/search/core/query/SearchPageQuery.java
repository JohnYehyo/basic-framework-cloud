package com.rongji.rjsoft.search.core.query;

import com.rongji.core.query.common.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * @description: 搜索分页查询
 * @author: JohnYehyo
 * @create: 2021-10-12 15:33:41
 */
@Data
public class SearchPageQuery<T> extends PageQuery {

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
     * 搜索值
     */
    private List<String> values;

    /**
     * 泛型
     */
    private Class<T> clazz;

    /**
     * 时间键
     */
    private String timeKey;

    /**
     * 开始时间
     */
    private Long sTime;

    /**
     * 结束时间
     */
    private Long eTime;

    /**
     * 行政区划键
     */
    private String branchKey;

    /**
     * 行政区划值
     */
    private String branchCode;

}
