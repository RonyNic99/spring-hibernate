package com.rony.springhibernate.service;

import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.model.TeacherSocialMedia;

import java.util.List;

public interface SocialMediaService {
    void saveSocialMedia(SocialMedia socialMedia);
    List<SocialMedia> findAllSocialMedia();
    void deleteSocialMediaById(Long idSocialMedia);
    void updateSocialMedia(SocialMedia socialMedia);
    SocialMedia findSocialMediaById(Long idSocialMedia);
    SocialMedia findSocialMediaByName(String name);
    TeacherSocialMedia findSocialMediaByIdAndNickName(Long idSocialMedia, String nickName);
    TeacherSocialMedia findSocialMediaByIdTeacherAndIdSocialMedia(Long idTeacher, Long idSocialMedia);
}
