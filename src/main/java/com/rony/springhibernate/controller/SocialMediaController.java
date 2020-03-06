package com.rony.springhibernate.controller;

import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.service.SocialMediaService;
import com.rony.springhibernate.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/v1")
public class SocialMediaController {
    @Autowired
    private SocialMediaService socialMediaService;
    //GET BY TOTAL OR NAME
    @RequestMapping(value = "/socialMedias",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<List<SocialMedia>> getSocialMedias(@RequestParam (value = "name",required = false ) String name){
        List<SocialMedia> socialMediaList = new ArrayList<>();
        if (name == null){
            socialMediaList = socialMediaService.findAllSocialMedia();
            if(socialMediaList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<SocialMedia>>(socialMediaList,HttpStatus.OK);
        }else {
            SocialMedia socialMedia = socialMediaService.findSocialMediaByName(name);
            if (socialMedia == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            socialMediaList.add(socialMedia);
            return new ResponseEntity<List<SocialMedia>>(socialMediaList,HttpStatus.OK);
        }
    }
    //GET
    @RequestMapping(value = "/socialMedias/{id}",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> getSocialMediasById(@PathVariable("id") Long idSocialMedia){
        if (idSocialMedia == null || idSocialMedia <= 0 ){
            return new ResponseEntity(new CustomErrorType("id Social media is require"),HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("name Social media is require"),HttpStatus.CONFLICT);
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
            return new ResponseEntity(new CustomErrorType("id Social media is require"),HttpStatus.CONFLICT);
        }
       SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        socialMediaService.deleteSocialMediaById(idSocialMedia);
       return new ResponseEntity<SocialMedia>(HttpStatus.OK);
    }
    public static final String TEACHER_UPLOADED_FOLDER ="img/socialmedia/";
    //CREATE TEACHER IMAG
    @RequestMapping(value = "/socialMedia/image", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("idSocialMedia") Long idSocialMedia,
                                                     @RequestParam("file") MultipartFile multipartFile,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        if (idSocialMedia == null) {
            return new ResponseEntity(new CustomErrorType("id SocialMedia is require"), HttpStatus.CONFLICT);
        }
        if (multipartFile.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Asignale una imagen"), HttpStatus.CONFLICT);
        }
        SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null) {
            return new ResponseEntity(new CustomErrorType("El idSocialMedia " + idSocialMedia + " no existe"), HttpStatus.CONFLICT);
        }
        if (socialMedia.getIcon().isEmpty() || socialMedia.getIcon() != null) {
            String fileName = socialMedia.getIcon();
            Path path = Paths.get(fileName);
            File f = path.toFile();
            if (f.exists()) {
                f.delete();
            }
        }
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-mm-ss");
            String dateName = simpleDateFormat.format(date);
            String fileName = String.valueOf(idSocialMedia) + "-pictureTeacher-" + dateName + "." + multipartFile.getContentType().split("/")[1];
            //Insertar el registo
            socialMedia.setIcon(TEACHER_UPLOADED_FOLDER + fileName);
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
            Files.write(path, bytes);
            socialMediaService.updateSocialMedia(socialMedia);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new CustomErrorType("Ocurrio un error en la subida del archivo" + multipartFile), HttpStatus.CONFLICT);
        }
    }
    //GET IMAGE
    @RequestMapping(value = "/socialMedia/{id}/image",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getTeacherImage(@PathVariable("id") Long idSocialMedia){
        if (idSocialMedia == null){
            new ResponseEntity(new CustomErrorType("id Teacher is require"), HttpStatus.NO_CONTENT);
        }
        SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null){
            new ResponseEntity(new CustomErrorType("id SocialMedia" +idSocialMedia + " is invalid"), HttpStatus.NO_CONTENT);
        }
        try {
            String fileName = socialMedia.getIcon();
            Path path = Paths.get(fileName);
            File file = path.toFile();
            if (!file.exists()){
                return new ResponseEntity(new CustomErrorType("Imagen no encontrada"), HttpStatus.NO_CONTENT);
            }
            byte[] image = Files.readAllBytes(path);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(new CustomErrorType("error al traer la imagen"), HttpStatus.NO_CONTENT);
        }
    }
    @RequestMapping(value = "/socialMedia/{id}/image",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeacherImage(@PathVariable("id") Long idSocialMedia){
        if (idSocialMedia == null) {
            return new ResponseEntity(new CustomErrorType("id SocialMedia is require"), HttpStatus.CONFLICT);
        }
        SocialMedia socialMedia = socialMediaService.findSocialMediaById(idSocialMedia);
        if (socialMedia == null) {
            return new ResponseEntity(new CustomErrorType("El idSocialMedia " + idSocialMedia + " no existe"), HttpStatus.CONFLICT);
        }
        if(socialMedia.getIcon().isEmpty() || socialMedia.getIcon() == null){
            return new ResponseEntity(new CustomErrorType("Este profesor no tiene asignada una imagen"), HttpStatus.CONFLICT);
        }
        String fileName = socialMedia.getIcon();
        Path path = Paths.get(fileName);
        File file = path.toFile();
        if (file.exists()){
            file.delete();
        }
        socialMedia.setIcon("");
        socialMediaService.updateSocialMedia(socialMedia);
        return new ResponseEntity<SocialMedia>(HttpStatus.NO_CONTENT);
    }

}
