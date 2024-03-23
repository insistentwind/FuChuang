package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 12:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {

    List<TreeSelectVo> menus;

    List<String> checkedKeys;
}