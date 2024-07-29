package com.eventdate.catalog.controller;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EventController {
    public final EventService eventService;

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
        return new ResponseEntity<>(eventService.getEventsById(id), HttpStatus.OK);
    }
    @GetMapping("/events/date")
    public ResponseEntity<Flux<Event>> getEventByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("Get events by date: {}", date);
        return new ResponseEntity<>(eventService.getEventsByDate(date),HttpStatus.OK);
    }

}
