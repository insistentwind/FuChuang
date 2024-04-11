package com.ning.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/04/11 23:27
 **/
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "管理员职位条件")
@Data
public class AdminWorkLogVo {

    /**  */
    @ApiModelProperty(value = "1")
    private Integer id ;
    /** 分类id */
    @ApiModelProperty(value = "分类id")
    private Integer classifyId ;
    /** 职位介绍 */
    @ApiModelProperty(value = "职位介绍")
    private String description ;
    /**  */
    @ApiModelProperty(value = "职位名称")
    private String title ;
    /** 学历要求 */
    @ApiModelProperty(value = "学历要求")
    private Integer education ;
    /** 工作经验要求 */
    @ApiModelProperty(value = "工作经验要求")
    private Integer jobExperience ;
    /** 薪资分类 */
    @ApiModelProperty(value = "薪资分类")
    private Integer salary ;
    /** 薪资水平 */
    @ApiModelProperty(value = "薪资水平")
    private String salaryDesc ;
    /** 技术要求 */
    @ApiModelProperty(value = "技术要求")
    private String skills ;
    /** 福利列表 */
    @ApiModelProperty(value = "福利列表")
    private String welfareList ;
    /** 工作地点id */
    @ApiModelProperty(value = "工作地点id")
    private Integer cityName ;
    /** 工作地区 */
    @ApiModelProperty(value = "工作地区")
    private String areaDistrict ;
    /** 工作地点 */
    @ApiModelProperty(value = "工作地点")
    private String businessDistrict ;
    /** 链接 */
    @ApiModelProperty(value = "链接")
    private String href ;
    /** hr姓名 */
    @ApiModelProperty(value = "hr姓名")
    private String bossName ;
    /** hr职位 */
    @ApiModelProperty(value = "hr职位")
    private String bossTitle ;
    /** 浏览量 */
    @ApiModelProperty(value = "浏览量")
    private Long viewCount ;

    /**
     * 操作标识位(0是删除，1是插入，2是更新操作)
     */
    @ApiModelProperty(value = "操作标识位(0是删除，1是插入，2是更新操作)", notes = "")
    private Integer tagFlag;
}