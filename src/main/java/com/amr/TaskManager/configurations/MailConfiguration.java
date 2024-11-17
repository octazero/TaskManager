package com.amr.TaskManager.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "mail")
public class MailConfiguration {
    private String hostName;
    private int port;
    private String from;
}
