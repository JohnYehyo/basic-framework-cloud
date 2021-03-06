package com.rongji.core.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: ้็จๅ้กต
 * @author: JohnYehyo
 * @create: 2021-08-17 11:14:50
 */
@Data
public class CommonPage<T> {

    private Long current;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;
}
