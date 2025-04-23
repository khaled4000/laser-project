package com.example.university.controller;

import com.example.university.model.Student;
import com.example.university.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/getStudents")
    public List<Student> getStudents() {
        logger.info("GET /students called");
        List<Student> students = service.getAllStudents();
        logger.info("Returning {} students", students.size());
        return students;
    }

    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student) {
        logger.info("POST /students called with: name={}, university={}", student.getName(), student.getUniversity());
        service.addStudent(student);
        logger.info("Student added successfully.");
    }

    @PostMapping("/save-all")
    public void saveAllStudents(@RequestBody List<Student> students) {
        logger.info("POST /students/save-all called with {} students", students.size());
        service.saveAllStudents(students);
        logger.info("All students saved successfully.");
    }

    @DeleteMapping("/deleteStudent/{index}")
    public void deleteStudent(@PathVariable int index) {
        logger.info("DELETE /students/{} called", index);
        service.deleteStudent(index);
        logger.info("Student at index {} deleted successfully.", index);
    }

    @PutMapping("/updateStudent/{index}")
    public void updateStudent(@PathVariable int index, @RequestBody Student updatedStudent) {
        logger.info("PUT /students/{} called with: name={}, university={}", index, updatedStudent.getName(), updatedStudent.getUniversity());
        service.updateStudent(index, updatedStudent);
        logger.info("Student at index {} updated successfully.", index);
    }
}
