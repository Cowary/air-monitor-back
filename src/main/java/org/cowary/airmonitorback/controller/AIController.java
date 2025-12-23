package org.cowary.airmonitorback.controller;

import lombok.extern.slf4j.Slf4j;
import org.cowary.airmonitorback.service.ai.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AIController {

    final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/ask")
    public String getAnswer(@RequestParam String question) {
        String prompt = "Ответь кратко на вопрос: " + question;
        return aiService.askAI(prompt, "Ты эксперт во всем");
    }
}
