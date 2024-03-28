package com.ning.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/28 18:18
 **/
@Data
@Accessors(chain = true)
public class BrandScaleNameVo {
    private Integer id;
    // 公司规模
    private String brandScaleName;

}