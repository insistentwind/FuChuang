package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: qjn
 * @create: 2024/03/31 18:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceVo {
    @ApiModelProperty(value = "1")
    private Integer id;

    //标题
    @ApiModelProperty(value = "标题")
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

    @ApiModelProperty(value = "状态 0启用")
    private String status;
    //访问量
    @ApiModelProperty(value = "访问量")
    private Long viewCount;
}
