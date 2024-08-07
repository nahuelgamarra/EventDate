package com.eventdate.msreservationservice.model.records;

public record ReservationRequest(Long userId,
                                 Long eventId,
                                 int numberOfTickets) {
}
