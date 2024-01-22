package com.ning.domain.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ResumeVo {
    private String birthplace;
    
    private String advantages;
    
    private String works;
    
    private String desires;
    
    private String projects;
    
    private String educations;
    
    private String certificates;

}

