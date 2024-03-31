package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
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
}