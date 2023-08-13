package com.junesworld.pharmacyrecommendation.direction.service;

import com.junesworld.pharmacyrecommendation.api.dto.DocumentDto;
import com.junesworld.pharmacyrecommendation.direction.entity.Direction;
import com.junesworld.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import com.junesworld.pharmacyrecommendation.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private final PharmacySearchService pharmacySearchService;

    // 최대 3개 return
    public List<Direction> buildDirectionList(DocumentDto documentDto) { // DocumentDto = 고객의 주소정보
        // 약국 데이터 조회
        List<PharmacyDto> pharmacyDtos = pharmacySearchService.searchPharmacyDtoList();

        // 거리계산 알고리즘을 이용하여, 고객과 약국 사이의 거리를 계산하고 sort
    }

    // Haversine formula
    // lat1, lon1 = 고객의 위도 경도
    // lat2, lat2 = 약국의 위도 경도
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; // Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
