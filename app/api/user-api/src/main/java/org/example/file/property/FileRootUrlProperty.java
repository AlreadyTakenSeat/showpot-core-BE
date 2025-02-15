package org.example.file.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public record FileRootUrlProperty(
    String rootUrl
) {

}
