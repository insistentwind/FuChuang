package com.ning.domain.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@ApiModel(value = "用户关注公司",description = "")
@TableName("follow")
public class Follow  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userId ;
    /** 关注的公司的id */
    @ApiModelProperty(name = "关注的公司的id",notes = "")
    private Integer companyId ;



}

