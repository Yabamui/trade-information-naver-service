package com.service.openapi.trade.naverapi.config.client;

import com.service.openapi.trade.naverapi.model.customproperty.NaverProperty;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class NaverApiConfig {
    private final NaverProperty naverProperty;

    @Bean
    public RequestInterceptor requestTemplate() {
        return requestTemplate -> requestTemplate
                .header("X-Naver-Client-Id", naverProperty.getClientId())
                .header("X-Naver-Client-Secret", naverProperty.getClientSecret());
    }
}
