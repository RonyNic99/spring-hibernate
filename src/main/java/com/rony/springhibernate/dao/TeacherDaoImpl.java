package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Teacher;

import java.util.List;

public class TeacherDaoImpl extends AbstractSession implements TeacherDao {


    public void saveTeacher(Teacher teacher) {
        getSession().persist(teacher);
    }

    public List<Teacher> findAllTeacher() {
        return getSession().createQuery("from Teacher").list();
    }

    @Override
    public Teacher findTeacherById(Long idTeacher) {
         return (Teacher) getSession().get(Teacher.class,idTeacher);
    }


    public Teacher findTeacherByName(String name) {
      return (Teacher) getSession().createQuery("from Teacher where name = :name ").setParameter(name,name).uniqueResult();
    }

    public void deleteTeacherById(Long IdTeacher_) {
        Teacher teacher = findTeacherById(IdTeacher_);
        if (teacher != null){
            getSession().delete(teacher);
        }
    }

    public void updateTeacher(Teacher teacher) {
        getSession().update(teacher);
    }
}
