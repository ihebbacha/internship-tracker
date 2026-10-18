package com.iheb.tracker.dto;

import com.iheb.tracker.model.Application;
import com.iheb.tracker.model.Status;

import java.time.LocalDate;

public record ApplicationResponse(
        Long id,
        String company,
        String position,
        String city,
        String offerUrl,
        Status status,
        LocalDate appliedDate,
        LocalDate followUpDate,
        String notes
) {

    public static ApplicationResponse from(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getPosition(),
                application.getCity(),
                application.getOfferUrl(),
                application.getStatus(),
                application.getAppliedDate(),
                application.getFollowUpDate(),
                application.getNotes()
        );
    }
}