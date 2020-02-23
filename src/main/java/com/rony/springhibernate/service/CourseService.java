package com.rony.springhibernate.service;

import com.rony.springhibernate.model.Course;

import java.util.List;

public interface CourseService {
    void saveCourse(Course course);
    List<Course> findAllCourse();
    void deleteCourseById(Long idCourse);
    void updateCourse(Course course);
    Course findCourseById(Long idCourse);
    Course findCourseByName(String name);
    List<Course> findCourseByIdTeacher(Long idTeacher);
}
