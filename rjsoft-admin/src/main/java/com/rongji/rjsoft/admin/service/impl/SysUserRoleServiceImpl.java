package com.rongji.rjsoft.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.core.entity.system.SysUserRole;
import com.rongji.rjsoft.admin.mapper.SysUserRoleMapper;
import com.rongji.rjsoft.admin.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 通多用户id查询角色信息
     * @param userId 用户id
     * @return 用户角色
     */
    @Override
    public List<Integer> getRolesByUserId(Long userId) {
        return sysUserRoleMapper.getRolesByUserId(userId);
    }
}
