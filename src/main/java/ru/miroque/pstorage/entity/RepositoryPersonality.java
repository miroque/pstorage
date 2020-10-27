package ru.miroque.pstorage.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryPersonality extends JpaRepository<Personality, Long> {
    Personality findByNid(UUID uuid);
}
