package com.mycompany.myapp.config;

import com.mycompany.myapp.config.pojo.AuthRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Transaction Aggregation.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    AuthRegistry authRegistry;

    public AuthRegistry getAuthRegistry() {
        return authRegistry;
    }

    public void setAuthRegistry(AuthRegistry authRegistry) {
        this.authRegistry = authRegistry;
    }
}
