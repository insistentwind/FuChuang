package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2024/2/1 18:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavorDto {
    //收藏人id
    private Integer userId;
    //职位id
    private Integer workId;
}
