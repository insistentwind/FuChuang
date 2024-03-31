package com.ning.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}