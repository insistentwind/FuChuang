package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Relation)表实体类
 *
 * @author makejava
 * @since 2024-03-01 15:20:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("relation")
@Accessors(chain = true)
@Builder
public class Relation  {
    @TableId
    private Integer id;

    
    private Integer companyId;
    
    private Integer workId;



}

