package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (Ack)表实体类
 *
 * @author makejava
 * @since 2024-04-03 22:16:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ack")
public class Ack  {
    @TableId
    private Integer id;

    //用户
    private Integer userId;
    //公司
    private Integer companyId;
    //内容
    private String content;
    //删除标志位
    private Integer delFlag;
    //是公司发送的消息(0否，1是)
    private Integer isCompany;
    //是否已读(0否，1是)
    private Integer read;



}

