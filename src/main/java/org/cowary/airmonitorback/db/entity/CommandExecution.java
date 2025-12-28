package org.cowary.airmonitorback.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "command_execution")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommandExecution extends BaseEntity {
    @Column(nullable = false)
    String status;
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    Agent agent;
    @ManyToOne
    @JoinColumn(name = "command_id", nullable = false)
    Command command;
    String output;
    String aiAnalyze;
}
