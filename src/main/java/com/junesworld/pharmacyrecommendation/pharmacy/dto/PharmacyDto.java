package com.junesworld.pharmacyrecommendation.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Redis 위해 필요
// PharmacySearchService에서 약국 데이터 조회 후 담아서 사용
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDto {
    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;
}
