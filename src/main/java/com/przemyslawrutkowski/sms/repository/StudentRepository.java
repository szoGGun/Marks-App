package com.przemyslawrutkowski.sms.repository;

import com.przemyslawrutkowski.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
