package com.ning.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: qjn
 * @create: 2024/03/31 17:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncePublishDto {
    private Integer id;
    @ApiModelProperty(value = "标题")
    //标题
    private String title;
    //公告内容
    @ApiModelProperty(value = "公告内容")
    private String content;
    //公告摘要
    @ApiModelProperty(value = "公告摘要")
    private String summary;
    //缩略图
    @ApiModelProperty(value = "缩略图")
    private String thumbnail;
    //是否置顶（0否，1是）
    @ApiModelProperty(value = "是否置顶（0否，1是）")
    private String isTop;
}