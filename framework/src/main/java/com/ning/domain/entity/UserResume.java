package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * (UserResume)表实体类
 *
 * @author makejava
 * @since 2024-03-14 21:38:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "用户-简历表",description = "")
@TableName("user_resume")
@Builder
public class UserResume  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userId ;
    /** 简历id */
    @ApiModelProperty(name = "简历id",notes = "")
    private Integer resumeId ;
    /** 默认简历(0否，1是) */
    @ApiModelProperty(name = "默认简历(0否，1是)",notes = "")
    private Integer isDefault ;



}

