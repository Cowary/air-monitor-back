package org.cowary.airmonitorback.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String ipAddress;
    private int port;
}
