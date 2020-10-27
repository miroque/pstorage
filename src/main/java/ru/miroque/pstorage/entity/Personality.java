package ru.miroque.pstorage.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID nid;
    private LocalDate birthDate;

    public Personality(UUID nid, LocalDate birthDate) {
        this.nid = nid;
        this.birthDate = birthDate;
    }
}
