package com.rony.springhibernate.controller;

import com.rony.springhibernate.model.Course;
import com.rony.springhibernate.service.CourseServiceImpl;
import com.rony.springhibernate.service.TeacherServiceImpl;
import com.rony.springhibernate.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;
    private TeacherServiceImpl teacherService;
    //GET ALL COURSE
    @RequestMapping(value = "/course",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<List<Course>> getAllCourse(){
        List<Course> courseList = new ArrayList<>();
        courseList = courseService.findAllCourse();
        return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
    }
    //GET BY ID
    @RequestMapping(value = "/course/{id}",method = RequestMethod.GET,headers = "Accept=application/json")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long idCourse){
        if (idCourse == null || idCourse <= 0 ){
            return new ResponseEntity(new CustomErrorType("idCouse is require"),HttpStatus.CONFLICT);
        }
        Course course = courseService.findCourseById(idCourse);
        if (course == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<Course>(course,HttpStatus.OK);
    }
    //POST
    @RequestMapping(value = "/course",method = RequestMethod.POST,headers = "Accept=application/json")
    public ResponseEntity<?> createCourse(@RequestBody Course course, UriComponentsBuilder uriComponentsBuilder){
        if (courseService.findCourseByName(course.getName())!=null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        courseService.saveCourse(course);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriComponentsBuilder.path("/v1/course/{id}")
                        .buildAndExpand(course.getIdCourse())
                        .toUri()
        );
        return new ResponseEntity<String>( headers,HttpStatus.CREATED);
    }
    //POST
    /*
    @RequestMapping(value = "/course/{id}",method = RequestMethod.POST,headers = "Accept=application/json")
    public ResponseEntity<?> assignTeacherCourse(@PathVariable("id") Long idCourse, @RequestParam(value = "teacherId",required = false) Long idTeacher ){
        Course course = courseService.findCourseById(idCourse);
        if(course != null){
            Teacher teacher = teacherService.findTeacherById(idTeacher);
            if (teacher != null){
                course.setTeacher(teacher);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new CustomErrorType("IdTeacher invalido"),HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(new CustomErrorType("IdCouse invalido"),HttpStatus.NO_CONTENT);
        }

    }*/
    //UPDATE
    @RequestMapping(value = "/course/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public ResponseEntity<Course> updateSocialMedia(@PathVariable("id") Long idCourse,@RequestBody Course course){
        Course currentCourse = courseService.findCourseById(idCourse);
        if (currentCourse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentCourse.setName(course.getName());
        currentCourse.setProject(course.getProject());
        currentCourse.setThemes(course.getThemes());
        currentCourse.setTeacher(course.getTeacher());

        courseService.updateCourse(currentCourse);
        return new ResponseEntity<Course>(currentCourse,HttpStatus.OK);
    }
    //DELETE
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long idCourse){
        System.out.println("course ID recived: " + idCourse);
        if (idCourse == null || idCourse <= 0 ){
            return new ResponseEntity(new CustomErrorType("id Social media is require"),HttpStatus.CONFLICT);
        }
        Course course = courseService.findCourseById(idCourse);
        if (course == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        courseService.deleteCourseById(idCourse);
        return new ResponseEntity<Course>(HttpStatus.OK);
    }

}
