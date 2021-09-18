package com.shine.approval.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties for whole project.
 * <p>
 * Properties are configured in the application.yml file.
 *
 * @author xueancao
 */
@Data
@Component
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {

}
