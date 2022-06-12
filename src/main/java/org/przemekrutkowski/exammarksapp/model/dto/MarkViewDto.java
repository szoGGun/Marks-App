package org.przemekrutkowski.exammarksapp.model.dto;

import org.przemekrutkowski.exammarksapp.model.Mark;
import org.przemekrutkowski.exammarksapp.model.Student;

import java.time.LocalDate;

public class MarkViewDto {


    private Integer id;
    private LocalDate markDate;
    private Integer studentId;
    private Student student;
    private Integer markValue;
    private String markDescription;
    public MarkViewDto() {
    }

    public MarkViewDto(Mark mark) {
        if(mark != null) {
            this.id = mark.getId();
            this.markDate = mark.getMarkDate();
            this.studentId = mark.getStudent().getId();
            this.student = mark.getStudent();
            this.markValue = mark.getMarkValue();
            this.markDescription = mark.getMarkDescription();
        } else {
            throw new IllegalArgumentException("Mark object must not be null");
        }

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Integer getStudentId() {
        return studentId;
    }
    public Integer getMarkValue() {
        return markValue;
    }
    public String getMarkDescription() {
        return markDescription;
    }
    public LocalDate getMarkDate() {
        return markDate;
    }
    void setMarkDate(LocalDate markDate) {
        this.markDate = markDate;
    }
    void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    void setMarkValue(Integer markValue) {
        this.markValue = markValue;
    }
    void setMarkDescription(String markDescription) {
        this.markDescription = markDescription;
    }
}
