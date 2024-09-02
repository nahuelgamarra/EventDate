package com.eventdate.catalog.service.impl;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.EventCreationException;
import com.eventdate.catalog.exception.EventNotFoundException;
import com.eventdate.catalog.model.dto.EventRequest;
import com.eventdate.catalog.model.dto.EventResponse;
import com.eventdate.catalog.model.dto.ReservationPending;
import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import com.eventdate.catalog.repository.EventRepository;
import com.eventdate.catalog.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Flux<EventResponse> getEvents() {
        return eventRepository.findAll()
                .map(event -> modelMapper.map(event, EventResponse.class));
    }

    @Override
    public Flux<EventResponse> getEventsByCategory(String category) {
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
                            .map(event -> modelMapper.map(event, EventResponse.class))
                            .switchIfEmpty(Flux.empty());

                });
    }

    @Override
    public Flux<EventResponse> getEventsByDate(LocalDate date) {

        return eventRepository.findByDate(date)
                .map(event -> modelMapper.map(event, EventResponse.class))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<EventResponse> getEventsByLocation(String location) {
        return eventRepository.findByLocation(location)
                .map(event -> modelMapper.map(event, EventResponse.class))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<Event> getEventsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return eventRepository.findByPriceRange(minPrice, maxPrice)
                .switchIfEmpty(Flux.empty());
    }


    @Override
    public Mono<Void> createEvent(@Valid EventRequest event) {
        return eventRepository.save(generateEvent(event))
                .doOnSuccess(e -> log.info("Created event: " + e))
                .onErrorMap(e -> {
                    log.error("Error creating event: ", e);
                    return new EventCreationException("Error creating event");
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

        return eventRepository.findById(eventId)
                .flatMap(event -> {
                    event.setActive(false);
                    return eventRepository.save(event);
                })
                .doOnSuccess(e -> log.info("Cancelled event: " + e)).then();

    }

    @Override
    public Mono<EventResponse> getEventById(Long id) {
        return eventRepository.findById(id)
                .map(event -> modelMapper.map(event, EventResponse.class))
                .switchIfEmpty(Mono.error(new EventNotFoundException("Event not found for id: " + id)));
    }


    @KafkaListener(topics = "event-reservation", groupId = "myGroup1")
    @Override
    public Mono<Void> buyTickets(ReservationPending reservationPending) {
        return eventRepository.findById(reservationPending.eventId())
                .flatMap(event -> {
                    if (event.getTicketsSold() + reservationPending.numberOfTickets() <= event.getCapacity()) {
                        event.setTicketsSold(event.getTicketsSold() + reservationPending.numberOfTickets());
                        return eventRepository.save(event)
                                .doOnSuccess(e -> kafkaTemplate.send("reservation-confirmed", reservationPending.reservationId()))
                                .then();
                    } else {
                        kafkaTemplate.send("reservation-rejected", reservationPending.reservationId());
                        return Mono.error(new RuntimeException("Not enough tickets available for event ID: " + reservationPending.eventId()));
                    }
                })
                .doOnError(e -> log.error("Error buying tickets: ", e))
                .then();
    }
}
