package com.eventdate.catalog.repository;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {
    Flux<Event> findByCategory(Category category);

    @Query("SELECT * FROM events WHERE event_date = :date ORDER BY start_time ASC")
    Flux<Event> findByDate(@Param("date") LocalDate date);

    @Query("SELECT * FROM events WHERE location LIKE CONCAT('%', :location, '%')")
    Flux<Event> findByLocation(@Param("location") String location);

    @Query("SELECT * FROM events WHERE price BETWEEN :minPrice AND :maxPrice")
    Flux<Event> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
