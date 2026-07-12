package com.jobtracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String role;

    private String location;

    @Column(name = "job_url")
    private String jobUrl;

    @Lob
    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(name = "date_applied")
    private LocalDate dateApplied;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;

    //AI Match result which is cached after the scoring runs
    @Column(name = "match_score")
    private Integer matchScore;

    @Lob
    @Column(name = "match_summary", columnDefinition = "TEXT")
    private String matchSummary;

    @Lob
    @Column(name = "match_strengths", columnDefinition = "TEXT")
    private String matchStrengths;

    @Lob
    @Column(name = "match_gaps", columnDefinition = "TEXT")
    private String matchGaps;
}
