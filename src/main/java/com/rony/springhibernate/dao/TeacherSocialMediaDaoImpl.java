package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Teacher;
import com.rony.springhibernate.model.TeacherSocialMedia;

import java.util.List;

public class TeacherSocialMediaDaoImpl extends AbstractSession implements TeacherSocialMediaDao {
    @Override
    public void saveTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
        getSession().persist(teacherSocialMedia);
    }

    @Override
    public List<TeacherSocialMedia> findAllTeacherSocialMedia() {
        return getSession().createQuery("from TeacherSocialMedia ").list();
    }

    @Override
    public void deleteTeacherSocialMediaById(Long idTeacherSocialMedia) {
            TeacherSocialMedia teacherSocialMedia = findTeacherSocialMediaById(idTeacherSocialMedia);
            if (teacherSocialMedia != null){
                getSession().delete(teacherSocialMedia);
            }
    }

    @Override
    public void updateTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia) {
        getSession().update(teacherSocialMedia);
    }

    @Override
    public TeacherSocialMedia findTeacherSocialMediaById(Long idTeacherSocialMedia) {
        return (TeacherSocialMedia) getSession().get(TeacherSocialMedia.class,idTeacherSocialMedia);
    }
}
