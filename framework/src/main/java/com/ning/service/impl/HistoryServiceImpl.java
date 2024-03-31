package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.History;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.HistoryVo;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.HistoryMapper;
import com.ning.service.HistoryService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    @Autowired
    private WorkService workService;

    /**
     * 查询当前用户的浏览历史
     * @return
     */
    @Override
    public Result<List<HistoryVo>> getHistoryByUser() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDto.getUser();
//        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(History::getUserId,user.getId());
//        List<History> histories = list(wrapper);
        List<WorkVo> works = historyMapper.getListByUserId(user.getId());
        List<HistoryVo> historyVos = BeanCopyUtils.copyBeanList(works, HistoryVo.class);
        return Result.success(historyVos);
    }
    /**
     * 根据历史记录id查询职位详细信息
     * @param id
     * @return
     */
    @Override
    public Result<WorkVo> getHistoryById(Integer id) {
        History history = getById(id);
        Integer workId = history.getWorkId();
        return workService.getByWorkId(workId);
    }
}

