package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: qjn
 * @Date: 2023/12/3 13:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVo {
    //用户名
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "手机号")
    //手机号
    private String tele;
    @ApiModelProperty(value = "0用户，1公司，2管理端")
    //0用户，1公司，2管理端
    private Integer isCompany;
    @ApiModelProperty(value = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "1")
    private Integer pageSize;
}
