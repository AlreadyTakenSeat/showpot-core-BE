package org.example.config;

import org.example.file.property.FileRootUrlProperty;
import org.example.property.AlarmServerProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(UserDomainConfig.class)
@EnableConfigurationProperties({AlarmServerProperty.class, FileRootUrlProperty.class})
@EnableCaching
@ComponentScan(basePackages = "org.example")
public class UserApiConfig {

}
