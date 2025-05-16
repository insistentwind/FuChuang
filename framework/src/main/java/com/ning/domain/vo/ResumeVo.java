package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: qjn
 * @create: 2024/03/19 20:16
 **/
@Data
@Accessors(chain = true)
@ApiModel(description = "简历创建条件")
public class ResumeVo {

    @ApiModelProperty(value = "1")
    private Integer id ;
    //简历所属的用户id
    @TableField(exist = false)
    private Integer userId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name ;
    /** 出生日期 */
    @ApiModelProperty(value = "出生日期")
    private String bir ;
    /** 年龄 */
    @ApiModelProperty(value = "年龄")
    private String age ;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email ;
    /** 性别（0女，1男） */
    @ApiModelProperty(value = "性别（0女，1男）")
    private Integer gend ;
    /** 电话 */
    @ApiModelProperty(value = "电话")
    private String tel ;
    /** 最高学历 */
    @ApiModelProperty(value = "最高学历")
    private String acad ;
    /** 籍贯 */
    @ApiModelProperty(value = "籍贯")
    private String nati ;
    /** 落户市县 */
    @ApiModelProperty(value = "落户市县")
    private String live ;
    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌")
    private String poli ;
    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校")
    private String unv ;
    /** 工作经历 */
    @ApiModelProperty(value = "工作经历")
    private String workExperience ;
    /** 教育经历 */
    @ApiModelProperty(value = "教育经历")
    private String education ;
    /** 项目经历 */
    @ApiModelProperty(value = "项目经历")
    private String projectExperience ;
    /** 教育培训经历 */
    @ApiModelProperty(value = "教育培训经历")
    private String trainingExperience ;
    /** 获奖情况 */
    @ApiModelProperty(value = "获奖情况")
    private String awards ;
    /** 自我评价 */
    @ApiModelProperty(value = "自我评价")
    private String selfAssessment ;
    /** 求职意向 */
    @ApiModelProperty(value = "求职意向")
    private String careerObjective ;
    /** 技能与特长 */
    @ApiModelProperty(value = "技能与特长")
    private String skills ;
    /** 个人爱好 */
    @ApiModelProperty(value = "个人爱好")
    private String interests ;
    /** 证书获得 */
    @ApiModelProperty(value = "证书获得")
    private String certifications ;
    /** 语言能力 */
    @ApiModelProperty(value = "语言能力")
    private String languageSkills ;
    /** 发表论文 */
    @ApiModelProperty(value = "发表论文")
    private String publications ;
    /** 人生格言 */
    @ApiModelProperty(value = "人生格言")
    private String philosophy ;
    /** 主修课程 */
    @ApiModelProperty(value = "主修课程")
    private String majorCourses ;
    /** 自我介绍 */
    @ApiModelProperty(value = "自我介绍")
    private String selfIntroduction ;
    /** 其它情况 */
    @ApiModelProperty(value = "其它情况")
    private String other ;
    /** 是否默认可见(0 是，1 否) */
    @ApiModelProperty(value = "是否默认可见(0 是，1 否)")
    private Integer obscure ;

    /**是否加入公共简历池(0 是，1否) */
    @ApiModelProperty(value = "是否加入公共简历池(0 是，1否)")
    private Integer publicPool;

    //要投递的职位id，公司端投递要用
    @ApiModelProperty(value = "要投递的职位id，公司端投递要用")
    private Integer workId;
    @ApiModelProperty(value = "字段是否加密（0 否，1 是")
    private Integer isEncode;

    @ApiModelProperty(name = "是否已读(0否1是)",notes = "")
    private Integer isRead;
    //1为默认，0否
    @ApiModelProperty(value = "1为默认，0否")
    private Integer isDefault;

}