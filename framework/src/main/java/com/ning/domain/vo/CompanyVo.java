package com.ning.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: qjn
 * @Date: 2024/1/24 23:35
 */
@Data
@Accessors(chain = true)
public class CompanyVo {

    private String message;

    private String name;

    private String job;

    private String hr;

    private String discribe;

    private String address;

    private Integer status;

}
