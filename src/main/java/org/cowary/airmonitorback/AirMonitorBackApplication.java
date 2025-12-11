package org.cowary.airmonitorback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirMonitorBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirMonitorBackApplication.class, args);
    }

}
