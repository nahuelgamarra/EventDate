package com.eventdate.catalog.service.impl;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.EventCreationException;
import com.eventdate.catalog.exception.EventNotFoundException;
import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import com.eventdate.catalog.model.record.EventRequest;
import com.eventdate.catalog.repository.EventRepository;
import com.eventdate.catalog.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

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
    public Flux<Event> getEventsByDate(LocalDate date) {

        return eventRepository.findByDate(date)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<Event> getEventsByLocation(String location) {
        return eventRepository.findByLocation(location)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<Event> getEventsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return eventRepository.findByPriceRange(minPrice, maxPrice)
                .switchIfEmpty(Flux.empty());
    }


    @Override
    public Mono<Void> createEvent(@Valid EventRequest event) {
        generateEvent(event);
            return eventRepository.save(generateEvent(event))
                    .doOnSuccess(e -> log.info("Created event"+ e))
                    .doOnError(e -> {
                        log.error("Error creating event: " + e);
                        throw new EventCreationException("Error creating event");
                    })
                    .then();

    }

    private Event generateEvent(EventRequest eventRequest) {
        return Event.builder()
                .name(eventRequest.name())
                .eventDate(eventRequest.eventDate())
                .startTime(eventRequest.startTime())
                .endTime(eventRequest.endTime())
                .description(eventRequest.description())
                .location(eventRequest.location())
                .price(eventRequest.price())
                .category(eventRequest.category())
                .organizer(eventRequest.organizer())
                .capacity(eventRequest.capacity())
                .ticketAvailability(eventRequest.ticketAvailability())
                .address(eventRequest.address())
                .newEvent(true)
                .active(true)
                .build();
    }

    @Override
    public Mono<Void> cancellationEvent(Long eventId) {

        return getEventById(eventId)
                .flatMap(event -> {
                    event.setActive(false);
                    return eventRepository.save(event);
                })
               .doOnSuccess(e -> log.info("Cancelled event: " + e)).then();

    }

    @Override
    public Mono<Event> getEventById(Long id) {
        return eventRepository.findById(id)
                .switchIfEmpty(Mono.error(new EventNotFoundException("Event not found for id: " + id)));

    }
}
