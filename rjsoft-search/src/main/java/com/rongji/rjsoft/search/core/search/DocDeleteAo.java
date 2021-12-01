package com.rongji.rjsoft.search.core.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 删除文档参数
 * @author: JohnYehyo
 * @create: 2021-12-01 13:38:16
 */
@Data
@ApiModel(value = "文档参数")
public class DocDeleteAo {

    /**
     * 索引
     */
    @ApiModelProperty(value = "索引", required = true)
    @NotBlank(message = "索引不能为空")
    private String index;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 文档id
     */
    @ApiModelProperty(value = "文档id")
    private Long id;
}
