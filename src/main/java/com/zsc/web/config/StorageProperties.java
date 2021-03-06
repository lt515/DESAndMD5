package com.zsc.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abouerp
 */
@Component
@ConfigurationProperties(prefix = "storage")
@Data
public class StorageProperties {
    private String location;
}
