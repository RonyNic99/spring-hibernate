package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.model.Teacher;
import com.rony.springhibernate.model.TeacherSocialMedia;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class SocialMediaImpl extends AbstractSession implements SocialMediaDao {

    @Override
    public void saveSocialMedia(SocialMedia socialMedia) {
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

    @Override
    public SocialMedia findSocialMediaByName(String name) {
        return (SocialMedia) getSession().createQuery("from SocialMedia where name = :name").setParameter("name",name).uniqueResult();
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndNickName(Long idSocialMedia, String nickName) {
                 List<Object[ ]> objects = getSession().createQuery("from TeacherSocialMedia tsm join tsm.socialMedia sm " +
                "where sm.idSocialMedia = :idSocialMedia and tsm.nickname = :nickName")
                .setParameter("idSocialMedia",idSocialMedia)
                .setParameter("nickName",nickName)
                .list();

                if(objects.size() > 0){
                    for (Object[ ] object2: objects) {
                        for (Object object: object2) {
                            if (object instanceof TeacherSocialMedia){
                                return (TeacherSocialMedia) object;
                            }
                        }
                    }
                }
                return null;
    }

}
