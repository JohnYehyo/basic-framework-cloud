package com.rongji.rjsoft.system.service;

import com.rongji.core.ao.system.SysDeptAo;
import com.rongji.core.entity.system.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.core.query.system.dept.DeptQuey;
import com.rongji.core.vo.system.dept.SysDeptAllTreeInfoVo;
import com.rongji.core.vo.system.dept.SysDeptAllTreeVo;
import com.rongji.core.vo.system.dept.SysDeptVo;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-17
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 检查部门名称是否存在
     * @param sysDeptAo 部门信息
     * @return 是否存在
     */
    boolean checkDeptByName(SysDeptAo sysDeptAo);

    /**
     * 添加部门信息
     * @param sysDeptAo 部门信息
     * @return 添加结果
     */
    boolean saveDept(SysDeptAo sysDeptAo);

    /**
     * 编辑部门信息
     * @param sysDeptAo 部门信息
     * @return 编辑结果
     */
    boolean updateDept(SysDeptAo sysDeptAo);

    /**
     * 删除部门信息
     * @param deptIds 部门id
     * @return 删除结果
     */
    boolean deleteDept(Long[] deptIds);


    /**
     * 通过id获取部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    SysDeptVo getDeptById(Long deptId);

    /**
     * 部门列表
     * @param deptQuey 查询条件
     * @return 部门列表
     */
    List<SysDeptVo> listByCondition(DeptQuey deptQuey);

    /**
     * 获取当前用户部门树
     * @param deptId 部门id
     * @return 用户树
     */
    Object listByUser(Long deptId);

    /**
     * 部门同步树
     * @param deptId 部门id
     * @return 部门树
     */
    SysDeptAllTreeVo allTree(Long deptId);

    /**
     * 部门同步树列表
     * @param deptId 部门id
     * @return 部门树
     */
    SysDeptAllTreeInfoVo pageList(Long deptId);
}
