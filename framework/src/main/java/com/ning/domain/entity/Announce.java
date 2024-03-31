package com.ning.domain.entity;

import java.util.Date;

import java.io.Serializable;
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
    private Integer id;

    //标题
    private String title;
    //公告内容
    private String content;
    //公告摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;

    private String status;
    //访问量
    private Long viewCount;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}

