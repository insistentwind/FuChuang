package com.ning.domain.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (UserPermitcompany)表实体类
 *
 * @author makejava
 * @since 2024-04-03 21:53:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_permitcompany")
public class UserPermitcompany {
    @TableId
    private Integer id;

    //用户id
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    //允许查看的公司id
    @ApiModelProperty(value = "允许查看的公司id")
    private Integer companyPermitId;



}

