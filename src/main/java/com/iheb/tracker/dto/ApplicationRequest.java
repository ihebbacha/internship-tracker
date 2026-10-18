package com.iheb.tracker.dto;

import com.iheb.tracker.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ApplicationRequest(

        @NotBlank(message = "Company is required")
        String company,

        @NotBlank(message = "Position is required")
        String position,

        String city,

        @Size(max = 500, message = "Offer link is too long (max 500 characters)")
        String offerUrl,

        @NotNull(message = "Status is required")
        Status status,

        LocalDate appliedDate,

        LocalDate followUpDate,

        @Size(max = 2000, message = "Notes are too long (max 2000 characters)")
        String notes
) {
}