package com.rongji.rjsoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.core.ao.system.SysUserAo;
import com.rongji.core.entity.system.SysUser;
import com.rongji.core.query.system.user.UserQuery;
import com.rongji.core.vo.CommonPage;
import com.rongji.core.vo.system.user.SysUserInfoVo;
import com.rongji.core.vo.system.user.SysUserVo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 通过用户姓名查询用户信息
     * @param userName 用户姓名
     * @return 用户信息
     */
    SysUser getUserByName(String userName);

    /**
     * 查询用户列表
     * @param userQuery 查询条件
     * @return 用户列表
     */
    CommonPage<SysUserVo> listOfUser(UserQuery userQuery);

    /**
     * 新增用户
     * @param user 用户表单信息
     * @return int
     */
    int addUser(SysUserAo user);

    /**
     * 编辑用户
     * @param user 用户表单信息
     * @return int
     */
    int editUser(SysUserAo user);

    /**
     * 删除用户
     * @param userIds 用户id
     * @return int
     */
    int deleteUser(Long[] userIds);

    /**
     * 检查用户名
     * @param user 用户信息
     * @return boolean
     */
    boolean checkUserByUserName(SysUserAo user);

    /**
     * 检查手机号
     * @param user 用户信息
     * @return boolean
     */
    boolean checkUserByPhone(SysUserAo user);

    /**
     * 检查email
     * @param user 用户信息
     * @return boolean
     */
    boolean checkUserByEmail(SysUserAo user);

    /**
     * 通过用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    SysUserInfoVo getUserInfoById(Long userId);

    /**
     * 重置密码
     * @param userId 用户id
     * @return 重置结果
     */
    boolean restPwd(Long userId);
}
