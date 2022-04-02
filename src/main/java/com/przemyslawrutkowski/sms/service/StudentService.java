package com.przemyslawrutkowski.sms.service;

import com.przemyslawrutkowski.sms.entity.Student;

import java.util.List;

public interface StudentService{
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudentById(Long id);
    Student updateStudent(Student student);
}
