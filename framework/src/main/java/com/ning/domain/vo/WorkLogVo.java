package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: qjn
 * @create: 2024/04/05 17:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "职位操作记录", description = "")
public class WorkLogVo {
    /**
     *
     */
    @ApiModelProperty(value = "", notes = "")
    @TableId
    private Integer id;
    @ApiModelProperty(value = "职位id")
    private Integer workId;
    /**
     * 操作标识位(0是删除，1是插入，2是更新操作)
     */
    @ApiModelProperty(value = "操作标识位(0是删除，1是插入，2是更新操作)", notes = "")
    private Integer tagFlag;

}