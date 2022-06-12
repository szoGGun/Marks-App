package org.przemekrutkowski.exammarksapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.model.Mark;
import org.przemekrutkowski.exammarksapp.model.dto.MarkDto;
import org.przemekrutkowski.exammarksapp.model.dto.MarkViewDto;
import org.przemekrutkowski.exammarksapp.service.StudentService;
import org.przemekrutkowski.exammarksapp.service.MarkService;
import org.przemekrutkowski.exammarksapp.util.AttributeNames;
import org.przemekrutkowski.exammarksapp.util.Mappings;
import org.przemekrutkowski.exammarksapp.util.ViewNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;
    private final StudentService studentService;


    @GetMapping(Mappings.ADD_MARK)
    public String addMarkView(final Model model) {
        model.addAttribute(AttributeNames.MARK, new MarkViewDto());
        model.addAttribute(AttributeNames.STUDENT_LIST, studentService.findAll());
        return ViewNames.ADD_MARK;
    }

    @GetMapping(Mappings.EDIT_MARK)
    public String editMarkView(@RequestParam final Integer id, Model model) {
        var mark = markService.getMark(id);
        var markReader = new MarkViewDto(mark);
        model.addAttribute(AttributeNames.MARK, markReader);
        model.addAttribute(AttributeNames.STUDENT_LIST, studentService.findAll());
        return ViewNames.ADD_MARK;
    }

    @GetMapping(Mappings.MARKS_LIST)
    public String markListView(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                               @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                               final Model model) {
        Page<Mark> markPage = markService.getPaginated(PageRequest.of(pageNumber - 1, pageSize));
        model.addAttribute(AttributeNames.MARK_PAGE, markPage);
        Stream.of(markPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .forEach(pageNumbers -> model.addAttribute(AttributeNames.PAGE_NUMBERS, pageNumbers));
        return ViewNames.MARK_LIST;
    }

    @PostMapping(Mappings.ADD_MARK)
    public String addMark(
            @ModelAttribute(AttributeNames.MARK) @Valid final MarkDto submittedMark,
            final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            markService.addEditMark(submittedMark);
            return "redirect:/" + Mappings.MARKS_LIST;
        }
        model.addAttribute(Map.of(
                AttributeNames.MARK, submittedMark,
                AttributeNames.STUDENT_LIST, studentService.findAll()));
        return ViewNames.ADD_MARK;
    }

    @GetMapping(Mappings.DELETE_MARK)
    public String deleteMark(@RequestParam final Integer id) {
        markService.deleteMark(id);
        return "redirect:/" + Mappings.MARKS_LIST;
    }

    @GetMapping(Mappings.VIEW_MARK)
    public String markView(@RequestParam final Integer id, final Model model) {
        model.addAttribute(AttributeNames.MARK, markService.getMark(id));
        return ViewNames.VIEW_MARK;
    }
}
