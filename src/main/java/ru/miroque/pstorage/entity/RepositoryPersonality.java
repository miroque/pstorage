package ru.miroque.pstorage.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryPersonality extends JpaRepository<Personality, Long> {
    Optional<Personality> findByNid(UUID uuid);
}
