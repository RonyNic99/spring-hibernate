package com.rony.springhibernate.service;

import com.rony.springhibernate.dao.TeacherDao;
import com.rony.springhibernate.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Override
    public void saveTeacher(Teacher teacher) {
        teacherDao.saveTeacher(teacher);
    }

    @Override
    public List findAllTeacher() {
        return teacherDao.findAllTeacher();
    }

    @Override
    public Teacher findTeacherById(Long idTeacher) {
        return teacherDao.findTeacherById(idTeacher);
    }

    @Override
    public Teacher findTeacherByName(String name) {
        return teacherDao.findTeacherByName(name);
    }

    @Override
    public void deleteTeacherById(Long IdTeacher) {
        teacherDao.deleteTeacherById(IdTeacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherDao.updateTeacher(teacher);
    }
}
