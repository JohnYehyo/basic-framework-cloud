package com.rongji.rjsoft.search.handler;

import com.rongji.rjsoft.search.service.ISearchService;
import com.yskj.minaserver.BaseServerHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2021-11-16 16:19:28
 */
public class MinaServerHandler extends BaseServerHandler {

    /**
     * 这里注入要发布的service
     */
    @Autowired
    private ISearchService searchService;

    @PostConstruct
    private void PostConstruct() {
        //填写自己要发布的service 多个service逗号隔开
        servers = new Object[]{searchService};
        //服务name
        serviceName = "search_sever";
    }
}
