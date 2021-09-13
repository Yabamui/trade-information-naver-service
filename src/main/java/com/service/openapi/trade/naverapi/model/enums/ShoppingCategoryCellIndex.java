package com.service.openapi.trade.naverapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShoppingCategoryCellIndex {
    INDEX_CODE(0),
    INDEX_CATEGORY_1(1),
    INDEX_CATEGORY_2(2),
    INDEX_CATEGORY_3(3),
    INDEX_CATEGORY_4(4)
    ;

    private final int index;
}
