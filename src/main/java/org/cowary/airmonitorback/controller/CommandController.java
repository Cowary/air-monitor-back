package org.cowary.airmonitorback.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.cowary.airmonitorback.dto.CommandRq;
import org.cowary.airmonitorback.service.ai.AIService;
import org.cowary.airmonitorback.service.command.CommandService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandController {
    CommandService commandService;
    AIService aiService;

    @PostMapping("/shell")
    public String sendCommand(@RequestBody CommandRq command) {
        var response = commandService.executeShellCommand(command.getAgentName(), command.getCommand());
        var aiResponse = aiService.askAI(String.format("Команда: %s, Ответ команды: %s", command, response), "Проанализируй лог после выполнения команды и проведи анализ успешно ли выполнена команда. Ответ кратко");
        return aiResponse;
    }

    @PostMapping("/shell-ai")
    public String sendCommandWithAiAnalyze(@RequestBody CommandRq command) {
        var response = commandService.executeShellCommand(command.getAgentName(), command.getCommand());
        var aiResponse = aiService.askAI(String.format("Команда: %s, Ответ команды: %s", command, response), "Проанализируй лог после выполнения команды и проведи анализ успешно ли выполнена команда. Ответ кратко");
        return aiResponse;
    }
}
