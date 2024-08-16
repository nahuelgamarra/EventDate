package com.eventdate.msreservationservice.model.records;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EventInfo(
        Long id,
        String name,
        String location,
        LocalDate eventDate,
        LocalTime startTime,
        LocalTime endTime,
        BigDecimal price
) {
}
