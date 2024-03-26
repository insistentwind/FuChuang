package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/21 23:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ClassifyShowListVo {
    // 分类名
    private String classify;
    // 此分类的id
    private Integer id;
    // 小分类下的薪资
    private String salaryClassify;
    // 大分类和中分类列表
    private List<ClassifyShowListVo> childClassifyList;

}