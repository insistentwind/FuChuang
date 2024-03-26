package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("city_classify")
public class CityClassify  {
    @TableId
    private Integer id;

    //城市
    private String cityName;

}

