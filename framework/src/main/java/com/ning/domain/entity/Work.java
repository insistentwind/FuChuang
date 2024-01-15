package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Resume)表实体类
 *
 * @author makejava
 * @since 2024-01-09 23:25:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work")
public class Work  {
    @TableId
    private Integer id;

    private String company;
    
    private String title;
    
    private String salary;
    
    private String education;
    
    private String description;
    
    private String address;
    
    private String link;



}

