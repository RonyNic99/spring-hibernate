package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Teacher;
import com.rony.springhibernate.model.TeacherSocialMedia;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
@Repository
@Transactional
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
      return (Teacher) getSession().createQuery("from Teacher where name = :name ").setParameter("name",name).uniqueResult();
    }

    public void deleteTeacherById(Long IdTeacher_) {
        Teacher teacher = findTeacherById(IdTeacher_);
        if (teacher != null){
            Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedias().iterator();
            while (i.hasNext()){
                TeacherSocialMedia teacherSocialMedia = i.next();
                i.remove();
                getSession().delete(teacherSocialMedia);
            }
            teacher.getTeacherSocialMedias().clear();
            getSession().delete(teacher);
        }
    }

    public void updateTeacher(Teacher teacher) {
        getSession().update(teacher);
    }
}
