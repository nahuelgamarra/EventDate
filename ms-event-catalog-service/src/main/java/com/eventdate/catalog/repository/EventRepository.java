package com.eventdate.catalog.repository;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {


    Flux<Event> findByCategory(Category category);
}
