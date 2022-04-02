package com.przemyslawrutkowski.sms.service.impl;

import com.przemyslawrutkowski.sms.entity.Student;
import com.przemyslawrutkowski.sms.repository.StudentRepository;
import com.przemyslawrutkowski.sms.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}
