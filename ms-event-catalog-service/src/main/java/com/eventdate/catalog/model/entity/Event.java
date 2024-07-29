package com.eventdate.catalog.model.entity;

import com.eventdate.catalog.model.enums.EventType;
import jdk.jfr.StackTrace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "events")
public class Event implements Persistable<Long> {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private Double price;
    private EventType type;
    private String organizer;
    @Transient
    private boolean newEvent;
    @Column(value = "event_active")
    private boolean active;

    @Override
    public boolean isNew() {
        return newEvent;
    }
}