package ru.miroque.pstorage.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID nid;
    private LocalDate ld;
    private LocalDateTime ldt;
    private OffsetTime ot;
    private OffsetDateTime odt;
    private ZonedDateTime zdt;
    private ZonedDateTime zdt2;
}
