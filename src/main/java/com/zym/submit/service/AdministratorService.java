package com.zym.submit.service;

import com.zym.submit.dto.AdministratorDTO;

/**
 * @author zym
 * @date 2019-11-14-19:34
 */
public interface AdministratorService {

    AdministratorDTO login(String teacherNumber, String teacherPassword);

    void deleteStu();

    void deleteTeacher();


}
