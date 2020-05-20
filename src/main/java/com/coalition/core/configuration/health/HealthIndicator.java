package com.coalition.core.configuration.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * This class can be modified to add your custom health checks that
 * are not accounted for by spring boot stater packs.
 */
@Component
public class HealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            builder.up()
                    .withDetail("service", "custom service check")
                    .withDetail("validation", " producer.publish(\"{\\\"health\\\":\\\"OK\\\"}\")");
        } catch (Exception e) {
            builder.down()
                    .withDetail("service", "custom service check")
                    .withDetail("validation", " producer.publish(\"{\\\"health\\\":\\\"OK\\\"}\")");
        }
    }
}