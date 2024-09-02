package com.eventdate.msreservationservice.model.records;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record TicketInfo(
        Long reservationId,
        String eventName,
        LocalDate eventDate,
        LocalTime eventStartTime,
        LocalTime eventEndTime,
        int numberOfTickets,
        BigDecimal amount

) {
}
