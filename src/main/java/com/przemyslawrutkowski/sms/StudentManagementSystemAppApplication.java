package com.przemyslawrutkowski.sms;

import com.przemyslawrutkowski.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentManagementSystemAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemAppApplication.class, args);
    }

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public void run(String... args) throws Exception {

//        Student student1 = new Student("Przemysław", "Rutkowski", "przrut4@gmail.com" );
//        studentRepository.save(student1);
//
//        Student student2 = new Student("Joe", "Doe", "joedoe@gmail.com" );
//        studentRepository.save(student2);
//
//
//        Student student3 = new Student("Tony", "Stark", "ironaman@avengers.com" );
//        studentRepository.save(student3);
    }
}
