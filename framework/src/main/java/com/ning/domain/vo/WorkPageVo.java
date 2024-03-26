package com.ning.domain.vo;

import lombok.Data;

/**
 * @author: qjn
 * @create: 2024/03/24 10:35
 **/
@Data
public class WorkPageVo {

//    //页数
//    private Integer pageNum;
//    //分页大小
//    private Integer pageSize;
    // id前端可传可不传
    private Integer companyId;
    //分类id
    private Integer categoryId;
    //城市分类id
    private Integer cityClassifyId;
}