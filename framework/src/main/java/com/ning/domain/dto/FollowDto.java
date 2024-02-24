package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    //用户id
    private Integer userId;
    //关注的公司的id
    private Integer companyId;
}
