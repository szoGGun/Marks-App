package org.przemekrutkowski.exammarksapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.exceptions.NotFoundException;
import org.przemekrutkowski.exammarksapp.model.Student;
import org.przemekrutkowski.exammarksapp.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    public static final String STUDENT_CONFLICT_ERROR_MESSAGE = "Student with given id=%s already exist.";
    public static final String STUDENT_NOT_FOUND_ERROR_MESSAGE = "Student with id=%s has not been found.";
    private final StudentRepository repository;


    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student getStudent(Integer id) {
            return repository.findById(id)
                    .orElse(null);
    }

    public void addEditStudent(final Student student) {
        if(student.getId() == null) {
            addStudent(student);
        } else {
            editStudent(student);
        }
    }

    public void addStudent(Student student) {
        repository.save(student);
    }

    public void editStudent(Student student) {
        if(!repository.existsById(student.getId())) {
            throw new NotFoundException(STUDENT_NOT_FOUND_ERROR_MESSAGE.formatted(student.getId()));
        }
        repository.save(student);
    }

    public void deleteStudent(Integer StudentId) {
        repository.deleteById(StudentId);
    }

    public Page<Student> getPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        var users = repository.findAll();
        var studentsPage = Stream.of(users)
                .filter(markList -> !markList.isEmpty())
                .flatMap(Collection::stream)
                .skip((long) currentPage * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return new PageImpl<Student>(studentsPage, PageRequest.of(currentPage, pageSize), users.size());
    }
}
