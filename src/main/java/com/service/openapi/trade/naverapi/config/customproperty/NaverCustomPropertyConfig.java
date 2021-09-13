package com.service.openapi.trade.naverapi.config.customproperty;

import com.service.openapi.trade.naverapi.model.customproperty.NaverProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverCustomPropertyConfig {
    @Bean
    @ConfigurationProperties(prefix = "naver")
    public NaverProperty naverProperty() {
        return NaverProperty.builder().build();
    }
}
