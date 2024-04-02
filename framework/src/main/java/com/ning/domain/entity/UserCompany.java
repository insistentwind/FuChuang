package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * (UserCompany)表实体类
 *
 * @author makejava
 * @since 2024-03-05 21:25:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "用户-公司表",description = "")
@TableName("user_company")
public class UserCompany  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer userId ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer companyId ;



}

