package com.ning.domain.entity;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 菜单权限表(Menu)表实体类
 *
 * @author makejava
 * @since 2024-03-11 20:38:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@Accessors(chain = true)
public class Menu  {
    //菜单ID
    @TableId
    private Long id;
    //父菜单ID
    @ApiModelProperty(value = "父菜单ID",notes = "")
    private Long parentId;
    //父节点ID路径
    @ApiModelProperty(value = "父节点ID路径",notes = "")
    private String treePath;
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
    //显示状态(1-显示;0-隐藏)
    @ApiModelProperty(value = "显示状态(1-显示;0-隐藏)",notes = "")
    private Integer visible;
    //排序
    @ApiModelProperty(value = "排序",notes = "")
    private Integer sort;
    //菜单图标
    @ApiModelProperty(value = "菜单图标",notes = "")
    private String icon;
    //跳转路径
    @ApiModelProperty(value = "跳转路径",notes = "")
    private String redirect;
    //创建时间
    @ApiModelProperty(value = "创建时间",notes = "")
    private LocalDateTime createTime;
    //更新时间
    @ApiModelProperty(value = "更新时间",notes = "")
    private LocalDateTime updateTime;
    //【目录】只有一个子路由是否始终显示(1:是 0:否)
    @ApiModelProperty(value = "【目录】只有一个子路由是否始终显示(1:是 0:否)",notes = "")
    private Integer alwaysShow;
    //【菜单】是否开启页面缓存(1:是 0:否)
    @ApiModelProperty(value = "【菜单】是否开启页面缓存(1:是 0:否)",notes = "")
    private Integer keepAlive;
    //删除标志位
    @ApiModelProperty(value = "删除标志位",notes = "")
    private Integer delFlag;

    @TableField(exist = false)
    private List<Menu> children;

}

