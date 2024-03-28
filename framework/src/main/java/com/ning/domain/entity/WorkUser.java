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
 * (WorkUser)表实体类
 *
 * @author makejava
 * @since 2024-03-19 22:40:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("work_user")
@Builder
public class WorkUser  {
    @TableId
    private Integer id;

    //职位id
    private Integer workId;
    //用户id
    private Integer userId;
    //用户简历id
    private Integer resumeId;



}

