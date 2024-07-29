package com.eventdate.catalog.service.impl;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.EventNotFoundException;
import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import com.eventdate.catalog.repository.EventRepository;
import com.eventdate.catalog.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.Delayed;

@Service
@Slf4j
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Flux<Event> getEvents() {
        return eventRepository.findAll().delayElements(Duration.ofSeconds(2));
    }

    @Override
    public Flux<Event> getEventsByCategory(String category) {
        return Mono.just(category)
                .<Category>handle((cat, sink) -> {
                    try {
                        sink.next(Category.valueOf(cat.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        sink.error(new CategoryNotFoundException("Category not found: " + category));
                    }
                })
                .flatMapMany(cat -> {
                    log.info("Searching events for category: {}", cat);
                    return eventRepository.findByCategory(cat)
                            .switchIfEmpty(Flux.empty());

                });
    }

    @Override
    public Flux<Event> getEventsByDate(String date) {
        return null;
    }

    @Override
    public Flux<Event> getEventsByLocation(String location) {
        return null;
    }

    @Override
    public Flux<Event> getEventsByPriceRange(double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public Flux<Event> getEventsByOrganizer(String organizer) {

        return null;
    }

    @Override
    public Mono<Event> getEventsBy(Long eventId) {
        return null;
    }

    @Override
    public Mono<Event> createEvent(Event event) {
        return null;
    }

    @Override
    public Mono<Event> updateEvent(Long eventId, Event updatedEvent) {
        return null;
    }

    @Override
    public Mono<Void> cancellationEvent(Long eventId) {
        return null;
    }

    @Override
    public Mono<Event> getEventsById(Long id) {
        return eventRepository.findById(id)
                .switchIfEmpty(Mono.error(new EventNotFoundException("Event not found for id: " + id)));

    }
}
