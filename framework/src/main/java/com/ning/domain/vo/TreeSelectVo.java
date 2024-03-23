package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TreeSelectVo {
    private Integer id;

    //菜单（权限）名称
    private String label;
    //父id
    private Integer parentId;
    //子目录
    private List<TreeSelectVo> children;
}
