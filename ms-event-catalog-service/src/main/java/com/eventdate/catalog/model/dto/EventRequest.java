package com.eventdate.catalog.model.record;

import com.eventdate.catalog.model.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EventRequest(
        @NotNull(message = "Name is required.")
        @Size(max = 50, message = "Name must not exceed 50 characters.")
        String name,

        @NotEmpty(message = "Description is required.")
        @Size(max = 500, message = "Description must not exceed 500 characters.")
        String description,

        @NotEmpty(message = "Location is required.")
        @Size(max = 100, message = "Location must not exceed 100 characters.")
        String location,

        @NotNull(message = "Date is required.")
        LocalDate eventDate,

        @NotNull(message = "Start time is required.")
        LocalTime startTime,

        @NotNull(message = "End time is required.")
        LocalTime endTime,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
        BigDecimal price,

        @NotNull(message = "Category is required.")
        Category category,

        @NotEmpty(message = "Organizer is required.")
        @Size(max = 100, message = "Organizer must not exceed 100 characters.")
        String organizer,

        @Min(value = 1, message = "Capacity must be at least 1.")
        Integer capacity,

        @NotNull(message = "Ticket availability is required.")
        Boolean ticketAvailability,

        @NotEmpty(message = "Address is required.")
        @Size(max = 255, message = "Address must not exceed 255 characters.")
        String address
) {
}
