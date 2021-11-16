package com.rongji.rjsoft.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.core.entity.common.SysCommonFile;
import com.rongji.core.query.common.SysCommonFileQuery;
import com.rongji.core.vo.common.SysCommonFileVo;

import java.util.List;

/**
 * <p>
 * 公共附件表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-10-19
 */
public interface SysCommonFileMapper extends BaseMapper<SysCommonFile> {

    /**
     * 附件查询
     * @param sysCommonFileQuery 查询对象
     * @return 附件列表
     */
    List<SysCommonFileVo> getFiles(SysCommonFileQuery sysCommonFileQuery);
}
