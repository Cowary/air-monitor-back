package org.cowary.airmonitorback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCommandRs {
    Boolean isSuccess;
    String output;
}
