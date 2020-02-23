package com.rony.springhibernate.service;

import com.rony.springhibernate.dao.CourseDao;
import com.rony.springhibernate.dao.CourseDaoImpl;
import com.rony.springhibernate.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseDao courseDao;
    @Override
    public void saveCourse(Course course) {
        courseDao.saveCourse(course);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseDao.findAllCourse();
    }

    @Override
    public void deleteCourseById(Long idCourse) {
        courseDao.findCourseById(idCourse);
    }

    @Override
    public void updateCourse(Course course) {
        courseDao.updateCourse(course);
    }

    @Override
    public Course findCourseById(Long idCourse) {
        return courseDao.findCourseById(idCourse);
    }

    @Override
    public Course findCourseByName(String name) {
        return courseDao.findCourseByName(name);
    }

    @Override
    public List<Course> findCourseByIdTeacher(Long idTeacher) {
        return courseDao.findCourseByIdTeacher(idTeacher);
    }
}
