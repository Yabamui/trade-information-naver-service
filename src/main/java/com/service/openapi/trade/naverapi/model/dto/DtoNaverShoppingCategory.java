package com.service.openapi.trade.naverapi.model.dto;

import com.service.openapi.trade.naverapi.model.enums.ShoppingCategoryCellIndex;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

@Builder
@Getter
@Setter
public class DtoNaverShoppingCategory {
    private String code;
    private String category1;
    private String category2;
    private String category3;
    private String category4;

    public static DtoNaverShoppingCategory getInstance(final Row row, final FormulaEvaluator formulaEvaluator, final DataFormatter formatter) {
        return DtoNaverShoppingCategory.builder()
                .code(formatter.formatCellValue(row.getCell(ShoppingCategoryCellIndex.INDEX_CODE.getIndex()), formulaEvaluator))
                .category1(formatter.formatCellValue(row.getCell(ShoppingCategoryCellIndex.INDEX_CATEGORY_1.getIndex()), formulaEvaluator))
                .category2(formatter.formatCellValue(row.getCell(ShoppingCategoryCellIndex.INDEX_CATEGORY_2.getIndex()), formulaEvaluator))
                .category3(formatter.formatCellValue(row.getCell(ShoppingCategoryCellIndex.INDEX_CATEGORY_3.getIndex()), formulaEvaluator))
                .category4(formatter.formatCellValue(row.getCell(ShoppingCategoryCellIndex.INDEX_CATEGORY_4.getIndex()), formulaEvaluator))
                .build();
    }
}
