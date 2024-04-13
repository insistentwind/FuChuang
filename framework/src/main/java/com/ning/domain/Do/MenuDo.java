package com.ning.domain.Do;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ning.domain.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/12 20:58
 **/
@Data
@AllArgsConstructor
public class MenuDo {
    //菜单ID
    @TableId
    private Long id;
    //父菜单ID
    @ApiModelProperty(value = "父菜单ID",notes = "")
    private Long parentId;
    //菜单名称
    @ApiModelProperty(value = "菜单名称",notes = "")
    private String name;
    //菜单类型(1:菜单 2:目录 3:外链 4:按钮)
    @ApiModelProperty(value = "菜单类型(1:菜单 2:目录 3:外链 4:按钮)",notes = "")
    private Integer type;
    //路由路径(浏览器地址栏路径)
    @ApiModelProperty(value = "浏览器地址栏路径",notes = "")
    private String path;
    //组件路径(vue页面完整路径，省略.vue后缀)
    @ApiModelProperty(value = "组件路径(vue页面完整路径，省略.vue后缀)",notes = "")
    private String component;
    //权限标识
    @ApiModelProperty(value = "权限标识",notes = "")
    private String perms;
    //菜单图标
    @ApiModelProperty(value = "菜单图标",notes = "")
    private String icon;

    @TableField(exist = false)
    private List<Menu> children;
}