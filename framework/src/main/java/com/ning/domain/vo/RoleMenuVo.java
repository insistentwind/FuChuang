package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 11:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleMenuVo {
    @ApiModelProperty(value = "1")
    private Integer id;
    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    //角色权限字符串
    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;
    //显示顺序
    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;
    //角色状态（0正常 1停用）
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private Integer status;
    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单id")
    private List<String> menuIds;
}
