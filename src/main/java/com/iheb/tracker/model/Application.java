package com.iheb.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company is required")
    @Column(nullable = false)
    private String company;
    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    private String city;

    @Size(max = 500, message = "Offer link is too long (max 500 characters)")
    @Column(length = 500)
    private String offerUrl;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private LocalDate appliedDate;

    private LocalDate followUpDate;

    @Size(max = 2000, message = "Notes are too long (max 2000 characters)")
    @Column(length = 2000)
    private String notes;

    public Application() {
    }

    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public String getCity() {
        return city;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}