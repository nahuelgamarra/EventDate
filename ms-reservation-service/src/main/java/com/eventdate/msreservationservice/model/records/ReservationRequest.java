package com.eventdate.msreservationservice.model.records;

public record ReservationRequest(
                                 Long eventId,
                                 int numberOfTickets) {
}
