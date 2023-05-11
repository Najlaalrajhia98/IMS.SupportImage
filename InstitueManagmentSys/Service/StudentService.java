package com.thedynamicdoers.InstitueManagmentSys.Service;

import com.thedynamicdoers.InstitueManagmentSys.Model.Student;
import com.thedynamicdoers.InstitueManagmentSys.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class StudentService {
    public List<Student> listOfStudents = new CopyOnWriteArrayList<>();
    @Autowired
    StudentRepository studentRepository;

    public List<Student> getListOfStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(int id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> deleteStudent(int id) {
        Optional<Student> studentToBeDeleted = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return studentToBeDeleted;
    }

    public Student createStudent(Student newStudent) {
        return studentRepository.save(newStudent);

    }

    public Optional<Student> updateStudent(int id, Student upToDateStudent) {
        Optional<Student> foundedStudent = getStudent(id);
        foundedStudent.ifPresent(
                (currentStudent) -> {
                    currentStudent.name = upToDateStudent.name;
                    currentStudent.email = upToDateStudent.email;
                    studentRepository.save(currentStudent);
                }
        );
        return foundedStudent;

    }
}
