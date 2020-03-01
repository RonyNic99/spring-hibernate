package com.rony.springhibernate.controller;

import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    //GET
    @RequestMapping(value = "/socialMedias/{id}",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> getSocialMediasById(@PathVariable("id") Long idSocialMedia){
        if (idSocialMedia == null || idSocialMedia <= 0 ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<SocialMedia>(socialMedia,HttpStatus.OK);
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
    //UPDATE
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> updateSocialMedia(@PathVariable("id") Long idSocialMedia,@RequestBody SocialMedia socialMedia){
        SocialMedia currentSocialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (currentSocialMedia == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentSocialMedia.setName(socialMedia.getName());
        currentSocialMedia.setIcon(socialMedia.getIcon());

        socialMediaService.updateSocialMedia(currentSocialMedia);
        return new ResponseEntity<SocialMedia>(currentSocialMedia,HttpStatus.OK);
    }
    //DELETE
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteSocialMedia(@PathVariable("id") Long idSocialMedia){
        if (idSocialMedia == null || idSocialMedia <= 0 ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        socialMediaService.deleteSocialMediaById(idSocialMedia);
       return new ResponseEntity<SocialMedia>(HttpStatus.OK);
    }
}
