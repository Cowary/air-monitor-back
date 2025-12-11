package org.cowary.airmonitorback.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "agent")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Agent extends BaseEntity {
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String ipAddress;
    @Column(nullable = false)
    int port;
    @ToString.Exclude
    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    List<HealthHistory> healthHistory;
}
