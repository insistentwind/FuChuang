package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.nio.channels.Channel;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Resume)表实体类
 *
 * @author makejava
 * @since 2024-03-03 20:50:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "简历表",description = "")
@TableName("resume")
@Accessors(chain = true)
public class Resume  {
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    //简历所属的用户id
    @TableField(exist = false)
    private Integer userId;

    /** 姓名 */
    @ApiModelProperty(name = "姓名",notes = "")
    private String name ;
    /** 出生日期 */
    @ApiModelProperty(name = "出生日期",notes = "")
    private LocalDateTime bir ;
    /** 年龄 */
    @ApiModelProperty(name = "年龄",notes = "")
    private String age ;
    /** 邮箱 */
    @ApiModelProperty(name = "邮箱",notes = "")
    private String email ;
    /** 性别（0女，1男） */
    @ApiModelProperty(name = "性别（0女，1男）",notes = "")
    private Integer gend ;
    /** 电话 */
    @ApiModelProperty(name = "电话",notes = "")
    private String tel ;
    /** 最高学历 */
    @ApiModelProperty(name = "最高学历",notes = "")
    private String acad ;
    /** 籍贯 */
    @ApiModelProperty(name = "籍贯",notes = "")
    private String nati ;
    /** 落户市县 */
    @ApiModelProperty(name = "落户市县",notes = "")
    private String live ;
    /** 政治面貌 */
    @ApiModelProperty(name = "政治面貌",notes = "")
    private String poli ;
    /** 毕业院校 */
    @ApiModelProperty(name = "毕业院校",notes = "")
    private String unv ;
    /** 工作经历 */
    @ApiModelProperty(name = "工作经历",notes = "")
    private String workExperience ;
    /** 教育经历 */
    @ApiModelProperty(name = "教育经历",notes = "")
    private String education ;
    /** 项目经历 */
    @ApiModelProperty(name = "项目经历",notes = "")
    private String projectExperience ;
    /** 教育培训经历 */
    @ApiModelProperty(name = "教育培训经历",notes = "")
    private String trainingExperience ;
    /** 获奖情况 */
    @ApiModelProperty(name = "获奖情况",notes = "")
    private String awards ;
    /** 自我评价 */
    @ApiModelProperty(name = "自我评价",notes = "")
    private String selfAssessment ;
    /** 求职意向 */
    @ApiModelProperty(name = "求职意向",notes = "")
    private String careerObjective ;
    /** 技能与特长 */
    @ApiModelProperty(name = "技能与特长",notes = "")
    private String skills ;
    /** 个人爱好 */
    @ApiModelProperty(name = "个人爱好",notes = "")
    private String interests ;
    /** 证书获得 */
    @ApiModelProperty(name = "证书获得",notes = "")
    private String certifications ;
    /** 语言能力 */
    @ApiModelProperty(name = "语言能力",notes = "")
    private String languageSkills ;
    /** 发表论文 */
    @ApiModelProperty(name = "发表论文",notes = "")
    private String publications ;
    /** 人生格言 */
    @ApiModelProperty(name = "人生格言",notes = "")
    private String philosophy ;
    /** 主修课程 */
    @ApiModelProperty(name = "主修课程",notes = "")
    private String majorCourses ;
    /** 自我介绍 */
    @ApiModelProperty(name = "自我介绍",notes = "")
    private String selfIntroduction ;
    /** 其它情况 */
    @ApiModelProperty(name = "其它情况",notes = "")
    private String other ;
    /** 是否默认可见(0 是，1 否) */
    @ApiModelProperty(name = "是否默认可见(0 是，1 否)",notes = "")
    private Integer obscure ;
    /** 删除标志位 */
    @ApiModelProperty(name = "删除标志位",notes = "")
    private Integer delFlag ;
}

