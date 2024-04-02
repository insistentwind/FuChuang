package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "1")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "邮箱")
    private String email;


}
