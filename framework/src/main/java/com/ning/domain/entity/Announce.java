package com.ning.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * 文章表(Announce)表实体类
 *
 * @author makejava
 * @since 2024-03-31 17:49:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("announce")
public class Announce  {
    @TableId
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer id ;
    /** 标题 */
    @ApiModelProperty(name = "标题",notes = "")
    private String title ;
    /** 公告内容 */
    @ApiModelProperty(name = "公告内容",notes = "")
    private String content ;
    /** 公告摘要 */
    @ApiModelProperty(name = "公告摘要",notes = "")
    private String summary ;
    /** 缩略图 */
    @ApiModelProperty(name = "缩略图",notes = "")
    private String thumbnail ;
    /** 是否置顶（0否，1是） */
    @ApiModelProperty(name = "是否置顶（0否，1是）",notes = "")
    private Integer isTop ;
    /** 访问量 */
    @ApiModelProperty(name = "访问量",notes = "")
    private Long viewCount ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer createBy ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private LocalDateTime createTime ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer updateBy ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private LocalDateTime updateTime ;
    /** 删除标志（0代表未删除，1代表已删除） */
    @ApiModelProperty(name = "删除标志（0代表未删除，1代表已删除）",notes = "")
    private Integer delFlag ;
    /** 0 启用，1不启用 */
    @ApiModelProperty(name = "0 启用，1不启用",notes = "")
    private Integer status ;



}

