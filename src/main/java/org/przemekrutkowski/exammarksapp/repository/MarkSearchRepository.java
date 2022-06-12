package org.przemekrutkowski.exammarksapp.repository;

import org.przemekrutkowski.exammarksapp.model.Mark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarkSearchRepository extends PagingAndSortingRepository<Mark, Integer> {

    Page<Mark> findMarksByMarkDescriptionContainsAndStudent_NameContainsIgnoreCase(final String markDescription, final String student, final Pageable pageable);
}
