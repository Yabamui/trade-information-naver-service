package com.service.openapi.trade.naverapi.client;

import com.service.openapi.trade.naverapi.config.client.NaverApiConfig;
import com.service.openapi.trade.naverapi.model.client.CategoryTrendRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "naver-api-client", value = "naver-api-client", url = "https://openapi.naver.com/v1", configuration = {NaverApiConfig.class})
public interface NaverApiClient {
    /**
     * 쇼핑인사이트 분야별 트렌드 조회
     */
    @PostMapping("/datalab/shopping/categories")
    Response getCategoryTrend(@RequestBody CategoryTrendRequest request);
}
