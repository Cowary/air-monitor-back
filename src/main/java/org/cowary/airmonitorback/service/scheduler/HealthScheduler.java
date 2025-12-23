package org.cowary.airmonitorback.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cowary.airmonitorback.db.entity.HealthHistory;
import org.cowary.airmonitorback.dto.HealthRs;
import org.cowary.airmonitorback.service.db.AgentService;
import org.cowary.airmonitorback.service.db.HealthHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthScheduler {
    final AgentService agentService;
    final HealthHistoryService healthHistoryService;
    final WebClient webClient;

    @Scheduled(fixedRate = 300000)
    public void healthCheck() {
        log.info("Health check started");
        Flux.fromStream(agentService.findAll().stream())
                .flatMap(agent -> {
                    String url = "http://" + agent.getIpAddress() + ":" + agent.getPort() + "/health";
                    log.debug("Checking agent: {} at URL: {}", agent.getName(), url);

                    return webClient.get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(HealthRs.class)
                            .map(response -> {
                                boolean isHealthy = "SUCCESS".equals(response.getStatus());
                                log.debug("Agent {} is healthy: {}", agent.getName(), isHealthy);
                                return HealthHistory.builder()
                                        .agent(agent)
                                        .isHealthy(isHealthy)
                                        .build();
                            })
                            .onErrorReturn(HealthHistory.builder()
                                    .agent(agent)
                                    .isHealthy(false)
                                    .build())
                            .doOnSuccess(history -> log.debug("Health check result for {}: {}", agent.getName(), history.isHealthy()))
                            .onErrorResume(ex -> {
                                log.warn("Request failed for agent {}: {}", agent.getName(), ex.getMessage());
                                return Mono.just(HealthHistory.builder()
                                        .agent(agent)
                                        .isHealthy(false)
                                        .build());
                            });
                }, 10)
                .collectList()
                .flatMap(histories -> {
                    log.info("Health check completed. Total agents checked: {}", histories.size());
                    healthHistoryService.saveAll(histories);
                    return Mono.just(histories);
                })
                .doOnError(error -> log.error("Unexpected error during health check", error))
                .block();
        log.info("Helth check finished. Next check will be in {}", LocalDateTime.now().plusMinutes(300_000).truncatedTo(TimeUnit.SECONDS.toChronoUnit()));
    }
}
