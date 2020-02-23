package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.SocialMedia;

import java.util.List;

public class SocialMediaImpl extends AbstractSession implements SocialMediaDao {

    @Override
    public void saveTeacher(SocialMedia socialMedia) {
        getSession().persist(socialMedia);
    }

    @Override
    public List<SocialMedia> findAllSocialMedia() {
        return  getSession().createQuery("from SocialMedia").list();
    }

    @Override
    public void deleteSocialMediaById(Long idSocialMedia) {
        SocialMedia socialMedia = findSocialMediaById(idSocialMedia);
        if (socialMedia != null){
            getSession().delete(socialMedia);
        }
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        getSession().update(socialMedia);
    }

    @Override
    public SocialMedia findSocialMediaById(Long idSocialMedia) {
        return (SocialMedia) getSession().get(SocialMedia.class,idSocialMedia);
    }

}
