package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Course;
import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.model.TeacherSocialMedia;

import java.util.List;

public interface SocialMediaDao {
    void saveTeacher(SocialMedia socialMedia);
    List<SocialMedia> findAllSocialMedia();
    void deleteSocialMediaById(Long idSocialMedia);
    void updateSocialMedia(SocialMedia socialMedia);
    SocialMedia findSocialMediaById(Long idSocialMedia);
    SocialMedia findSocialMediaByName(String name);
    TeacherSocialMedia findSocialMediaByIdAndNickName(Long idSocialMedia,String nickName);
}
