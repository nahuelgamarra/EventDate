package com.eventdate.catalog.controller;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
}
