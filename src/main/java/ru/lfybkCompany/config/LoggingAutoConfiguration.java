package ru.lfybkCompany.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lfybkCompany.aop.PointcutAspect;
import ru.lfybkCompany.aop.LoggingAspect;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.service.logging", name = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {

    @Bean
    @Order(1)
    public PointcutAspect pointcutAspect() {
        return new PointcutAspect();
    }

    @Bean
    @Order(2)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @PostConstruct
    public void init() {
        log.info("LoggingAutoConfiguration is initialised");
    }
}
