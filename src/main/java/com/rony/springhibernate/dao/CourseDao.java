package com.rony.springhibernate.dao;

import com.rony.springhibernate.model.Course;

import java.util.List;

public interface CourseDao {
    void saveCourse(Course course);
    List<Course> findAllCourse();
    void deleteCourseById(Long idCourse);
    void updateCourse(Course course);
    Course findCourseById(Long idCourse);
    Course findCourseByName(String name);
    List<Course> findCourseByIdTeacher(Long idTeacher);

}
