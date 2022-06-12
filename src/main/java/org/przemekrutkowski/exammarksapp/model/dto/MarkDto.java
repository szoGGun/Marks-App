package org.przemekrutkowski.exammarksapp.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Slf4j
@NoArgsConstructor
public class MarkDto {

    private Integer id;

    private LocalDate markDate;

    @NotNull(message = "Field student must not be empty")
    private Integer studentId;

    @NotBlank(message = "Field Mark Description must not be empty")
    private String markDescription;

    @Max(5)
    private Integer MarkValue;
}
