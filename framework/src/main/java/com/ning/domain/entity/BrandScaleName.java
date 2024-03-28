package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("brand_scale_name")
public class BrandScaleName  {
    @TableId
    private Integer id;
    // 公司规模
    private String brandScaleName;



}

