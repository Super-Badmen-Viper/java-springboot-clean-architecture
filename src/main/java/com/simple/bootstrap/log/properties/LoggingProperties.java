package com.simple.bootstrap.log.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@ConfigurationProperties("logging")
@Getter
@Setter
@Component
public class LoggingProperties {
    private List<String> includePaths;
}
