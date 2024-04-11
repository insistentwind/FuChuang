package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserCompany;
import com.ning.domain.entity.UserPermitcompany;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPermitcompanyVo;
import com.ning.exception.BaseException;
import com.ning.exception.SystemException;
import com.ning.mapper.AckMapper;
import com.ning.domain.entity.Ack;
import com.ning.service.AckService;
import com.ning.service.UserCompanyService;
import com.ning.service.UserPermitcompanyService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * (Ack)表服务实现类
 *
 * @author makejava
 * @since 2024-04-03 22:16:26
 */
@Service("ackService")
public class AckServiceImpl extends ServiceImpl<AckMapper, Ack> implements AckService {
    @Autowired
    private UserPermitcompanyService userPermitcompanyService;
    @Autowired
    private UserCompanyService userCompanyService;

    /**
     * 查看所有收到的查看简历申请
     *
     * @return
     */
    @Override
    public Result<List<Ack>> getAll() {
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        LambdaQueryWrapper<Ack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ack::getUserId, user.getId())
                .eq(Ack::getIsCompany, SystemConstants.IS_COMPANY);
        List<Ack> list = this.list(wrapper);
        return Result.success(list);
    }

    /**
     * 允许公司查看简历
     *
     * @param userPermitcompanyVo
     * @return
     */
    @Override
    public Result<String> allow(UserPermitcompanyVo userPermitcompanyVo) {
        UserPermitcompany userPermitcompany = BeanCopyUtils.copyBean(userPermitcompanyVo, UserPermitcompany.class);
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        if (!Objects.equals(userPermitcompany.getUserId(), user.getId())) {
            return Result.error(SystemConstants.OPERATION_NOT_COMPARE_WITH_USER);
        }
        LambdaQueryWrapper<UserPermitcompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermitcompany::getUserId, userPermitcompanyVo.getUserId())
                .eq(UserPermitcompany::getCompanyPermitId, userPermitcompanyVo.getCompanyPermitId());
        UserPermitcompany userPermit = userPermitcompanyService.getOne(wrapper);

        if (Objects.equals(userPermitcompanyVo.getAgree(), SystemConstants.AGREE_TO_SEE)) {
            Ack ack = new Ack();
            ack.setContent("用户同意了您的申请");
            ack.setIsCompany(SystemConstants.IS_NOT_COMPANY);
            ack.setRead(SystemConstants.HAS_NO_READ)
                    .setUserId(user.getId())
                    .setTime(LocalDateTime.now())
                    .setCompanyId(userPermitcompanyVo.getCompanyPermitId());
            this.save(ack);
            if (userPermit == null) {
                userPermitcompanyService.save(userPermitcompany);
            } else {
                userPermitcompany.setId(userPermit.getId());
                userPermitcompanyService.updateById(userPermitcompany);
            }

            return Result.success(SystemConstants.SUCCESS);
        } else {
            Ack ack = new Ack();
            ack.setContent("用户拒绝了您的申请");
            ack.setIsCompany(SystemConstants.IS_NOT_COMPANY)
                    .setRead(SystemConstants.HAS_NO_READ)
                    .setUserId(user.getId())
                    .setTime(LocalDateTime.now())
                    .setCompanyId(userPermitcompanyVo.getCompanyPermitId());
            this.save(ack);

            if (userPermit == null) {
                userPermitcompanyService.save(userPermitcompany);
            } else {
                userPermitcompany.setId(userPermit.getId());
                userPermitcompanyService.updateById(userPermitcompany);
            }
            return Result.success(SystemConstants.SUCCESS);
        }
    }

    /**
     * 查看所有收到的简历查看申请
     *
     * @return
     */
    @Override
    public Result<List<Ack>> getCompanyAll() {
        Integer companyId = null;
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, user.getId());
            List<UserCompany> list = userCompanyService.list(wrapper);
            if (list.size() > 0) {
                UserCompany userCompany = list.get(0);
                companyId = userCompany.getCompanyId();
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        LambdaQueryWrapper<Ack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ack::getCompanyId, companyId)
                .eq(Ack::getIsCompany, SystemConstants.IS_NOT_COMPANY);
        List<Ack> list = this.list(wrapper);
        return Result.success(list);
    }
}

