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
 * @since 2024-01-20 22:05:22
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resume")
public class Resume  {
    @TableId
    private Integer id;

    private Integer userId;

    private String birthplace;
    
    private String advantages;
    
    private String works;
    
    private String desires;
    
    private String projects;
    
    private String educations;
    
    private String certificates;



}

