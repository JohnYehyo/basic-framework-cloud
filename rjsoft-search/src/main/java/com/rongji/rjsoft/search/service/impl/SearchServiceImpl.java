package com.rongji.rjsoft.search.service.impl;

import com.rongji.rjsoft.search.service.ISearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 搜索服务
 * @author: JohnYehyo
 * @create: 2021-11-16 16:14:08
 */
@Service
public class SearchServiceImpl implements ISearchService {


    @Override
    public Object addIndex() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }
}
