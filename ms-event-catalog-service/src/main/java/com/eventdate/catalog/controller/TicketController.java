package com.eventdate.catalog.controller;

import com.eventdate.catalog.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {
    private final EventService eventService;

    @PostMapping("/events/{eventId}/buy")
    public Mono<Boolean> buyTickets(@PathVariable Long eventId, @RequestParam int numberOfTickets) {
        log.info("Buy tickets for event: {}, number: {}", eventId, numberOfTickets);
        return eventService.buyTickets(eventId, numberOfTickets);

    }
}
