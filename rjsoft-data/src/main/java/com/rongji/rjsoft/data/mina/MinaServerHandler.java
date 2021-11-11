package com.rongji.rjsoft.data.mina;

import com.rongji.rjsoft.data.service.IDemoService;
import com.yskj.minaserver.BaseServerHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2021-11-10 10:12:07
 */
public class MinaServerHandler extends BaseServerHandler {

    /**
     * 这里注入要发布的service
     */
    @Autowired
    private IDemoService demoService;

    @PostConstruct
    private void PostConstruct() {
        //填写自己要发布的service 多个service逗号隔开
        servers = new Object[]{demoService};
        //服务name
        serviceName = "search-service";
    }
}