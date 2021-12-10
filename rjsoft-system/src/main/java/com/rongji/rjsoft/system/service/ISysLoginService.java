package com.rongji.rjsoft.system.service;

import com.rongji.core.ao.system.LoginAo;
import com.rongji.core.vo.ResponseVo;

/**
 * @description: 登录退出
 * @author: JohnYehyo
 * @create: 2021-04-26 17:28:25
 */
public interface ISysLoginService {

    /**
     * 登录
     * @param loginAo 请求参数体
     * @return 登录情况
     */
    ResponseVo login(LoginAo loginAo);

    /**
     * 中心登录
     * @param loginAo
     * @return
     */
    ResponseVo centerLogin(LoginAo loginAo);

}
