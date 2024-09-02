package com.eventdate.catalog.controller;

import com.eventdate.catalog.model.dto.EventRequest;
import com.eventdate.catalog.model.dto.EventResponse;
import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/v1/catalog")
@AllArgsConstructor
@Validated
public class EventController {
    public final EventService eventService;
    private static final Pattern LOCATION_PATTERN = Pattern.compile("^[\\w\\s]+$");

    @GetMapping("/events")
    public Flux<EventResponse> getAllEvents() {
        return eventService.getEvents()
                .doOnNext(event -> log.info("Event: {}", event));
    }

    @GetMapping("/events/category/{category}")
    public Flux<EventResponse> getEventsByCategory(@PathVariable("category") String category) {
        log.info("Get events by category: {}", category);
        return eventService.getEventsByCategory(category)
                .doOnNext(event -> log.info("Event: {}", event));
    }




    @GetMapping("/event")
    public Mono<EventResponse> getEventById(@RequestParam("id") Long id) {
        log.info("Get event by id: {}", id);
        return eventService.getEventById(id);
    }

    @GetMapping("/events/date")
    public Flux<EventResponse> getEventByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("Get events by date: {}", date);
        return eventService.getEventsByDate(date);
    }

    @GetMapping("/events/location")
    public Flux<EventResponse>getEventByLocation(@RequestParam("location") String location) {
        log.info("Get events by location: {}", location);
        return eventService.getEventsByLocation(location);
    }

    @GetMapping("/events/price-range")
    public ResponseEntity<Flux<Event>> getEventsByPriceRange(@Valid @RequestParam("minPrice") @Min(0) BigDecimal minPrice,
                                                             @Valid @RequestParam("maxPrice") @Min(0) BigDecimal maxPrice) {
        log.info("Get events by price range: {} - {}", minPrice, maxPrice);
        return new ResponseEntity<>(eventService.getEventsByPriceRange(minPrice, maxPrice), HttpStatus.OK);
    }


    @PostMapping("/event")
    public ResponseEntity<Mono<Void>> registerEvent(@RequestBody @Valid EventRequest event) {
        log.info("Create event: {}", event);

        return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
    }

}
