package com.ning.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: qjn
 * @create: 2024/03/19 20:16
 **/
@Data
@Accessors(chain = true)
public class ResumeVo {
    //简历id
    private Integer id;
    //简历所属的用户id
    private Integer userId;
    //要投递的职位id，公司端投递要用
    private Integer workId;
    //姓名
    private String name;
    //出生日期
    private Date bir;
    //年龄
    private String age;
    //邮箱
    private String email;
    //性别（0女，1男）
    private Integer gend;
    //电话
    private String tel;
    //最高学历
    private String acad;
    //籍贯
    private String nati;
    //落户市县
    private String live;
    //政治面貌
    private String poli;
    //毕业院校
    private String unv;
    //工作经历
    private String workExperience;
    //教育经历
    private String education;
    //项目经历
    private String projectExperience;
    //教育培训经历
    private String trainingExperience;
    //获奖情况
    private String awards;
    //自我评价
    private String selfAssessment;
    //求职意向
    private String careerObjective;
    //技能与特长
    private String skills;
    //个人爱好
    private String interests;
    //证书获得
    private String certifications;
    //语言能力
    private String languageSkills;
    //发表论文
    private String publications;
    //人生格言
    private String philosophy;
    //主修课程
    private String majorCourses;
    //自我介绍
    private String selfIntroduction;
    //其它情况
    private String other;
    //1为默认，0否
    private Integer isDefault;

}