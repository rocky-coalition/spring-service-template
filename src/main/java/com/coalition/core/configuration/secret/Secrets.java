package com.coalition.core.configuration.secret;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class Secrets {
    @Builder.Default
    private Map<String, String> secrets = new HashMap<>();
}
