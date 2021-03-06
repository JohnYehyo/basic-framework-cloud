package com.rongji.rjsoft.system.service;

import com.rongji.core.ao.system.PasswordAo;
import com.rongji.core.ao.system.PersonInfoAo;
import com.rongji.core.vo.ResponseVo;
import com.rongji.core.vo.system.person.PersonInfoVo;

/**
 * @description: 个人中心
 * @author: JohnYehyo
 * @create: 2021-09-09 16:26:46
 */
public interface ISysPersonService {

    /**
     * 查询个人信息
     *
     * @return
     */
    PersonInfoVo getPerson();

    /**
     * 修改个人信息
     *
     * @param personInfoAo 个人信息
     * @return 修改结果
     */
    ResponseVo update(PersonInfoAo personInfoAo);

    /**
     * 修改密码
     * @param passwordAo 参数
     * @return 修改结果
     */
    ResponseVo updatePassWord(PasswordAo passwordAo);
}
