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
 * 城市分类(CityClassify)表实体类
 *
 * @author makejava
 * @since 2024-03-26 16:33:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "城市分类",description = "")
@TableName("city_classify")
public class CityClassify  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 城市 */
    @ApiModelProperty(name = "城市",notes = "")
    private String cityName ;

}

