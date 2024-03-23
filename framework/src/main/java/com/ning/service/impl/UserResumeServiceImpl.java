package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import .dao.UserResumeDao;
import .entity.UserResume;
import .service.UserResumeService;
import org.springframework.stereotype.Service;

/**
 * (UserResume)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 21:38:21
 */
@Service("userResumeService")
public class UserResumeServiceImpl extends ServiceImpl<UserResumeMapper, UserResume> implements UserResumeService {

}

