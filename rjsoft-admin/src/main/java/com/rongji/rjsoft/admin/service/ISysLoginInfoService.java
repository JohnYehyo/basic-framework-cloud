package com.rongji.rjsoft.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.core.entity.monitor.SysLoginInfo;
import com.rongji.rjsoft.core.query.query.monitor.login.LoginInfoQuery;
import com.rongji.rjsoft.core.vo.vo.CommonPage;
import com.rongji.rjsoft.core.vo.vo.monitor.login.LoginInfoVo;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-09
 */
public interface ISysLoginInfoService extends IService<SysLoginInfo> {

    /**
     * 记录访问
     * @param userName 登录用户
     * @param result 登录结果
     * @param msg 登录提示
     */
    void saveLoginInfo(String userName, int result, String msg);

    /**
     * 登录信息分页列表
     * @param loginInfoQuery 查询条件
     * @return 分页结果
     */
    CommonPage<LoginInfoVo> getPages(LoginInfoQuery loginInfoQuery);
}
