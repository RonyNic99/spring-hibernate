package com.rony.springhibernate.service;

import com.rony.springhibernate.dao.SocialMediaDao;
import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.model.TeacherSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {
    @Autowired
    private SocialMediaDao socialMediaDao;

    public void saveTeacher(SocialMedia socialMedia) {
        socialMediaDao.saveSocialMedia(socialMedia);
    }

    @Override
    public List<SocialMedia> findAllSocialMedia() {
        return socialMediaDao.findAllSocialMedia();
    }

    @Override
    public void deleteSocialMediaById(Long idSocialMedia) {
        socialMediaDao.deleteSocialMediaById(idSocialMedia);
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        socialMediaDao.updateSocialMedia(socialMedia);
    }

    @Override
    public SocialMedia findSocialMediaById(Long idSocialMedia) {
        return socialMediaDao.findSocialMediaById(idSocialMedia);
    }

    @Override
    public SocialMedia findSocialMediaByName(String name) {
        return socialMediaDao.findSocialMediaByName(name);
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndNickName(Long idSocialMedia, String nickName) {
        return socialMediaDao.findSocialMediaByIdAndNickName(idSocialMedia,nickName);
    }
}
