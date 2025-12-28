package org.cowary.airmonitorback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRs<T> {
    T data;
    String errorMessage;
    ErrorType error;
}
