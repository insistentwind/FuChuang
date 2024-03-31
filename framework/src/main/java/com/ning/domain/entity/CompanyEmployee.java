package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("company_employee")
public class CompanyEmployee  {
    @TableId
    private Integer id;

    //公司管理员id
    private Integer companyUserId;
    //公司用户id
    private Integer employeeId;



}

