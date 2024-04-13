package com.ning;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.domain.entity.*;
import com.ning.service.*;
import com.ning.utils.KdfUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: qjn
 * @create: 2024/02/29 22:15
 **/
@SpringBootTest
@MapperScan("com.ning.mapper")
public class SplitSalary {
    @Autowired
    private WorkService workService;

    @Autowired
    private RelationService relationService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private KdfUtils kdfUtils;


    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserCompanyService userCompanyService;



    /**
     * 批量创建公司账号
     */
    @Test
    public void test() {
        List<Company> companyList = companyService.list();

        companyList.stream().forEach(item -> {
            String companyEncryptBrandid = item.getEncryptBrandid();

//            hrName = null == hrName?"fuChuangTest":hrName ;
            String companyName = item.getBrandName();
            Integer companyId = item.getId();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getCompanyId, companyId);

            if (userCompanyService.list(wrapper).size() == 0) {
                User user = new User();
                // 这里设置用户名不唯一
                user.setUsername("fuChuang" + item.getId())
                        .setPassword("123456")
                        .setMail("fuChuang" + item.getId())
                        .setName(companyName)
                        .setIsCompany(1);
                try {
                    //注册用户
                    userService.register(user);
                }catch (Exception e){
                    e.printStackTrace();
                }

                UserCompany userCompany = new UserCompany();

                userCompany.setCompanyId(companyId)
                        .setUserId(user.getId());
                // 保存用户公司对应关系
                userCompanyService.save(userCompany);
            }
        });
    }

    /**
     * 公司和职位id关联
     */
    @Test
    public void test2() {
        List<Company> companyList = companyService.list();

        companyList.stream().forEach(item -> {
            //公司唯一id
            String companyEncryptBrandid = item.getEncryptBrandid();
//            hrName = null == hrName ? "fuChuangTest" : hrName;
            Integer companyId = item.getId();
            //TODO error 注意这里EncryptBrandId被删除了，此方法暂时弃用
            LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Work::getEncryptBrandid, companyEncryptBrandid);
            List<Work> workList = workService.list(wrapper);
            if (workList != null) {
                for (Work work : workList) {
                    Integer workId = work.getId();
                    Relation relation = Relation.builder()
                            .workId(workId)
                            .companyId(companyId)
                            .build();
                    LambdaQueryWrapper<Relation> wrapper1 = new LambdaQueryWrapper<>();
                    wrapper1.eq(Relation::getWorkId, workId)
                            .eq(Relation::getCompanyId, companyId);
                    Relation one = relationService.getOne(wrapper1);
                    if (one == null) {
                        relationService.save(relation);
                    }

                }
            }
        });

    }



    //TODO 目前问题是，当一个公司有不同的行业时，会有两个不同的encrypt_brandId存在，而这两个不同的encrypt_bran
    //TODO 会绑定了两个不同的职位,导致另一个职位在插入时被忽略

    /**
     * 解决方案：插入时，list遍历时先按照每一个item的公司名查找是否有重名的。如果有重名的先处理重名公司
     * 把这个重名的公司对应的职位id找出来，绑定到
     */

    /**
     * 公司表去重
     */
    @Test
    public void test3() {
        List<Company> companyList = companyService.list();
        for (Company company : companyList) {
            String encryptBrandid = company.getEncryptBrandid();

            LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Company::getEncryptBrandid, encryptBrandid)
                    .select(Company::getId);
            List<Company> list = companyService.list(wrapper);
            if (list.size() > 1) {
                List<Integer> companyIdList = list.stream().map(Company::getId).collect(Collectors.toList());
                companyIdList.remove(0);
                companyService.deleteByIds(companyIdList);
            }
        }
    }


    /**
     * 职位表去重
     */
    @Test
    public void workDistinct() {
        List<Work> workList = workService.list();
        for (Work work : workList) {
            //TODO error 注意这里EncryptBrandId被删除了，此方法暂时弃用
//            String encryptBrandid = work.getEncryptBrandid();

            String skills = work.getSkills();
            Integer cityName = work.getCityName();
            String businessDistrict = work.getBusinessDistrict();

            LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
            wrapper
//                    .eq(Work::getEncryptBrandid, encryptBrandid)
                    .eq(Work::getSkills, skills)
                    .eq(Work::getCityName, cityName)
                    .eq(Work::getBusinessDistrict, businessDistrict)
                    .select(Work::getId);
            List<Work> list = workService.list(wrapper);
            if (list.size() > 1) {
                List<Integer> WorkIdList = list.stream().map(Work::getId).collect(Collectors.toList());
                WorkIdList.remove(0);
                workService.deleteByIds(WorkIdList);
            }
        }
    }


    /**
     * 公司和公司角色绑定
     */
    @Test
    public void cpyAssociatedWithRole(){
        List<User> userList = userService.list();
        userList.forEach(item -> {
            if(item.getIsCompany() == 1){
                Integer userId = item.getId();
                UserRole userRole = new UserRole();
                userRole.setUserId(Long.valueOf(userId))
                        //公司管理员
                        .setRoleId(4L);
                userRoleService.save(userRole);
            }
            else if(item.getIsCompany() == 2){
                Integer userId = item.getId();
                UserRole userRole = new UserRole();
                userRole.setUserId(Long.valueOf(userId))
                        //系统管理员
                        .setRoleId(2L);
                userRoleService.save(userRole);
            }
        });
    }


}