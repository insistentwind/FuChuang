package com.ning.domain.entity;


import java.io.Serializable;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (University)表实体类
 *
 * @author makejava
 * @since 2024-04-05 15:18:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "学校信息表",description = "")
@TableName("yiqixue_university")
public class University  {
    /** id */
    @TableId
    private Integer schoolId ;
    /** 唯一id */
    @ApiModelProperty(value = "唯一id",notes = "")
    private String schoolIdCode ;
    /** 学校代码 */
    @ApiModelProperty(value = "学校代码",notes = "")
    private String schoolCode ;
    /** 学校名称 */
    @ApiModelProperty(value = "学校名称",notes = "")
    private String name ;
    /** 学校类型 */
    @ApiModelProperty(value = "学校类型",notes = "")
    private String type ;
    /** 类型名称 */
    @ApiModelProperty(value = "类型名称",notes = "")
    private String typeName ;
    /** 教育类型（本科，专科） */
    @ApiModelProperty(value = "教育类型（本科，专科）",notes = "")
    private String schoolType ;
    /** 教育类型名称 */
    @ApiModelProperty(value = "教育类型名称",notes = "")
    private String schoolTypeName ;
    /** 学校办公类型 */
    @ApiModelProperty(value = "学校办公类型",notes = "")
    private String schoolNature ;
    /** 学校办公类型代码 */
    @ApiModelProperty(value = "学校办公类型代码",notes = "")
    private String schoolNatureName ;
    /** 地理位置 */
    @ApiModelProperty(value = "地理位置",notes = "")
    private String belong ;
    /** f985 */
    @ApiModelProperty(value = "f985",notes = "")
    private String f985 ;
    /** f211 */
    @ApiModelProperty(value = "f211",notes = "")
    private String f211 ;
    /** 学科数量 */
    @ApiModelProperty(value = "学科数量",notes = "")
    private String numSubject ;
    /** 研究生数量 */
    @ApiModelProperty(value = "研究生数量",notes = "")
    private String numMaster ;
    /** 博士数量 */
    @ApiModelProperty(value = "博士数量",notes = "")
    private Integer numDoctor ;
    /** acad数量 */
    @ApiModelProperty(value = "acad数量",notes = "")
    private String numAcademician ;
    /** 图书数量 */
    @ApiModelProperty(value = "图书数量",notes = "")
    private String numLibrary ;
    /** 灯数量 */
    @ApiModelProperty(value = "灯数量",notes = "")
    private String numLab ;
    /** 省份id */
    @ApiModelProperty(value = "省份id",notes = "")
    private String provinceId ;
    /** 省份名称 */
    @ApiModelProperty(value = "省份名称",notes = "")
    private String provinceName ;
    /** 城市id */
    @ApiModelProperty(value = "城市id",notes = "")
    private String cityId ;
    /** 城市名称 */
    @ApiModelProperty(value = "城市名称",notes = "")
    private String cityName ;
    /** 区域id */
    @ApiModelProperty(value = "区域id",notes = "")
    private String countyId ;
    /** 乡镇名称 */
    @ApiModelProperty(value = "乡镇名称",notes = "")
    private String townName ;
    /** 创立时间 */
    @ApiModelProperty(value = "创立时间",notes = "")
    private String createDate ;
    /** 地区 */
    @ApiModelProperty(value = "地区",notes = "")
    private String area ;
    /** 老名字 */
    @ApiModelProperty(value = "老名字",notes = "")
    private String oldName ;
    /** 简称 */
    @ApiModelProperty(value = "简称",notes = "")
    private String shortName ;
    /** ruanke排名 */
    @ApiModelProperty(value = "ruanke排名",notes = "")
    private String ruankeRank ;
    /** wsl排名 */
    @ApiModelProperty(value = "wsl排名",notes = "")
    private String wslRank ;
    /** qs排名 */
    @ApiModelProperty(value = "qs排名",notes = "")
    private String qsRank ;
    /** xyh排名 */
    @ApiModelProperty(value = "xyh排名",notes = "")
    private String xyhRank ;
    /** 双一流 */
    @ApiModelProperty(value = "双一流",notes = "")
    private String dualClassName ;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱",notes = "")
    private String email ;
    /** 地址 */
    @ApiModelProperty(value = "地址",notes = "")
    private String address ;
    /** 邮政编码 */
    @ApiModelProperty(value = "邮政编码",notes = "")
    private String postcode ;
    /** 网站 */
    @ApiModelProperty(value = "网站",notes = "")
    private String site ;
    /** 学校网站 */
    @ApiModelProperty(value = "学校网站",notes = "")
    private String schoolSite ;
    /** 电话 */
    @ApiModelProperty(value = "电话",notes = "")
    private String phone ;
    /** 内容 */
    @ApiModelProperty(value = "内容",notes = "")
    private String content ;


}

