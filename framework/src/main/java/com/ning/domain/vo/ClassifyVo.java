package com.ning.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/21 22:45
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClassifyVo {

    Integer id;

    //max分类
    private String bigClassify;
    //middle分类
    private String midClassify;
    //子分类
    private String smallClassify;
    //薪资地址
    private String smallClassifyHtml;

}