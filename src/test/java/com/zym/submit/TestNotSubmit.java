package com.zym.submit;

import com.zym.submit.dto.TaskDTO;
import com.zym.submit.service.impl.ReportServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zym
 * @date 2019-10-02-17:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestNotSubmit {

    @Autowired
    private ReportServiceImpl reportService;

    @Test
    public void test_NotSubmit() {
        List<TaskDTO> taskDTOList = reportService.listNotSubmit("1001", 1);
        System.out.println(taskDTOList);
    }
}
