package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/31 17:56
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncePageVo {
    //标题
    private String title;
    //文章摘要
    private String summary;

    private Integer pageNum;

    private Integer pageSize;
}