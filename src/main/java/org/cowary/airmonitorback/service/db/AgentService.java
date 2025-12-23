package org.cowary.airmonitorback.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cowary.airmonitorback.db.entity.Agent;
import org.cowary.airmonitorback.db.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }

    public Agent findById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }

    public Agent save(Agent agent) {
        return agentRepository.save(agent);
    }

    public boolean registerAgent(String name, String ipAddress, int port) {
        if (agentRepository.existsByName(name)) {
            return true;
        }
        Agent agent = Agent.builder()
                .name(name)
                .ipAddress(ipAddress)
                .port(port)
                .build();
        agent = save(agent);
        log.debug("New agent registered: {}", agent);
        return true;
    }

    public Agent findByName(String name) {
        return agentRepository.findByName(name).orElseThrow();
    }
}
