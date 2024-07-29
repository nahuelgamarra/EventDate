package com.eventdate.catalog.repository;

import com.eventdate.catalog.model.entity.Event;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {
}
