package org.lr0.server.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cyh
 * @since 2025/3/12
 */
@ConfigurationProperties(prefix = "project.file")
@Configuration
@Data
public class FileSavaParamProperties {

    private String saveAvatarPath;

}
