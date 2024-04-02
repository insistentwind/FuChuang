package com.ning.domain.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * (CompanyUser)表实体类
 *
 * @author makejava
 * @since 2024-03-31 16:55:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "公司-员工关系表",description = "")
@TableName("company_employee")
public class CompanyEmployee  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 公司管理员id */
    @ApiModelProperty(name = "公司管理员id",notes = "")
    private Integer companyUserId ;
    /** 公司用户id */
    @ApiModelProperty(name = "公司用户id",notes = "")
    private Integer employeeId ;



}

