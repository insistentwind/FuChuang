package com.ning.domain.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (SimilarPosition)表实体类
 *
 * @author makejava
 * @since 2024-04-12 19:09:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("similar_position")
public class SimilarPosition {

    @ApiModelProperty(value = "职位id")
    private Integer workId;
    @ApiModelProperty(value = "相似职位id")
    private String similarId;



}

