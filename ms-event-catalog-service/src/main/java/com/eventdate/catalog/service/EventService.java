package com.eventdate.catalog.service;

import com.eventdate.catalog.model.entity.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
    Flux<Event> getEvents();
    Flux<Event> getEventsByType(String category);
    Flux<Event> getEventsByDate(String date);
    Flux<Event> getEventsByLocation(String location);
    Flux<Event> getEventsByPriceRange(double minPrice, double maxPrice);
    Flux<Event> getEventsByOrganizer(String organizer);
    Mono<Event> getEventsBy(Long eventId);
    Mono<Event> createEvent(Event event);
    Mono<Event> updateEvent(Long eventId, Event updatedEvent);
    Mono<Void> cancellationEvent(Long eventId);


}
