package com.tutorconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;

    private String meditationTitle;

    private String tutorName;

    private String subject;

    private String level;

    @Column(length = 1000)
    private String message;

    private String preferredDay;

    private String preferredTime;

    private String status;

    private String sessionDate;

    private String sessionTime;

    public Booking() {
    }

    public Booking(
            String studentName,
            String meditationTitle,
            String tutorName,
            String subject,
            String level,
            String message,
            String preferredDay,
            String preferredTime,
            String status,
            String sessionDate,
            String sessionTime
    ) {

        this.studentName = studentName;
        this.meditationTitle = meditationTitle;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.message = message;
        this.preferredDay = preferredDay;
        this.preferredTime = preferredTime;
        this.status = status;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMeditationTitle() {
        return meditationTitle;
    }

    public void setMeditationTitle(String meditationTitle) {
        this.meditationTitle = meditationTitle;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPreferredDay() {
        return preferredDay;
    }

    public void setPreferredDay(String preferredDay) {
        this.preferredDay = preferredDay;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }
}