package ru.lfybkCompany.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.service.logging")
public class LoggingProperties {

    private boolean enabled;

    @PostConstruct
    public void init() {
        log.info("Logging properties initialized {}", this);
    }
}
