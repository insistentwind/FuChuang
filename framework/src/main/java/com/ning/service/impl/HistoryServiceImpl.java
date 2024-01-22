package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.dto.ResumeVo;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.History;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.mapper.HistoryMapper;
import com.ning.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * (History)表服务实现类
 *
 * @author makejava
 * @since 2024-01-20 21:58:09
 */
@Service("historyService")
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    /**
     * 查询当前用户的浏览历史
     * @return
     */
    @Override
    public Result<List<ResumeVo>> getHistoryByUser() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDto.getUser();
//        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(History::getUserId,user.getId());
//        List<History> histories = list(wrapper);
        List<ResumeVo> resumes = historyMapper.getListByUserId(user.getId());
        return Result.success(resumes);
    }
}

