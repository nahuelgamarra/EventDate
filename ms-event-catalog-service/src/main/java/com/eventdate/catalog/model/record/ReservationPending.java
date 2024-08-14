package com.eventdate.catalog.model.record;

public record ReservationPending(Long reservationId,
                                 Long eventId,
                                 int numberOfTickets
                                 ) {
}
