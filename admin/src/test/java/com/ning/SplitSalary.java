package com.ning;

import com.ning.UserApplication;
import com.ning.domain.entity.Work;
import com.ning.mapper.WorkMapper;
import com.ning.service.WorkService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: qjn
 * @create: 2024/02/29 22:15
 **/
@SpringBootTest
@MapperScan("com.ning.mapper")
public class SplitSalary {
    @Autowired
    private WorkService workService;

    @Test
    public void test(){
        List<Work> workList = workService.list();
        workList.forEach(item -> {
            String salary = item.getSalary();
            String[] split = salary.split("-");
            try {
                String start = split[0] + "K";
//            String end = (split[1].replace("[^\\d.]", ""));
                String end = (split[1].replaceAll("K.*", "")) + "K";
                System.out.println(start + "-" + end);
                item.setMaxSa(end);
                item.setMinSa(start);
                Work work = new Work();
                work.setMinSa(end)
                        .setMinSa(start)
                        .setId(work.getId());
                workService.updateById(item);
            }
            catch (Exception e){

            }
        });
    }
}