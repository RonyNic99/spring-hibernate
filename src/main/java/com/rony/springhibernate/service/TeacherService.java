package com.rony.springhibernate.service;

import com.rony.springhibernate.model.Teacher;

import java.util.List;

public interface TeacherService {
    void saveTeacher(Teacher teacher);
    List findAllTeacher();
    Teacher findTeacherById(Long idTeacher);
    Teacher findTeacherByName(String name);
    void deleteTeacherById(Long IdTeacher);
    void updateTeacher(Teacher teacher);
}
