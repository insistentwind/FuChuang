package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (WorkResume)表实体类
 *
 * @author makejava
 * @since 2024-03-19 20:06:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WorkResume  {
    private Integer id;

    //职位id
    private Integer workId;
    //简历id
    private Integer resumeId;



}

