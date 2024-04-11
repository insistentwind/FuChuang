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
    /** 菜单ID */
    @ApiModelProperty(name = "菜单ID",notes = "")
    @TableId
    private Integer id ;
    /** 菜单名称 */
    @ApiModelProperty(name = "菜单名称",notes = "")
    private String menuName ;
    /** 父菜单ID */
    @ApiModelProperty(name = "父菜单ID",notes = "")
    private Long parentId ;
    /** 显示顺序 */
    @ApiModelProperty(name = "显示顺序",notes = "")
    private Integer orderNum ;
    /** 路由地址 */
    @ApiModelProperty(name = "路由地址",notes = "")
    private String path ;
    /** 组件路径 */
    @ApiModelProperty(name = "组件路径",notes = "")
    private String component ;
    /** 是否为外链（0是 1否） */
    @ApiModelProperty(name = "是否为外链（0是 1否）",notes = "")
    private Integer isFrame ;
    /** 菜单类型（M目录 C菜单 F按钮） */
    @ApiModelProperty(name = "菜单类型（M目录 C菜单 F按钮）",notes = "")
    private String menuType ;
    /** 菜单状态（0显示 1隐藏） */
    @ApiModelProperty(name = "菜单状态（0显示 1隐藏）",notes = "")
    private String visible ;
    /** 菜单状态（0正常 1停用） */
    @ApiModelProperty(name = "菜单状态（0正常 1停用）",notes = "")
    private Integer status ;
    /** 权限标识 */
    @ApiModelProperty(name = "权限标识",notes = "")
    private String perms ;
    /** 菜单图标 */
    @ApiModelProperty(name = "菜单图标",notes = "")
    private String icon ;
    /** 创建者 */
    @ApiModelProperty(name = "创建者",notes = "")
    private Integer createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;
    /** 更新者 */
    @ApiModelProperty(name = "更新者",notes = "")
    private Integer updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer delFlag ;

    @TableField(exist = false)
    private List<Menu> children;

}

