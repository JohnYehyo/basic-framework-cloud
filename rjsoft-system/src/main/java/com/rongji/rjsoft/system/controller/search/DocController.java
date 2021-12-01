package com.rongji.rjsoft.system.controller.search;

import com.rongji.core.enums.LogTypeEnum;
import com.rongji.core.enums.OperatorTypeEnum;
import com.rongji.core.vo.ResponseVo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.search.core.search.DocAo;
import com.rongji.rjsoft.search.core.search.DocDeleteAo;
import com.rongji.rjsoft.search.service.ISearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @description: 文档管理
 * @author: JohnYehyo
 * @create: 2021-12-01 13:34:59
 */
@Api(tags = "搜索引擎-文档管理")
@RestController
@RequestMapping(value = "doc")
public class DocController {

    @Autowired
    private ISearchService esService;


    /**
     * 新增文档
     *
     * @param docAo 文档参数
     * @return ResponseVo
     */
    @PreAuthorize(value = "@permissionIdentify.hasPermi('search_doc_add')")
    @ApiOperation(value = "新增文档")
    @PostMapping
    @LogAction(module = "搜索引擎-文档管理", method = "新增文档", logType = LogTypeEnum.INSERT,
            operatorType = OperatorTypeEnum.WEB)
    public ResponseVo add(@Valid DocAo docAo) throws IOException {
        esService.addDoc(docAo);
        return ResponseVo.success("更新文档成功");
    }

    /**
     * 更新文档
     *
     * @param docAo 文档参数
     * @return ResponseVo
     */
    @PreAuthorize(value = "@permissionIdentify.hasPermi('search_doc_update')")
    @ApiOperation(value = "更新文档")
    @PutMapping
    @LogAction(module = "搜索引擎-文档管理", method = "更新文档", logType = LogTypeEnum.UPDATE,
            operatorType = OperatorTypeEnum.WEB)
    public ResponseVo edit(@Valid DocAo docAo) throws IOException {
        esService.addDoc(docAo);
        return ResponseVo.success("更新文档成功");
    }

    /**
     * 删除文档
     *
     * @param docDeleteAo
     * @return ResponseVo
     */
    @PreAuthorize(value = "@permissionIdentify.hasPermi('search_doc_delete')")
    @ApiOperation(value = "删除文档")
    @DeleteMapping
    @LogAction(module = "搜索引擎-文档管理", method = "删除文档", logType = LogTypeEnum.DELETE,
            operatorType = OperatorTypeEnum.WEB)
    public ResponseVo delete(@Valid DocDeleteAo docDeleteAo) throws IOException {
        esService.deleteDoc(docDeleteAo);
        return ResponseVo.success("删除文档成功");
    }

}
