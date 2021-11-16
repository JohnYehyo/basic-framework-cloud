package com.rongji.rjsoft.system.controller.commom;

import com.rongji.core.vo.ResponseVo;
import com.rongji.rjsoft.search.service.ISearchService;
import com.yskj.service.org.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2021-11-16 16:42:53
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private IOrgService orgService;

    @Autowired
    private ISearchService searchService;

    @GetMapping
    public Object test() {
//        return orgService.getLinkState();
        return searchService.addIndex();
    }
}
