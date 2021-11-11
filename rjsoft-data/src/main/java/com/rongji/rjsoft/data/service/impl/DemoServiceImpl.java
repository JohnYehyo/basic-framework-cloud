package com.rongji.rjsoft.data.service.impl;

import com.rongji.rjsoft.data.service.IDemoService;
import com.rongji.rjsoft.core.vo.vo.ResponseVo;
import org.springframework.stereotype.Service;

/**
 * @description: 服务调用示例
 * @author: JohnYehyo
 * @create: 2021-11-10 20:02:17
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Override
    public Object oneDemo() {
        return ResponseVo.success();
    }
}
