package com.service.openapi.trade.naverapi.model.customproperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NaverProperty {
    private String clientId;
    private String clientSecret;
}
