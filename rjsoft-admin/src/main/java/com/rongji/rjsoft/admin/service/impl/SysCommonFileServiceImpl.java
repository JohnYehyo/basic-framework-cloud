package com.rongji.rjsoft.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.core.entity.common.SysCommonFile;
import com.rongji.rjsoft.admin.mapper.SysCommonFileMapper;
import com.rongji.rjsoft.core.query.query.common.SysCommonFileQuery;
import com.rongji.rjsoft.admin.service.ISysCommonFileService;
import com.rongji.rjsoft.core.vo.vo.common.SysCommonFileVo;
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
