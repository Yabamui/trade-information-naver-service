package com.service.openapi.trade.naverapi.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.service.openapi.trade.naverapi.client.NaverApiClient;
import com.service.openapi.trade.naverapi.entity.naver.NaverShoppingCategory;
import com.service.openapi.trade.naverapi.model.client.CategoryTrendRequest;
import com.service.openapi.trade.naverapi.model.client.DtoCategory;
import com.service.openapi.trade.naverapi.model.dto.DtoNaverShoppingCategory;
import com.service.openapi.trade.naverapi.repository.naver.NaverShoppingCategoryRepository;
import com.service.openapi.trade.naverapi.util.JsonConvert;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
public class NaverApiStoreServiceTest {
    @Autowired
    private NaverApiClient naverApiClient;

    @Autowired
    private NaverShoppingCategoryRepository naverShoppingCategoryRepository;

    @Test
    public void uploadCategoryTest() {
        final String filePath = "/Users/sangik.lee/personal-project/category_20210911_180340.xls";
        final String sheetName = "Sheet0";

        try (final HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath))) {

            final Sheet sheet = workbook.getSheet(sheetName);

            if (Objects.isNull(sheet)) {
                return;
            }

            final FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
            final DataFormatter formatter = new DataFormatter();

            final List<DtoNaverShoppingCategory> dtoCategories = this.getShoppingCategoryDto(sheet, formulaEvaluator, formatter);

            assertThat(dtoCategories).isNotEmpty();

            this.naverShoppingCategoryRepository.saveAll(this.getNaverShoppingCategory(dtoCategories));
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private List<DtoNaverShoppingCategory> getShoppingCategoryDto(final Sheet sheet, final FormulaEvaluator formulaEvaluator, final DataFormatter formatter) {
        return StreamSupport.stream(sheet.spliterator(), false)
                .map(row -> DtoNaverShoppingCategory.getInstance(row, formulaEvaluator, formatter))
                .filter(f -> StringUtils.isNumeric(f.getCode()))
                .collect(Collectors.toList());
    }

    private List<NaverShoppingCategory> getNaverShoppingCategory(final List<DtoNaverShoppingCategory> dtoCategories) {
        return dtoCategories.stream().map(dto -> NaverShoppingCategory.builder()
                .code(dto.getCode())
                .category1(dto.getCategory1())
                .category2(dto.getCategory2())
                .category3(dto.getCategory3())
                .category4(dto.getCategory4())
                .build()).collect(Collectors.toList());
    }

    /**
     *     * startDate	string	Y	?????? ?????? ?????? ??????(yyyy-mm-dd ??????). 2017??? 8??? 1????????? ????????? ??? ????????????.
     *      * endDate	string	Y	?????? ?????? ?????? ??????(yyyy-mm-dd ??????)
     *      * timeUnit	string	Y	?????? ??????
     *      * - date: ??????
     *      * - week: ??????
     *      * - month: ??????
     *      * category	array(JSON)	Y	?????? ????????? ?????? ?????? ?????? ??????. ?????? 3?????? ?????? ????????? ????????? ??? ????????????.
     *      * category.name	string	Y	?????? ?????? ??????
     *      * category.param	array(string)	Y	?????? ?????? ??????. ????????????????????? ??????????????? ???????????? ?????? URL??? ?????? cat_id ??????????????? ????????? ?????? ????????? ????????? ??? ????????????.
     *      * device	string	N	??????. ?????? ????????? ?????? ???????????????.
     *      * - ?????? ??? ???: ?????? ??????????????? ?????? ?????? ??????
     *      * - pc: PC????????? ?????? ?????? ??????
     *      * - mo: ????????? ??????????????? ?????? ?????? ??????
     *      * gender	string	N	??????. ?????? ???????????? ????????? ?????? ???????????????.
     *      * - ?????? ??? ???: ?????? ??????
     *      * - m: ??????
     *      * - f: ??????
     *      * ages	array(JSON)	N	??????. ?????? ???????????? ????????? ?????? ???????????????.
     *      * - ?????? ??? ???: ?????? ??????
     *      * - 10: 10???19???
     *      * - 20: 20???29???
     *      * - 30: 30???39???
     *      * - 40: 40???49???
     *      * - 50: 50???59???
     *      * - 60: 60??? ??????
     */

    @Test
    public void naverShoppingCategoryTest() {
        final List<NaverShoppingCategory> categories = this.naverShoppingCategoryRepository.findAll();

        assertThat(categories).isNotEmpty();

        final LocalDate localDateNow = LocalDate.now();

        final Response response = naverApiClient.getCategoryTrend(CategoryTrendRequest.builder()
                        .startDate(localDateNow.minusWeeks(1).toString())
                        .endDate(localDateNow.toString())
                        .timeUnit("date")
                        .device(null)
                        .gender(null)
                        .ages(Arrays.asList("10", "20"))
                        .category(Collections.singletonList(DtoCategory.builder().build()))
                .build());

        log.info(String.valueOf(response.status()));

        try {
            final String responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            log.info(responseBody);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private Map<String, List<String>> getCategory1(final List<DtoNaverShoppingCategory> categoryDtos) {
        final Map<String, List<String>> categories = categoryDtos.stream()
                .filter(f -> StringUtils.isNotEmpty(f.getCategory1()))
                .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory1))
                .entrySet()
                .stream()
                .map(data -> {
                    final List<String> category2 = new ArrayList<>(data.getValue().stream()
                            .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory2)).keySet());

                    return Map.of(data.getKey(), category2);
                })
                .flatMap(data -> data.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info(JsonConvert.toString(categories));

        return categories;
    }

    private Map<String, List<String>> getCategory2(final List<DtoNaverShoppingCategory> categoryDtos) {
        final Map<String, List<String>> categories = categoryDtos.stream()
                .filter(f -> StringUtils.isNotEmpty(f.getCategory2()))
                .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory2))
                .entrySet()
                .stream()
                .map(data -> {
                    final List<String> category3 = new ArrayList<>(data.getValue().stream()
                            .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory3)).keySet());

                    return Map.of(data.getKey(), category3);
                })
                .flatMap(data -> data.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info(JsonConvert.toString(categories));

        return categories;
    }

    private Map<String, List<String>> getCategory3(final List<DtoNaverShoppingCategory> categoryDtos) {
        final Map<String, List<String>> categories = categoryDtos.stream()
                .filter(f -> StringUtils.isNotEmpty(f.getCategory3()))
                .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory3))
                .entrySet()
                .stream()
                .map(data -> {
                    final List<String> category4 = new ArrayList<>(data.getValue().stream()
                            .collect(Collectors.groupingBy(DtoNaverShoppingCategory::getCategory4)).keySet());

                    return Map.of(data.getKey(), category4);
                })
                .flatMap(data -> data.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info(JsonConvert.toString(categories));

        return categories;
    }
}
