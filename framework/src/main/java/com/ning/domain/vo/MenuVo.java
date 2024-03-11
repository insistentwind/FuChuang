package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/11 20:52
 **/
@Data
@AllArgsConstructor
public class MenuVo {

    private List<String> perms;

    private List<String> menuName;

}