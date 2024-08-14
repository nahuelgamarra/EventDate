package com.eventdate.msreservationservice.model.records;

public record ReservationPending(Long reservationId,
                                 Long eventId,
                                 int numberOfTickets
                                 ) {
}
