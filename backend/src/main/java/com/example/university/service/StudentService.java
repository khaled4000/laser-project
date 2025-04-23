package com.example.university.service;

import com.example.university.model.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final File file = new File("src/main/resources/students.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Student> getAllStudents() {
        try {
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Failed to read students from file", e);
            return new ArrayList<>();
        }
    }

    public void addStudent(Student student) {
        List<Student> students = getAllStudents();
        students.add(student);
        saveToFile(students);
        logger.info("Student added: {}", student.getName());
    }

    public void saveAllStudents(List<Student> students) {
        saveToFile(students);
        logger.info("All students saved to file.");
    }

    public void deleteStudent(int index) {
        List<Student> students = getAllStudents();
        if (index >= 0 && index < students.size()) {
            Student removed = students.remove(index);
            saveToFile(students);
            logger.info("Deleted student: {}", removed.getName());
        } else {
            logger.warn("Invalid index for deletion: {}", index);
        }
    }

    public void updateStudent(int index, Student updatedStudent) {
        List<Student> students = getAllStudents();
        if (index >= 0 && index < students.size()) {
            students.set(index, updatedStudent);
            saveToFile(students);
            logger.info("Updated student at index {}: {}", index, updatedStudent.getName());
        } else {
            logger.warn("Invalid index for update: {}", index);
        }
    }

    private void saveToFile(List<Student> students) {
        try {
            mapper.writeValue(file, students);
        } catch (IOException e) {
            logger.error("Failed to save students to file", e);
        }
    }
}
