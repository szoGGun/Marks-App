package org.przemekrutkowski.exammarksapp.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.model.Mark;
import org.przemekrutkowski.exammarksapp.service.MarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkEndpoint {

    private final MarkService markService;

    @GetMapping("/findMarks")
    public ResponseEntity<Page<Mark>> findMarks(@RequestParam final String markDescription,
                                                @RequestParam final String student,
                                                @RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                                @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize) {

        var marks = markService.findMarks(markDescription, student, PageRequest.of(pageNumber - 1, pageSize, Sort.by("id")));
        return ResponseEntity.ok(marks);
    }
}
