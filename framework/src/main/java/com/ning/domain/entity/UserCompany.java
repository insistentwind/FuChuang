package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user_company")
public class UserCompany  {
    @TableId
    private Integer id;

    
    private Integer userId;
    
    private Integer companyId;



}

