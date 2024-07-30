package com.eventdate.catalog.model.entity;

import com.eventdate.catalog.model.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "events")
public class Event implements Persistable<Long> {
    @Id
    private Long id;

    @NotNull(message = "Name is required.")
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String name;

    @NotEmpty(message = "Description is required.")
    @Size(max = 500, message = "Description must not exceed 500 characters.")
    private String description;

    @NotEmpty(message = "Location is required.")
    @Size(max = 100, message = "Location must not exceed 100 characters.")
    private String location;

    @NotNull(message = "Date is required.")
    private LocalDate eventDate; // Separated date

    @NotNull(message = "Start time is required.")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    private LocalDateTime endTime;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Category is required.")
    private Category category;

    @NotEmpty(message = "Organizer is required.")
    @Size(max = 100, message = "Organizer must not exceed 100 characters.")
    private String organizer;

    @Min(value = 1, message = "Capacity must be at least 1.")
    private Integer capacity;

    @NotNull(message = "Ticket availability is required.")
    private Boolean ticketAvailability;

    @NotEmpty(message = "Address is required.")
    @Size(max = 255, message = "Address must not exceed 255 characters.")
    private String address;

    @Transient
    private boolean newEvent;
    private boolean active;

    @Override
    public boolean isNew() {
        return newEvent;
    }
}
