package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.CityClassifyMapper;
import com.ning.domain.entity.CityClassify;
import org.springframework.stereotype.Service;
import com.ning.service.CityClassifyService;

/**
 * 城市分类(CityClassify)表服务实现类
 *
 * @author makejava
 * @since 2024-03-26 16:33:34
 */
@Service("cityClassifyService")
public class CityClassifyServiceImpl extends ServiceImpl<CityClassifyMapper, CityClassify> implements CityClassifyService {

}

