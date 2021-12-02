package com.rongji.rjsoft.search.core.query;

import com.rongji.core.query.common.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 搜索分页查询
 * @author: JohnYehyo
 * @create: 2021-10-12 15:33:41
 */
@Data
public class SearchMultiPageQuery<T> extends PageQuery {

    /**
     * 索引
     */
    private String indexName;

    /**
     * 类型
     */
    private String indexType;

    /**
     * 搜索条件
     */
    private SearchMultiBaseQuery param;


    /**
     * 泛型
     */
    private Class<T> clazz;

}
