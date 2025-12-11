package org.cowary.airmonitorback.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.cowary.airmonitorback.db.repository.AgentRepository;
import org.cowary.airmonitorback.dto.RegisterDto;
import org.cowary.airmonitorback.service.db.AgentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentController {
    private final AgentRepository agentRepository;
    final AgentService agentService;

    @GetMapping("/list")
    public String list() {
        return agentRepository.findAll().toString();
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto request) {
        var result = agentService.registerAgent(request.getName(), request.getIpAddress(), request.getPort());
        if (result) {
            return "Register successful";
        } else {
            return "Register failed";
        }
    }
}
