package com.zhy.submit.teacher.service.servieImpl;

import com.zhy.submit.teacher.mapper.ClassMapper;
import com.zhy.submit.teacher.mapper.CourseMapper;
import com.zhy.submit.teacher.mapper.GradeMapper;
import com.zhy.submit.teacher.mapper.TermMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class addReportServiceImplTest {

@Autowired private  addReportServiceImpl addReportService;
@Autowired private TermMapper termMapper;
@Autowired private ClassMapper classMapper;
@Autowired private CourseMapper courseMapper;
    @Test
    public void insertFileURL() {
    }

    @Test
    public void insertGrade() {

    }

    @Test
    public void getTeamId() {
        Integer i=termMapper.selectTermIdByForm("2019-2020","第1学期","19级");
        System.out.println(i);
    }

    @Test
    public void getclassID() {
        Integer integer=classMapper.getClassId("医软1班");
        System.out.println(integer);
    }

    @Test
    public void getCourseId() {
        Integer integer=courseMapper.selectCourseId("计算机网络");
        System.out.println(integer);

    }
}