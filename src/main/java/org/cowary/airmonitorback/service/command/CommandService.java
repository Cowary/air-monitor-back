package org.cowary.airmonitorback.service.command;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.cowary.airmonitorback.db.entity.Command;
import org.cowary.airmonitorback.db.repository.CommandRepository;
import org.cowary.airmonitorback.dto.air_agent.AgentCommandRq;
import org.cowary.airmonitorback.dto.air_agent.AgentCommandRs;
import org.cowary.airmonitorback.service.db.AgentService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class CommandService {
    RestTemplate restTemplate;
    AgentService agentService;
    CommandRepository commandRepository;

    public String executeShellCommand(String agentName, String command) {
        var agent = agentService.findByName(agentName);
        var request = AgentCommandRq.builder()
                .command(command)
                .build();
        var response = restTemplate.postForEntity("http://" + agent.getIpAddress() + ":" + agent.getPort() + "/execute-shell", request, AgentCommandRs.class);
        log.debug("Execute shell command for agent: {}, response: {}", agent.getName(), response);
        return Optional.ofNullable(response.getBody()).map(AgentCommandRs::getOutput).orElseThrow();
    }

    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    public Command saveCommand(Command command) {
        return commandRepository.save(command);
    }
}
