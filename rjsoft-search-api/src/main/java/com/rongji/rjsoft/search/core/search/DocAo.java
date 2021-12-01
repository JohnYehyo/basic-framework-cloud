package com.rongji.rjsoft.search.core.search;

import lombok.Data;

/**
 * @description: 文档参数
 * @author: JohnYehyo
 * @create: 2021-12-01 13:38:16
 */
@Data
public class DocAo {

    /**
     * 索引
     */
    private String index;

    /**
     * 类型
     */
    private String type;


    /**
     * 文档JSON文本
     */
    private String content;

    /**
     * 文档id
     */
    private Long id;
}
