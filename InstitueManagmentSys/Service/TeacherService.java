package com.thedynamicdoers.InstitueManagmentSys.Service;

import com.thedynamicdoers.InstitueManagmentSys.Model.Teacher;
import com.thedynamicdoers.InstitueManagmentSys.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Optional<Teacher> getSpecificTeacherInfo(int id) {
     return teacherRepository.findById(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher hireTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Optional<Teacher> fireTeacher(int id) {
        Optional<Teacher> teacher = getSpecificTeacherInfo(id);
        teacherRepository.deleteById(id);
        return teacher;
    }

    public Optional<Teacher> updateSpecificTeacherInfo(int id, Teacher upToDateTeacher) {
        Optional<Teacher> foundTeacher = getSpecificTeacherInfo(id);
        foundTeacher.ifPresent(
                (currTeacher) -> {
                    currTeacher.name = upToDateTeacher.name;
                    currTeacher.email = upToDateTeacher.email;
                    currTeacher.hiredAt = upToDateTeacher.hiredAt;
                    teacherRepository.save(currTeacher);
                }
        );

        return foundTeacher;

    }
}
