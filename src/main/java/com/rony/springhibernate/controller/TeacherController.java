package com.rony.springhibernate.controller;

import com.rony.springhibernate.model.Course;
import com.rony.springhibernate.model.SocialMedia;
import com.rony.springhibernate.model.Teacher;
import com.rony.springhibernate.model.TeacherSocialMedia;
import com.rony.springhibernate.service.CourseService;
import com.rony.springhibernate.service.SocialMediaService;
import com.rony.springhibernate.service.TeacherService;
import com.rony.springhibernate.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Column;
import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/v1")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SocialMediaService socialMediaService;
    @Autowired
    private CourseService courseService;

    //GET
    @RequestMapping(value = "/teacher",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<List<Teacher>> getAllTeacher(){
        List<Teacher> teacherList = new ArrayList<>();
        teacherList = teacherService.findAllTeacher();
        return new ResponseEntity<List<Teacher>>(teacherList, HttpStatus.OK);
    }
    //GET BY ID
    @RequestMapping(value = "/teacher/{id}",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long idTeacher){
        if (idTeacher == null || idTeacher <= 0 ){
            return new ResponseEntity(new CustomErrorType("idTeacher is require"),HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherService.findTeacherById(idTeacher);
        if (teacher == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<Teacher>(teacher,HttpStatus.OK);
    }
    //DELETE
    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long idTeacher){
        System.out.println("teacher ID recived: " + idTeacher);
        if (idTeacher == null || idTeacher <= 0 ){
            return new ResponseEntity(new CustomErrorType("id Teacher is require"),HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherService.findTeacherById(idTeacher);
        if (teacher == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        teacherService.deleteTeacherById(idTeacher);
        return new ResponseEntity<Course>(HttpStatus.OK);
    }
    public static final String TEACHER_UPLOADED_FOLDER ="img/teachers/";
    //CREATE TEACHER IMAG
    @RequestMapping(value = "/teacher/image", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("idTeacher") Long idTeacher,
                                                     @RequestParam("file") MultipartFile multipartFile,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        if (idTeacher == null) {
            return new ResponseEntity(new CustomErrorType("id Teacher is require"), HttpStatus.CONFLICT);
        }
        if (multipartFile.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Asignale una imagen"), HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherService.findTeacherById(idTeacher);
        if (teacher == null) {
            return new ResponseEntity(new CustomErrorType("El idTeacher " + teacher.getIdTeacher() + " no existe"), HttpStatus.CONFLICT);
        }
        if (teacher.getAvatar().isEmpty() || teacher.getAvatar() != null) {
            String fileName = teacher.getAvatar();
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
            String fileName = String.valueOf(idTeacher) + "-pictureTeacher-" + dateName + "." + multipartFile.getContentType().split("/")[1];
            //Insertar el registo
            teacher.setAvatar(TEACHER_UPLOADED_FOLDER + fileName);
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
            Files.write(path, bytes);
            teacherService.updateTeacher(teacher);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
          return new ResponseEntity(new CustomErrorType("Ocurrio un error en la subida del archivo" + multipartFile), HttpStatus.CONFLICT);
        }
    }
    //GET IMAGE
    @RequestMapping(value = "/teacher/{id}/image",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getTeacherImage(@PathVariable("id") Long idTeacher){
        if (idTeacher == null){
            new ResponseEntity(new CustomErrorType("id Teacher is require"), HttpStatus.NO_CONTENT);
        }
        Teacher teacher = teacherService.findTeacherById(idTeacher);
        if (teacher == null){
            new ResponseEntity(new CustomErrorType("id Teacher " +idTeacher + " is invalid"), HttpStatus.NO_CONTENT);
        }
        try {
            String fileName = teacher.getAvatar();
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
    @RequestMapping(value = "/teacher/{id}/image",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeacherImage(@PathVariable("id") Long idTeacher){
        if (idTeacher == null) {
            return new ResponseEntity(new CustomErrorType("id Teacher is require"), HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherService.findTeacherById(idTeacher);
        if (teacher == null) {
            return new ResponseEntity(new CustomErrorType("El idTeacher " + teacher.getIdTeacher() + " no existe"), HttpStatus.CONFLICT);
        }
        if(teacher.getAvatar().isEmpty() || teacher.getAvatar() == null){
            return new ResponseEntity(new CustomErrorType("Este profesor no tiene asignada una imagen"), HttpStatus.CONFLICT);
        }
        String fileName = teacher.getAvatar();
        Path path = Paths.get(fileName);
        File file = path.toFile();
        if (file.exists()){
            file.delete();
        }
        teacher.setAvatar("");
        teacherService.updateTeacher(teacher);
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "teachers/socialMedias",method = RequestMethod.PATCH,headers = "Accept=application/json")
    public ResponseEntity<?> assignTeacherSocialMedia(@RequestBody Teacher teacher,UriComponentsBuilder uriComponentsBuilder){
        if (teacher.getIdTeacher() == null){
            return new ResponseEntity(new CustomErrorType("Necesitamos al menos idTeacher , idSocialMedia and nickname"), HttpStatus.NO_CONTENT);
        }

        Teacher teachersave = teacherService.findTeacherById(teacher.getIdTeacher());
        System.out.println("Entro");
        if (teachersave == null){
            return new ResponseEntity(new CustomErrorType("El idTeacher "+ teacher.getIdTeacher() + " no existe"), HttpStatus.NO_CONTENT);
        }

        if (teacher.getTeacherSocialMedias().size() == 0){
            return new ResponseEntity(new CustomErrorType("Necesitamos que el Objeto Teacher que me estas mandando contenga IdSocialmedia y nickname"), HttpStatus.NO_CONTENT);
        }else{
            Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedias().iterator();
            while (i.hasNext()){
                TeacherSocialMedia teacherSocialMedia = i.next();
                if (teacherSocialMedia.getSocialMedia().getIdSocialMedia() == null || teacherSocialMedia.getNickname() == null){
                    return new ResponseEntity(new CustomErrorType("Necesitamos que el objeto Teacher que me estas mandando contenga IdSocialMedia y nick name"), HttpStatus.NO_CONTENT);
                }else{
                    TeacherSocialMedia tsmAux = socialMediaService.findSocialMediaByIdAndNickName(teacherSocialMedia.getSocialMedia().getIdSocialMedia(),teacherSocialMedia.getNickname());
                    System.out.println("Entro Al else");
                    if (tsmAux != null){
                        return new ResponseEntity(new CustomErrorType("De IdSocialMedia" +teacherSocialMedia.getSocialMedia().getIdSocialMedia() + " con el nickname " + teacherSocialMedia.getNickname() +" Ya existe "), HttpStatus.CONFLICT);
                    }
                    SocialMedia socialMedia = socialMediaService.findSocialMediaById(teacherSocialMedia.getSocialMedia().getIdSocialMedia());
                    System.out.println("Entro al else 2");
                    if (socialMedia == null){
                        return new ResponseEntity(new CustomErrorType("El idSocialMedia "+teacherSocialMedia.getSocialMedia().getIdSocialMedia() +" no fue encontrado"), HttpStatus.NO_CONTENT);
                    }
                    teacherSocialMedia.setSocialMedia(socialMedia);
                    teacherSocialMedia.setTeacher(teachersave);

                    if (tsmAux == null){
                        teachersave.getTeacherSocialMedias().add(teacherSocialMedia);
                    }else{
                        LinkedList<TeacherSocialMedia> teacherSocialMedias = new LinkedList<>();
                        teacherSocialMedias.addAll(teachersave.getTeacherSocialMedias());
                        System.out.println("Entro a la parte que queriamos");
                        for (int j = 0; j < teacherSocialMedias.size() ; j++) {
                            TeacherSocialMedia teacherSocialMedia2 = teacherSocialMedias.get(j);
                            if (teacherSocialMedia.getTeacher().getIdTeacher() == teacherSocialMedia2.getTeacher().getIdTeacher()
                             && teacherSocialMedia.getSocialMedia().getIdSocialMedia() == teacherSocialMedia2.getSocialMedia().getIdSocialMedia()){
                                    teacherSocialMedia2.setNickname(teacherSocialMedia.getNickname());
                                    teacherSocialMedias.set(j,teacherSocialMedia2);
                            }else {
                                teacherSocialMedias.set(j,teacherSocialMedia2);
                            }
                        }
                        teachersave.getTeacherSocialMedias().clear();
                        teachersave.getTeacherSocialMedias().addAll(teacherSocialMedias);

                    }


                }
            }

        }

        teacherService.updateTeacher(teachersave);
        System.out.println("Entro");
        return new ResponseEntity<Teacher>(teachersave,HttpStatus.OK);

    }

    @RequestMapping(value = "teachers/course",method = RequestMethod.PATCH,headers = "Accept=application/json")
    public ResponseEntity<?> assignCourse(@RequestBody Teacher teacher,UriComponentsBuilder uriComponentsBuilder){
        if (teacher.getIdTeacher() == null){
            return new ResponseEntity(new CustomErrorType("Necesitamos al menos Course"), HttpStatus.NO_CONTENT);
        }
        Teacher teachersave = teacherService.findTeacherById(teacher.getIdTeacher());
        if (teachersave == null){
            return new ResponseEntity(new CustomErrorType("El idTeacher "+ teacher.getIdTeacher() + " no existe"), HttpStatus.NO_CONTENT);
        }
        if (teacher.getCourses().size() == 0) {
            return new ResponseEntity(new CustomErrorType("Necesitamos que el Objeto Teacher que me estas mandando contenga IdCourse y nickname"), HttpStatus.NO_CONTENT);
        }else{
            Iterator<Course> i = teacher.getCourses().iterator();
            while (i.hasNext()){
                Course course = i.next();
                if (course.getProject() == null || course.getName() == null || course.getThemes()== null){
                    return new ResponseEntity(new CustomErrorType("Necesitamos que el objeto Courses contenga todos los atributos"), HttpStatus.NO_CONTENT);
                }else {
                    Course courseAux = courseService.findCourseById(course.getIdCourse());
                    if (courseAux != null){
                        return new ResponseEntity(new CustomErrorType("Ya le asignaste este curso "+ course.getName() + " a este profesor"),HttpStatus.CONFLICT);
                    }
                    course.setTeacher(teachersave);
                    teachersave.getCourses().add(course);
                }
            }
        }
        teacherService.updateTeacher(teachersave);
        return new ResponseEntity<Teacher>(teachersave,HttpStatus.OK);
    }

}
