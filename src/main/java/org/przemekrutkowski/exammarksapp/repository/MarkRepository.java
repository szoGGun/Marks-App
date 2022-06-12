package org.przemekrutkowski.exammarksapp.repository;

import org.przemekrutkowski.exammarksapp.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {
}
