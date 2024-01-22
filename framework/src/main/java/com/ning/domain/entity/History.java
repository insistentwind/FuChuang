package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (History)表实体类
 *
 * @author makejava
 * @since 2024-01-20 22:01:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("history")
public class History  {
    @TableId
    private Integer id;

    
    private Integer userId;
    
    private Integer companyId;
    
    private Integer resumeId;



}

