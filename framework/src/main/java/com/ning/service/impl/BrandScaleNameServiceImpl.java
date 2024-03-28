package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.BrandScaleNameMapper;
import com.ning.domain.entity.BrandScaleName;
import com.ning.service.BrandScaleNameService;
import org.springframework.stereotype.Service;

/**
 * (BrandScaleName)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 17:04:34
 */
@Service("brandScaleNameService")
public class BrandScaleNameServiceImpl extends ServiceImpl<BrandScaleNameMapper, BrandScaleName> implements BrandScaleNameService {

}

