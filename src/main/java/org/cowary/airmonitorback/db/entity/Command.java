package org.cowary.airmonitorback.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "command")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Command extends BaseEntity{
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String commandText;
    @ToString.Exclude
    @OneToMany(mappedBy = "command", fetch = FetchType.LAZY)
    List<CommandExecution> commandExecutions;
}
