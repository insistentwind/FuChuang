package com.ning.domain.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Follow)表实体类
 *
 * @author makejava
 * @since 2024-02-24 22:22:07
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("follow")
public class Follow  {
    @TableId
    private Integer id;

    //用户id
    private Integer userId;
    //关注的公司的id
    private Integer companyId;



}

