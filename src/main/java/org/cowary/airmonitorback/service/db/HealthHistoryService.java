package org.cowary.airmonitorback.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cowary.airmonitorback.db.entity.HealthHistory;
import org.cowary.airmonitorback.db.repository.HealthHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthHistoryService {
    final HealthHistoryRepository healthHistoryRepository;

    public void saveAll(List<HealthHistory> healths) {
        var result = healthHistoryRepository.saveAll(healths);
        log.info("Health history saved: {}", result);
    }
}
