package ru.miroque.pstorage.entity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
public class ControllerPersonality {
    private final RepositoryPersonality repositoryPersonality;

    public ControllerPersonality(RepositoryPersonality repositoryPersonality) {
        this.repositoryPersonality = repositoryPersonality;
    }

    @GetMapping("/api/p/")
    public void create() {
        Personality item = new Personality();
        item.setLdt(LocalDateTime.now());
        item.setNid(UUID.randomUUID());
        item.setOdt(OffsetDateTime.now());
        repositoryPersonality.save(item);
    }

    @GetMapping("/api/ps/")
    public List<Personality> list() {
        return repositoryPersonality.findAll();
    }
}
