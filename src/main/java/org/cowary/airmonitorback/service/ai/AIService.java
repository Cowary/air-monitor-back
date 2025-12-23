package org.cowary.airmonitorback.service.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {
    final ChatModel chatModel;

    public String askAI(String userPrompt, String systemPrompt) {
        SystemMessage systemMessage = new SystemMessage(systemPrompt);
        UserMessage userMessage = new UserMessage(userPrompt);
        List<Message> messages = List.of(systemMessage, userMessage);

        Prompt prompt = new Prompt(messages);
        log.debug("Ask AI: {}", prompt);

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
