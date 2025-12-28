package org.cowary.airmonitorback.db.repository;

import org.cowary.airmonitorback.db.entity.CommandExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandExecutionRepository extends JpaRepository<CommandExecution, Long> {
}
