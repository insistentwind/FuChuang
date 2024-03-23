package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (Classify)表实体类
 *
 * @author makejava
 * @since 2024-03-21 17:02:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("work_classify")
public class Classify  {
    @TableId
    private Integer id;

    //max分类
    private String bigClassify;
    //middle分类
    private String midClassify;
    //子分类
    private String smallClassify;
    //薪资分类
    private String salaryClassify;
    //薪资地址
    private String smallClassifyHtml;



}

