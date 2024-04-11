package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TreeSelectVo {
    @ApiModelProperty(value = "1")
    private Integer id;

    //菜单（权限）名称
    @ApiModelProperty(value = "菜单（权限）名称")
    private String label;
    //父id
    @ApiModelProperty(value = "父id")
    private Long parentId;
    //子目录
    @ApiModelProperty(value = "子目录")
    private List<TreeSelectVo> children;
}
