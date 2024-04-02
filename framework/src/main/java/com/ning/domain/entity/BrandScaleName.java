package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * (BrandScaleName)表实体类
 *
 * @author makejava
 * @since 2024-03-28 17:04:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "公司规模分类",description = "")
@TableName("brand_scale_name")
public class BrandScaleName  {
    /** id */
    @ApiModelProperty(name = "id",notes = "")
    @TableId
    private Integer id ;
    /** 公司规模 */
    @ApiModelProperty(name = "公司规模",notes = "")
    private String brandScaleName ;


}

