package com.tutorconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meditations")
public class Meditation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String subject;

    private String description;

    private Double price;

    private String tutorName;

    public Meditation() {
    }

    public Meditation(
            Long userId,
            String title,
            String subject,
            String description,
            Double price,
            String tutorName
    ) {

        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.price = price;
        this.tutorName = tutorName;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
}