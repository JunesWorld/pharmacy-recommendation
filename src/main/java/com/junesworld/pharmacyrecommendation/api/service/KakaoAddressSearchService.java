package com.junesworld.pharmacyrecommendation.api.service;

import com.junesworld.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.net.URI;

@Slf4j // log
@Service
@RequiredArgsConstructor // 생성자 주입
public class KakaoAddressSearchService {
    // http client module 필요 (의존성 추가) => config/RestTemplateConfig
    private final RestTemplate restTemplate;
    // uri 만드는 service
    private final KakaoUriBuilderService kakaoUriBuilderService;

    // application.yml에 있는 key값 가져오기
    @Value("${kakao.rest.api.key")
    private String kakaoRestApiKey;

    // 응답값을 Dto에 담아서 return 해주는 DTO
    // requestAddressSearch = Method 이름
    // 주소 = 문자열
    public KakaoApiResponseDto requestAddressSearch(String address) {
        // address validation check!
        if(ObjectUtils.isEmpty(address)) return null;

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        // key값 header에 담아 사용
        HttpHeaders headers = new HttpHeaders();
        // 필요한 header값은 kakao Guide 참고!
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK" +kakaoRestApiKey);
        // HttpEntity에 담아 header에 전달
        HttpEntity httpEntity = new HttpEntity<>(headers);

        // kakao api 호출
        // uir 전달
        // get 방식
        // Entity
        // Response Type
        // Body 부분만 필요
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();


    }
}
