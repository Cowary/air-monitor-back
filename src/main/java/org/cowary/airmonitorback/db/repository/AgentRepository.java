package org.cowary.airmonitorback.db.repository;

import org.cowary.airmonitorback.db.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    boolean existsByName(String name);
}
