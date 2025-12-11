package org.cowary.airmonitorback.db.repository;

import org.cowary.airmonitorback.db.entity.HealthHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthHistoryRepository extends JpaRepository<HealthHistory, Long> {
}
