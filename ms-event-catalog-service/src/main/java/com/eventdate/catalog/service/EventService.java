package com.eventdate.catalog.service;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.dto.EventRequest;
import com.eventdate.catalog.model.dto.EventResponse;
import com.eventdate.catalog.model.dto.ReservationPending;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventService {
    Flux<EventResponse> getEvents();

    Flux<EventResponse> getEventsByCategory(String category);

    Mono<EventResponse> getEventById(Long id);

    Flux<EventResponse> getEventsByDate(LocalDate date);

    Flux<EventResponse> getEventsByLocation(String location);

    Flux<Event> getEventsByPriceRange(BigDecimal minPrice, @Valid @Min(0) BigDecimal maxPrice);

    Mono<Void> createEvent(@Valid EventRequest event);

    Mono<Void> cancellationEvent(Long eventId);

    Mono<Void> buyTickets(ReservationPending request);
}
