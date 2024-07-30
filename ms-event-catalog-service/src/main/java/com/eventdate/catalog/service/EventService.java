package com.eventdate.catalog.service;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.record.EventRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventService {
    Flux<Event> getEvents();

    Flux<Event> getEventsByCategory(String category);

    Mono<Event> getEventsById(Long id);

    Flux<Event> getEventsByDate(LocalDate date);

    Flux<Event> getEventsByLocation(String location);

    Flux<Event> getEventsByPriceRange(BigDecimal minPrice, @Valid @Min(0) BigDecimal maxPrice);

    Mono<Void> createEvent(@Valid EventRequest event);

    Mono<Event> updateEvent(Long eventId, Event updatedEvent);

    Mono<Void> cancellationEvent(Long eventId);
}
