package com.thedynamicdoers.InstitueManagmentSys.Controller;

import com.thedynamicdoers.InstitueManagmentSys.Model.Student;
import com.thedynamicdoers.InstitueManagmentSys.Service.StudentService;
import jakarta.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/**
 * The StudentController class is responsible for handling HTTP requests and responses for Student resource.
 * This class provides various HTTP methods such as GET, POST, PUT and DELETE to interact with Student resource.
 * It uses StudentService to perform CRUD operations on Student resource.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/api/Students")
public class StudentController {
    /**
     * The @Autowired annotation is used to inject the StudentService dependency into the StudentController class.
     * This allows us to use the methods provided by the StudentService class to interact with the Student resource.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Returns a list of all students.
     *
     * @return List of students.
     */
    @GetMapping
    public List<Student> getStudents() {
        return studentService.getListOfStudents();
    }

    /**
     * Returns a specific student.
     *
     * @param id The id of the student to retrieve.
     * @return The student with the given id.
     */
    @GetMapping(path = "/{id}")
    public Optional<Student> getStudent(@PathVariable int id) {
        Optional<Student> foundedStudent = studentService.getStudent(id);
        return foundedStudent;
    }
    @GetMapping(path = "/{id}/getImage")
    public ResponseEntity<byte[]> getStudentWithImage(@PathVariable int id) throws IOException {
        Optional<Student> optionalStudent = studentService.getStudent(id);
        if (optionalStudent.isPresent()){
            Student currStudent = optionalStudent.get();
            String filename= String.format("%d_%s.jpg", currStudent.id, currStudent.name);
            File imageFile = new File("./src/main/resources/static/student_images/"+ filename);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(Files.readAllBytes(imageFile.toPath()),httpHeaders, HttpStatus.OK);
        }
       else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with ID %d, not found !", id));
        }
    }

    /**
     * Creates a new student.
     *
     * @param newStudent The student object to create.
     * @return The created student object.
     */
    @PostMapping
    public Student createStudent(@Valid @RequestBody Student newStudent) {
        studentService.createStudent(newStudent);
        return newStudent;
    }

    @PostMapping(path = "/withImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Student createStudentWithImage(@RequestParam String name,
                                          @RequestParam String email,
                                          @RequestParam(required = false) MultipartFile image) throws IOException {

        Student newStudent = new Student();
        newStudent.name = name;
        newStudent.email = email;

        // to create the image with unique name of the students ids, first save it to get the auto-generated id
        Student savedStudent = studentService.createStudent(newStudent);

        if (image != null) {
            savedStudent.imageName = Integer.toString(savedStudent.id) + "_" + savedStudent.name + ".jpg";
            FileUtils.writeByteArrayToFile(new File("./src/main/resources/static/student_images/" + savedStudent.imageName), image.getBytes());
            studentService.updateStudent(savedStudent.id, savedStudent);
        }
        return savedStudent;
    }

    /**
     * Updates an existing student.
     *
     * @param id             The id of the student to update.
     * @param currentStudent The updated student object.
     * @return The updated student object.
     */
    @PutMapping(path = "/{id}")
    public Student updateStudent(@PathVariable(name = "id") int id, @RequestBody Student currentStudent) {
        studentService.updateStudent(id, currentStudent);
        return currentStudent;
    }

    /**
     * Deletes a student.
     *
     * @param id The id of the student to delete.
     * @return The deleted student object.
     */
    @DeleteMapping(path = "/{id}")
    public Optional<Student> removeStudent(@PathVariable int id) {
        Optional<Student> removedStudent = studentService.deleteStudent(id);
        return removedStudent;
    }
}
