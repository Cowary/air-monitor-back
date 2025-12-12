package org.cowary.airmonitorback.service.db;

import org.cowary.airmonitorback.db.entity.Agent;
import org.cowary.airmonitorback.db.repository.AgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
/**
 * Тесты для AgentService:
 * - findAll: возвращает список всех агентов
 * - findById: возвращает агента по ID, возвращает null если не найден или ID null
 * - save: сохраняет агента и возвращает его, возвращает null если агент null
 * - registerAgent: регистрирует агента, возвращает true; если агент с таким именем уже существует, не сохраняет нового агента; возвращает true если имя null или пустое
 */
class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private AgentService agentService;

    @Test
    void findAll_shouldReturnListOfAllAgents() {
        List<Agent> expectedAgents = List.of(
                Agent.builder().name("agent1").ipAddress("192.168.1.1").port(8080).build(),
                Agent.builder().name("agent2").ipAddress("192.168.1.2").port(8081).build()
        );
        when(agentRepository.findAll()).thenReturn(expectedAgents);

        List<Agent> actualAgents = agentService.findAll();

        assertThat(actualAgents).isEqualTo(expectedAgents);
        verify(agentRepository, times(1)).findAll();
    }

    @Test
    void findById_whenAgentExists_shouldReturnAgent() {
        Long agentId = 1L;
        Agent expectedAgent = Agent.builder().name("agent1").ipAddress("192.168.1.1").port(8080).build();
        when(agentRepository.findById(agentId)).thenReturn(Optional.of(expectedAgent));

        Agent actualAgent = agentService.findById(agentId);

        assertThat(actualAgent).isEqualTo(expectedAgent);
        verify(agentRepository, times(1)).findById(agentId);
    }

    @Test
    void findById_whenAgentDoesNotExist_shouldReturnNull() {
        Long agentId = 999L;
        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());

        Agent actualAgent = agentService.findById(agentId);

        assertThat(actualAgent).isNull();
        verify(agentRepository, times(1)).findById(agentId);
    }

    @Test
    void findById_withNullId_shouldReturnNull() {
        when(agentRepository.findById(null)).thenReturn(Optional.empty());

        Agent actualAgent = agentService.findById(null);

        assertThat(actualAgent).isNull();
        verify(agentRepository, times(1)).findById(null);
    }

    @Test
    void save_shouldSaveAgentAndReturnIt() {
        Agent agentToSave = Agent.builder().name("agent1").ipAddress("192.168.1.1").port(8080).build();
        Agent savedAgent = Agent.builder().name("agent1").ipAddress("192.168.1.1").port(8080).build();
        when(agentRepository.save(agentToSave)).thenReturn(savedAgent);

        Agent result = agentService.save(agentToSave);

        assertThat(result).isEqualTo(savedAgent);
        verify(agentRepository, times(1)).save(agentToSave);
    }

    @Test
    void save_withNullAgent_shouldReturnNull() {
        when(agentRepository.save(null)).thenReturn(null);

        Agent result = agentService.save(null);

        assertThat(result).isNull();
        verify(agentRepository, times(1)).save(null);
    }

    @Test
    void registerAgent_whenAgentWithSameNameExists_shouldReturnTrueWithoutSaving() {
        String name = "existingAgent";
        String ipAddress = "192.168.1.100";
        int port = 8080;
        when(agentRepository.existsByName(name)).thenReturn(true);

        boolean result = agentService.registerAgent(name, ipAddress, port);

        assertThat(result).isTrue();
        verify(agentRepository, times(1)).existsByName(name);
        verify(agentRepository, never()).save(any());
    }

    @Test
    void registerAgent_whenAgentWithSameNameDoesNotExist_shouldSaveNewAgentAndReturnTrue() {
        String name = "newAgent";
        String ipAddress = "192.168.1.200";
        int port = 8081;
        when(agentRepository.existsByName(name)).thenReturn(false);

        Agent agentToSave = Agent.builder()
                .name(name)
                .ipAddress(ipAddress)
                .port(port)
                .build();
        Agent savedAgent = Agent.builder()
                .name(name)
                .ipAddress(ipAddress)
                .port(port)
                .build();
        when(agentRepository.save(any(Agent.class))).thenReturn(savedAgent);

        boolean result = agentService.registerAgent(name, ipAddress, port);

        assertThat(result).isTrue();
        verify(agentRepository, times(1)).existsByName(name);
        verify(agentRepository, times(1)).save(any(Agent.class));
    }

    @Test
    void registerAgent_withNullName_shouldReturnTrueWithoutSaving() {
        when(agentRepository.existsByName(null)).thenReturn(true);

        boolean result = agentService.registerAgent(null, "192.168.1.1", 8080);

        assertThat(result).isTrue();
        verify(agentRepository, times(1)).existsByName(null);
        verify(agentRepository, never()).save(any());
    }

    @Test
    void registerAgent_withEmptyStringName_shouldReturnTrueWithoutSaving() {
        when(agentRepository.existsByName("")).thenReturn(true);

        boolean result = agentService.registerAgent("", "192.168.1.1", 8080);

        assertThat(result).isTrue();
        verify(agentRepository, times(1)).existsByName("");
        verify(agentRepository, never()).save(any());
    }
}