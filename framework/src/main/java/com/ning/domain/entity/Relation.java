package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Relation)表实体类
 *
 * @author makejava
 * @since 2024-03-01 15:20:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "公司-职位表",description = "")
@TableName("relation")
@Builder
public class Relation  {
    /**  */
    @TableId
    private Integer id ;
    /** 公司id */
    @ApiModelProperty(value = "公司id",notes = "")
    private Integer companyId ;
    /** 职位id */
    @ApiModelProperty(value = "职位id",notes = "")
    private Integer workId ;



}

