package com.przemyslawrutkowski.sms.repository;

import com.przemyslawrutkowski.sms.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
