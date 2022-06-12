package org.przemekrutkowski.exammarksapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "Field Name must not be empty.")
    private String name;

    @NotBlank(message = "Field Surname must not be empty.")
    private String surname;

    @NotBlank(message = "Field Email must not be empty.")
    private String email;

    @NotBlank(message = "Field Major must not be empty.")
    private String major;

    @Max(5)
    private Integer studentYear;

    @Max(5)
    private Integer studentGroup;

    public String getFullName() {
        return name + " " + surname;
    }
}
