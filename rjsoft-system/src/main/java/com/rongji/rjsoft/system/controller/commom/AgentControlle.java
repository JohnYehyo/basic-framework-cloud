package com.rongji.rjsoft.system.controller.commom;

import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.core.enums.ResponseEnum;
import com.rongji.core.exception.BusinessException;
import com.rongji.rjsoft.system.service.ISysDictDataService;
import com.rongji.core.vo.ResponseVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 探活
 * @author: JohnYehyo
 * @create: 2021-10-20 14:28:28
 */
@RestController
@RequestMapping(value = "agent")
@AllArgsConstructor
public class AgentControlle {

    private final ISysDictDataService sysDictDataService;

    private final RedisCache redisCache;

    @GetMapping
    public Object agent(){

        try {
            Object test = redisCache.getCacheObject("test");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.REDIS_EXCEPTION);
        }

        try {
            Object test = sysDictDataService.getById(1L);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.DATABASE_EXCEPTION);
        }

        return ResponseVo.success();
    }
}
