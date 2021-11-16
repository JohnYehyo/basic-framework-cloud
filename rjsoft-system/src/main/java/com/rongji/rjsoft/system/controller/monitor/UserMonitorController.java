package com.rongji.rjsoft.system.controller.monitor;

import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.core.constants.Constants;
import com.rongji.core.enums.ResponseEnum;
import com.rongji.core.query.monitor.user.UserMonitorQuery;
import com.rongji.rjsoft.system.service.IUserMonitorService;
import com.rongji.core.vo.CommonPage;
import com.rongji.core.vo.ResponseVo;
import com.rongji.core.vo.monitor.user.UserMonitorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户监控
 * @author: JohnYehyo
 * @create: 2021-09-06 11:02:06
 */
@Api(tags = "监控管理-用户监控")
@RestController
@RequestMapping(value = "userMonitor")
@AllArgsConstructor
public class UserMonitorController {

    private final IUserMonitorService userMonitorService;

    private final RedisCache redisCache;

    /**
     * 用户监控列表
     *
     * @param userMonitorQuery 用户监控查询
     * @return 用户监控列表
     */
    @ApiOperation(value = "用户监控列表")
    @GetMapping(value = "list")
    public Object pages(UserMonitorQuery userMonitorQuery) {
        CommonPage<UserMonitorVo> page = userMonitorService.getListOfUser(userMonitorQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "强制退出")
    @ApiImplicitParam(value = "用户唯一码", name = "uuid", required = true)
    @PostMapping(value = "forceLogout/{uuid}")
    public Object forceLogout(@PathVariable("uuid") String uuid){
        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + uuid);
        return ResponseVo.success();
    }

}
