package com.junesworld.pharmacyrecommendation.direction.service;

import com.junesworld.pharmacyrecommendation.api.dto.DocumentDto;
import com.junesworld.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import com.junesworld.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.junesworld.pharmacyrecommendation.direction.dto.OutputDto;
import com.junesworld.pharmacyrecommendation.direction.entity.Direction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    public List<OutputDto> recommendPharmacyList(String address) { // 문자데이터로 입력받고
        // 위치기반 데이터로 전환
       KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

       // validation check
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        // 가까운 약국 찾기
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        // 공공기관 약국 데이터 및 거리계산 알고리즘 이용
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        // API 직접 추천
//        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertT0OutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto convertT0OutputDto(Direction direction) {
        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("todo") // todo
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();

    }
}
