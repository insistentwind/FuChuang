package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2023/12/2 20:57
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RolePageVo {
    //角色名称
    private String roleName;
    //角色状态（0正常 1停用）
    private Integer status;

    private Integer pageNum;

    private Integer pageSize;
}