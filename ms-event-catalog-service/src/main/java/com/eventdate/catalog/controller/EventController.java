package com.eventdate.catalog.controller;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.record.EventRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
public class EventController {
    public final EventService eventService;
    private static final Pattern LOCATION_PATTERN = Pattern.compile("^[\\w\\s]+$");

    @GetMapping("/events")
    public ResponseEntity<Flux<Event>> getAllEvents() {
        log.info("Get all events");
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
    }

    @GetMapping("/events/category/{category}")
    public ResponseEntity<Flux<Event>> getEventsByCategory(@PathVariable("category") String category) {
        log.info("Get events by type: {}", category);
        return new ResponseEntity<>(eventService.getEventsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/event")
    public ResponseEntity<Mono<Event>> getEventById(@RequestParam("id") Long id) {
        log.info("Get event by id: {}", id);
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }

    @GetMapping("/events/date")
    public ResponseEntity<Flux<Event>> getEventByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("Get events by date: {}", date);
        return new ResponseEntity<>(eventService.getEventsByDate(date), HttpStatus.OK);
    }

    @GetMapping("/events/location")
    public ResponseEntity<Flux<Event>> getEventByLocation(@RequestParam("location") String location) {
        log.info("Get events by location: {}", location);
        if (!LOCATION_PATTERN.matcher(location).matches()) {
            return ResponseEntity.badRequest().body(Flux.empty());
        }
        return new ResponseEntity<>(eventService.getEventsByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/events/price-range")
    public ResponseEntity<Flux<Event>> getEventsByPriceRange(@Valid @RequestParam("minPrice") @Min(0) BigDecimal minPrice,
                                                             @Valid @RequestParam("maxPrice") @Min(0) BigDecimal maxPrice) {
        log.info("Get events by price range: {} - {}", minPrice, maxPrice);
        return new ResponseEntity<>(eventService.getEventsByPriceRange(minPrice, maxPrice), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<Mono<Void>> createEvent(@RequestBody @Valid EventRequest event) {
        log.info("Create event: {}", event);

        return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
    }

    @PatchMapping("/event/{id}/cancel")
    public ResponseEntity<Mono<Void>> cancelEvent(@PathVariable Long id) {
        log.info("Cancel event: {}", id);
        return new ResponseEntity<>(eventService.cancellationEvent(id), HttpStatus.NO_CONTENT);
    }


}
