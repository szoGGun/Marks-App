package org.przemekrutkowski.exammarksapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.model.Mark;
import org.przemekrutkowski.exammarksapp.model.dto.MarkDto;
import org.przemekrutkowski.exammarksapp.repository.MarkRepository;
import org.przemekrutkowski.exammarksapp.repository.MarkSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final MarkSearchRepository markSearchRepository;
    private final StudentService studentService;

    public Mark getMark(Integer id) {
        return markRepository.getById(id);
    }

    public Page<Mark> getPaginated(final Pageable pageable) {
        return markSearchRepository.findAll(pageable);
    }

    public Page<Mark> findMarks(final String markDescription, final String student, final Pageable pageable) {
        return markSearchRepository.findMarksByMarkDescriptionContainsAndStudent_NameContainsIgnoreCase(markDescription, student, pageable);
    }

    public void addEditMark(MarkDto markDto) {
        markRepository.save(createMarkOrGetMarkForUpdate(markDto));
    }

    public void deleteMark(Integer id) {
        markRepository.deleteById(id);
    }

    private Mark createMarkOrGetMarkForUpdate(final MarkDto markDto) {
        return markDto.getId() == null ?
                createMark(markDto) :
                findMarkAndPrepareForUpdate(markDto);
    }

    private Mark createMark(final MarkDto markDto) {
        return new Mark(studentService.getStudent(markDto.getStudentId()),
                markDto.getMarkDescription(),
                markDto.getMarkValue());
    }

    private Mark findMarkAndPrepareForUpdate(final MarkDto markDto) {
        Mark mark = getMark(markDto.getId());
        mark.setStudent(studentService.getStudent(markDto.getStudentId()));
        mark.setMarkDescription(markDto.getMarkDescription());
        mark.setMarkValue(markDto.getMarkValue());
        return mark;
    }

}
