package com.rongji.rjsoft.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.core.entity.common.SysCommonFile;
import com.rongji.rjsoft.system.mapper.SysCommonFileMapper;
import com.rongji.core.query.common.SysCommonFileQuery;
import com.rongji.rjsoft.system.service.ISysCommonFileService;
import com.rongji.core.vo.common.SysCommonFileVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公共附件表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-10-19
 */
@Service
@AllArgsConstructor
public class SysCommonFileServiceImpl extends ServiceImpl<SysCommonFileMapper, SysCommonFile> implements ISysCommonFileService {

    private SysCommonFileMapper sysCommonFileMapper;

    /**
     * 附件查询
     * @param sysCommonFileQuery 查询对象
     * @return 附件列表
     */
    @Override
    public List<SysCommonFileVo> getFiles(SysCommonFileQuery sysCommonFileQuery) {
        return sysCommonFileMapper.getFiles(sysCommonFileQuery);
    }
}
