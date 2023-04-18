package com.thedynamicdoers.InstitueManagmentSys.Controller;

import com.thedynamicdoers.InstitueManagmentSys.Model.Course;
import com.thedynamicdoers.InstitueManagmentSys.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/{id}")
    public Optional<Course> getSpecificCourse(@PathVariable int id) {
        return courseService.getCourse(id);

    }
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.registerCourse(course);
    }
    @DeleteMapping(path = "/{id}")
    public Optional<Course> dropCourse(@PathVariable int id) {
        return courseService.dropCourse(id);
    }

    @PutMapping(path = "/{id}")
    public Optional<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        return courseService.updateSpecificCourse(id, course);
    }
}
