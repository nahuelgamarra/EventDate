package com.eventdate.catalog.repository;

import com.eventdate.catalog.model.entity.Event;
import com.eventdate.catalog.model.enums.Category;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends ReactiveCrudRepository<Event, Long> {
    Flux<Event> findByCategory(Category category);

    @Query("SELECT * FROM events WHERE DATE(date) = :date Order By :date ASC")
    Flux<Event> findByDate(String date);

    @Query("SELECT * FROM events WHERE location LIKE CONCAT('%', :location, '%')")
    Flux<Event> findByLocation(@Param("location") String location);

    @Query("SELECT * FROM events WHERE price BETWEEN :minPrice AND :maxPrice")
    Flux<Event> findByPriceRange(double minPrice, double maxPrice);
}
