package com.service.openapi.trade.naverapi.model.client;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryTrendRequest {
    private final String startDate;
    private final String endDate;
    private final String timeUnit;
    private final String device;
    private final String gender;
    private final List<String> ages;
    private final List<DtoCategory> category;
    /**
     * startDate	string	Y	조회 기간 시작 날짜(yyyy-mm-dd 형식). 2017년 8월 1일부터 조회할 수 있습니다.
     * endDate	string	Y	조회 기간 종료 날짜(yyyy-mm-dd 형식)
     * timeUnit	string	Y	구간 단위
     * - date: 일간
     * - week: 주간
     * - month: 월간
     * category	array(JSON)	Y	분야 이름과 분야 코드 쌍의 배열. 최대 3개의 쌍을 배열로 설정할 수 있습니다.
     * category.name	string	Y	쇼핑 분야 이름
     * category.param	array(string)	Y	쇼핑 분야 코드. 네이버쇼핑에서 카테고리를 선택했을 때의 URL에 있는 cat_id 파라미터의 값으로 분야 코드를 확인할 수 있습니다.
     * device	string	N	기기. 검색 환경에 따른 조건입니다.
     * - 설정 안 함: 모든 기기에서의 검색 클릭 추이
     * - pc: PC에서의 검색 클릭 추이
     * - mo: 모바일 기기에서의 검색 클릭 추이
     * gender	string	N	성별. 검색 사용자의 성별에 따른 조건입니다.
     * - 설정 안 함: 모든 성별
     * - m: 남성
     * - f: 여성
     * ages	array(JSON)	N	연령. 검색 사용자의 연령에 따른 조건입니다.
     * - 설정 안 함: 모든 연령
     * - 10: 10∼19세
     * - 20: 20∼29세
     * - 30: 30∼39세
     * - 40: 40∼49세
     * - 50: 50∼59세
     * - 60: 60세 이상
     */
}
