package com.sbp_it_support.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String priority;
    private String department;
    private String description;
    private String providerName;
    private String solutionStatus;
    private String errorSolution;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ONGOING;

    private String assignee;

    @Column(nullable = true)
    private LocalDate date;

    private LocalDateTime createdDate = LocalDateTime.now();

    public enum Status {
        PENDING, ONGOING, DONE, GOING, OPEN, IN_PROGRESS, CLOSED
    }

    public Ticket() {}
    public Ticket(String name, String priority, String department, String description,
                  String providerName, String solutionStatus, String errorSolution) {
        this.name = name;
        this.priority = priority;
        this.department = department;
        this.description = description;
        this.providerName = providerName;
        this.solutionStatus = solutionStatus;
        this.errorSolution = errorSolution;
    }
}
