package com.rongji.rjsoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.core.entity.system.SysUserPost;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface ISysUserPostService extends IService<SysUserPost> {

    /**
     * 通过用户id查询岗位
     * @param userId 用户id
     * @return 用户岗位
     */
    List<Integer> getPostsByUserId(Long userId);

}
