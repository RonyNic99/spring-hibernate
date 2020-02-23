package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.SocialMedia;

import java.util.List;

public interface SocialMediaDao {
    void saveTeacher(SocialMedia socialMedia);
    List<SocialMedia> findAllSocialMedia();
    void deleteSocialMediaById(Long idSocialMedia);
    void updateSocialMedia(SocialMedia socialMedia);
    SocialMedia findSocialMediaById(Long idSocialMedia);
}
