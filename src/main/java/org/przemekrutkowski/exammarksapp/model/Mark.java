package org.przemekrutkowski.exammarksapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    private LocalDate markDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;


    @NotBlank(message = "Field Student Name must not be empty")
    @Column(name = "mark_description")
    private String markDescription;

    @Max(5)
    @Column(name = "mark")
    private Integer markValue;

    public Mark(Student student, String markDescription, Integer markValue) {
        this.markDate = LocalDate.now();
        this.student = student;
        this.markDescription = markDescription;
        this.markValue = markValue;
    }
}
