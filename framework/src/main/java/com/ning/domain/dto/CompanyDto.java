package com.ning.domain.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class CompanyDto {

    private int pageNum;

    //每页显示记录数
    private int pageSize;

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

