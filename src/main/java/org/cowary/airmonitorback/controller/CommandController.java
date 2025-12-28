package org.cowary.airmonitorback.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.cowary.airmonitorback.converter.CommandConverter;
import org.cowary.airmonitorback.db.entity.Command;
import org.cowary.airmonitorback.dto.*;
import org.cowary.airmonitorback.service.ai.AIService;
import org.cowary.airmonitorback.service.command.CommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandController {
    CommandService commandService;
    AIService aiService;
    CommandConverter commandConverter;

    @PostMapping("/shell")
    public String sendCommand(@RequestBody ExecutionCommandRq command) {
        var response = commandService.executeShellCommand(command.getAgentName(), command.getCommand());
        var aiResponse = aiService.askAI(String.format("Команда: %s, Ответ команды: %s", command, response), "Проанализируй лог после выполнения команды и проведи анализ успешно ли выполнена команда. Ответ кратко");
        return aiResponse;
    }

    @PostMapping("/shell-ai")
    public String sendCommandWithAiAnalyze(@RequestBody ExecutionCommandRq command) {
        var response = commandService.executeShellCommand(command.getAgentName(), command.getCommand());
        var aiResponse = aiService.askAI(String.format("Команда: %s, Ответ команды: %s", command, response), "Проанализируй лог после выполнения команды и проведи анализ успешно ли выполнена команда. Ответ кратко");
        return aiResponse;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiRs<CommandListRs>> listCommands() {
        var commandList = commandService.getAllCommands();
        var result = ApiRs.<CommandListRs>builder()
                .data(CommandListRs.builder()
                        .commands(commandConverter.commandListToCommandRsList(commandList)).build())
                .build();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiRs<CommandRs>> addCommand(@RequestBody CommandRq commandRq) {
        Command commandEntity = commandConverter.commandRqToCommand(commandRq);
        var savedCommand = commandService.saveCommand(commandEntity);
        ApiRs<CommandRs> result = ApiRs.<CommandRs>builder()
                .data(commandConverter.commandToCommandRs(savedCommand))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
