package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户收藏职位",description = "")
@TableName("favor")
public class Favor  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 收藏人id */
    @ApiModelProperty(name = "收藏人id",notes = "")
    private Integer userId ;
    /** 职位id */
    @ApiModelProperty(name = "职位id",notes = "")
    private Integer workId ;



}

