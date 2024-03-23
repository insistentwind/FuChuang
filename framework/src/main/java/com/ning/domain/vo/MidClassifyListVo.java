package com.ning.domain.vo;

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

    String midClassify;

    List<String> smallClassifyList;

}