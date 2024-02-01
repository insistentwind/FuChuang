package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Favor)表实体类
 *
 * @author makejava
 * @since 2024-02-01 18:01:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("favor")
public class Favor  {
    @TableId
    private Integer id;

    //收藏人id
    private Integer userId;
    //职位id
    private Integer workId;



}

