package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Teacher;

import java.util.List;

public interface TeacherDao {
    void saveTeacher(Teacher teacher);
    List findAllTeacher();
    Teacher findTeacherById(Long idTeacher);
    Teacher findTeacherByName(String name);
    void deleteTeacherById(Long IdTeacher);
    void updateTeacher(Teacher teacher);
}
