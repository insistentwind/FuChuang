package com.ning.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: qjn
 * @create: 2024/04/09 20:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "简历池分页实体",description = "")
public class ResumePageDto {

//    @ApiModelProperty(value = "1")
//    private Integer id ;

    @ApiModelProperty(value = "页号")
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}