package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/04/11 17:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "简历画像vo")
public class ResumeDrawVo {
    private Integer id;

    //对应的简历id
    @ApiModelProperty(value = "对应的简历id")
    private Integer resumeId;
    //大分类
    @ApiModelProperty(value = "大分类")
    private String bigClassify;
    //画像亮点
    @ApiModelProperty(value = "画像亮点")
    private String highLight;
    //错误
    @ApiModelProperty(value = "错误")
    private String isError;
    //技能
    @ApiModelProperty(value = "技能")
    private String skills;
    //小分类
    @ApiModelProperty(value = "小分类")
    private String smallClassify;

}