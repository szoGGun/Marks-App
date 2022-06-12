package org.przemekrutkowski.exammarksapp.repository;

import org.przemekrutkowski.exammarksapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByName(final String name);

}
