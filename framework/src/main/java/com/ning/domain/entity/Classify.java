package com.ning.domain.entity;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "职位分类表",description = "")
@TableName("work_classify")
public class Classify  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** max分类 */
    @ApiModelProperty(name = "max分类",notes = "")
    private String bigClassify ;
    /** middle分类 */
    @ApiModelProperty(name = "middle分类",notes = "")
    private String midClassify ;
    /** 子分类 */
    @ApiModelProperty(name = "子分类",notes = "")
    private String smallClassify ;
    /** 薪资地址 */
    @ApiModelProperty(name = "薪资地址",notes = "")
    private String smallClassifyHtml ;



}

