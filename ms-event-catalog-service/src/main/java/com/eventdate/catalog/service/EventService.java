package com.eventdate.catalog.service;

import com.eventdate.catalog.model.entity.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface EventService {
    Flux<Event> getEvents();

    Flux<Event> getEventsByCategory(String category);

    Mono<Event> getEventsById(Long id);

    Flux<Event> getEventsByDate(LocalDate date);

    Flux<Event> getEventsByLocation(String location);

    Flux<Event> getEventsByPriceRange(double minPrice, double maxPrice);

    Mono<Event> createEvent(Event event);

    Mono<Event> updateEvent(Long eventId, Event updatedEvent);

    Mono<Void> cancellationEvent(Long eventId);
}
