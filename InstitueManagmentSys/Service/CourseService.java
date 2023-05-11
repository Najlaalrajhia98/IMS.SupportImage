package com.thedynamicdoers.InstitueManagmentSys.Service;

import com.thedynamicdoers.InstitueManagmentSys.Model.Course;
import com.thedynamicdoers.InstitueManagmentSys.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // get a specific course info
    public Optional<Course> getCourse(int id) {
        return courseRepository.findById(id);
    }

    // get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // add course
    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    }

    // drop a specific course
    public Optional<Course> dropCourse(int id) {
        Optional<Course> course = getCourse(id);
        courseRepository.deleteById(id);
        return course;
    }

    // update a specific course
    public Optional<Course> updateSpecificCourse(int id, Course upToDateCourse) {
        Optional<Course> foundCourse = getCourse(id);
        foundCourse.ifPresent(
                (currCourse) -> {
                    currCourse.name = upToDateCourse.name;
                    courseRepository.save(currCourse);
                }
        );

        return foundCourse;

    }

}
