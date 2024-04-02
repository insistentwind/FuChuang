package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/21 23:32
 **/
@Data
@Accessors(chain = true)
public class MidClassifyListVo {
    @ApiModelProperty(value = "中分类")
    String midClassify;
    @ApiModelProperty(value = "小分类")
    List<String> smallClassifyList;

}