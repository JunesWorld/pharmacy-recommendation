package com.junesworld.pharmacyrecommendation.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    // 주소값으로 param를 만들어 전달
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    // URI 만드는 Method
    // 주소검색
    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        // uriBuilder에 Param 전달
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService buildUriByaddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    // Category를 이용한 장소검색 API 호출
    // 문자데이터를 위도, 경도로 변환 -> 기준으로 반경 지정, category로 검색
    public URI builderByCategorySearch(double latitude, double longitude, double radius, String category) {

        double meterRadius = radius * 1000;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code", category);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", meterRadius);
        uriBuilder.queryParam("sort", "distance"); // 위도 경도 기준으로 가까운 약국 거리순 sort

        URI uri = uriBuilder.build().encode().toUri();

        log.info("[KakaoAddressSearchService buildUriByCategorySearch] uri: {} ", uri);

        return uri;
    }
}
