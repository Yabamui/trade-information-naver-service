package com.service.openapi.trade.naverapi.model.client;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoCategory {
    private final String name;
    private final List<String> param;
}
