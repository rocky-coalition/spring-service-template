package com.coalition.core.configuration.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;

public class LogLayout extends JsonLayout {
    private static final UUID ID = UUID.randomUUID();
    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put("service", "spring-service-template");
        map.put("dtg", map.remove("timestamp"));
        map.put("caller", map.remove("logger"));
        map.put("pid", ProcessHandle.current().pid());
        map.put("id", ID);

        try {
            map.put("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            // nothing to do
        }

        MDC.clear();
    }
}
