package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.TeacherSocialMedia;

import java.util.List;

public interface TeacherSocialMediaDao {
    void saveTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia);
    List<TeacherSocialMedia> findAllTeacherSocialMedia();
    void deleteTeacherSocialMediaById(Long idTeacherSocialMedia);
    void updateTeacherSocialMedia(TeacherSocialMedia teacherSocialMedia);
    TeacherSocialMedia findTeacherSocialMediaById(Long idTeacherSocialMedia);
}
