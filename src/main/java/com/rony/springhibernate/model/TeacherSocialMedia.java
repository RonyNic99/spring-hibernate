package com.rony.springhibernate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacher_social_media")
public class TeacherSocialMedia implements Serializable {
    @Id
    @Column(name = "id_teacher_social_media")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTeacherSocialMedia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_teacher")
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_social_media")
    private SocialMedia socialMedia;

    @Column(name = "nickname")
    private String nickname;

    public TeacherSocialMedia() {
        super();
    }

    public TeacherSocialMedia(Teacher teacher, SocialMedia socialMedia, String nickname) {
        this.teacher = teacher;
        this.socialMedia = socialMedia;
        this.nickname = nickname;
    }

    public Long getIdTeacherSocialMedia() {
        return idTeacherSocialMedia;
    }

    public void setIdTeacherSocialMedia(Long idTeacherSocialMedia) {
        this.idTeacherSocialMedia = idTeacherSocialMedia;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
