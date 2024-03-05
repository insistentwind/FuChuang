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
 * @since 2024-03-03 20:50:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resume")
public class Resume  {
    @TableId
    private Integer id;

    //用户id
    private Integer userId;
    //出生日期
    private String birthplace;
    //个人优势
    private String advantages;
    //工作经历（实习经历）
    private String works;
    //期望的职位
    private String desires;
    //项目经历
    private String projects;
    //教育经历
    private String educations;
    //资格证书
    private String certificates;
    //简历链接
    private String link;


}

