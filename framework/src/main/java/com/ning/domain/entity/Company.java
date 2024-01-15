package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Company)表实体类
 *
 * @author makejava
 * @since 2024-01-15 18:09:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("company")
public class Company  {
    @TableId
    private Integer id;

    
    private String message;
    
    private String username;
    
    private String password;
    
    private String name;
    
    private String job;
    
    private String hr;
    
    private String discribe;
    
    private String address;
    
    private Integer status;
    
    private String fund;



}

