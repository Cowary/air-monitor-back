package org.cowary.airmonitorback.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "health_history")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HealthHistory extends BaseEntity {
    @Column(nullable = false)
    boolean isHealthy;
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    Agent agent;
}
