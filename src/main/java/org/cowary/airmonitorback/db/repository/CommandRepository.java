package org.cowary.airmonitorback.db.repository;

import org.cowary.airmonitorback.db.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    Optional<Command> findByName(String name);
    boolean existsByName(String name);
}
