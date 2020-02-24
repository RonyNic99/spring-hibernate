package com.rony.springhibernate.controller;

import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/v1")
public class SocialMediaController {
    @Autowired
    private SocialMediaService socialMediaService;
    //GET
    @RequestMapping(value = "/socialMedias",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<List<SocialMedia>> getSocialMedias(){
        List<SocialMedia> socialMediaList = new ArrayList<>();
        socialMediaList = socialMediaService.findAllSocialMedia();
        if(socialMediaList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<SocialMedia>>(socialMediaList,HttpStatus.OK);
    }
    //POST
    @RequestMapping(value = "/socialMedias",method = RequestMethod.POST,headers = "Accept=application/json")
    public ResponseEntity<?> createSocialMedia(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponentsBuilder){
        if (socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (socialMediaService.findSocialMediaByName(socialMedia.getName())!= null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        socialMediaService.saveSocialMedia(socialMedia);
        SocialMedia socialMedia2 = socialMediaService.findSocialMediaByName(socialMedia.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriComponentsBuilder.path("/v1/socialMedias/{id}")
                        .buildAndExpand(socialMedia2.getIdSocialMedia())
                        .toUri()
        );
        return new ResponseEntity<String>( headers,HttpStatus.CREATED);


    }
}
