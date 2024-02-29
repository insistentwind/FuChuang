package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Work)表实体类
 *
 * @author makejava
 * @since 2024-02-01 16:24:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work")
@Accessors(chain = true)
public class Work  {
    @TableId
    private Integer id;

    //公司名
    private String company;
    //职位名
    private String title;
    //薪资
    private String salary;
    //学历要求
    private String education;
    //职位描述
    private String description;
    //工作地点
    private String address;
    //链接
    private String link;
    //最大薪资
    private String maxSa;
    //最低薪资
    private String minSa;
    //浏览量
    private Long viewCount;

    public Work(Integer id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }

}

