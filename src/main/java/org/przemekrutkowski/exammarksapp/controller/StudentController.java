package org.przemekrutkowski.exammarksapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.model.Student;
import org.przemekrutkowski.exammarksapp.service.StudentService;
import org.przemekrutkowski.exammarksapp.util.AttributeNames;
import org.przemekrutkowski.exammarksapp.util.Mappings;
import org.przemekrutkowski.exammarksapp.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Controller
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping(Mappings.STUDENTS_LIST)
    public String userListView(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                               @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                               final Model model) {
        Page<Student> usersPage = studentService.getPaginated(PageRequest.of(pageNumber - 1, pageSize));
        model.addAttribute(AttributeNames.STUDENT_PAGE, usersPage);
        Stream.of(usersPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .forEach(pageNumbers -> model.addAttribute(AttributeNames.PAGE_NUMBERS, pageNumbers));
        return ViewNames.STUDENTS_LIST;
    }

    @GetMapping(Mappings.VIEW_STUDENT)
    public String viewStudent(@RequestParam Integer id, Model model) {
        model.addAttribute(AttributeNames.STUDENT, studentService.getStudent(id));
        return ViewNames.VIEW_STUDENT;
    }

    @GetMapping(Mappings.ADD_STUDENT)
    public String addStudent(Model model) {
        model.addAttribute(AttributeNames.STUDENT, new Student());
        return ViewNames.ADD_STUDENT;
    }

    @GetMapping(Mappings.EDIT_STUDENT)
    public String addStudent(@RequestParam Integer id, Model model) {
        model.addAttribute(AttributeNames.STUDENT, studentService.getStudent(id));
        return ViewNames.ADD_STUDENT;
    }

    @Transactional
    @PostMapping(Mappings.ADD_STUDENT)
    public String processAddOrEditStudent(
            @ModelAttribute(AttributeNames.STUDENT) @Valid Student student,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ViewNames.ADD_STUDENT;
        }
        studentService.addEditStudent(student);
        return "redirect:/" + Mappings.STUDENTS_LIST;
    }

    @GetMapping(Mappings.DELETE_STUDENT)
    public String deleteStudent(@RequestParam Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/" + Mappings.STUDENTS_LIST;
    }
}
