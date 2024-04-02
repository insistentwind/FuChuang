package com.ning.domain.dto;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * @author: qjn
 * @create: 2024/03/25 21:47
 **/
@Data
public class ResumeCommitDto {

    //职位id
    @ApiModelProperty(value = "职位id")
    private Integer workId;
    //用户id
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "简历id")
    private Integer resumeId;



}