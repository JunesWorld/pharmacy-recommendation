package com.junesworld.pharmacyrecommendation.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {

    // Test할 Service
    private KakaoUriBuilderService kakaoUriBuilderService

    // Method 시작 전 시작
    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"() {
        given:
        String address = "서울특별시 중구" // 명시적 타입 지정

        when: // def로 동적 타입 지정
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)

        then:
        prinln uri
    }
}

    // Decoding
//    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"() {
//        given:
//        String address = "서울특별시 중구" // 명시적 타입 지정
//        def charset = StandardCharsets.UTF_8
//
//        when: // def로 동적 타입 지정
//        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
//        def decodeResult = URLDecoder.decode(uri.toString(), charset)
//
//        then:
//        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울특별시 중구"
//    }
//}
