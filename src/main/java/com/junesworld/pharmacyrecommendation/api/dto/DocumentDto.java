package com.junesworld.pharmacyrecommendation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
public class DocumentDto {
    // @JsonProperty로 Mapping
    // category 사용 -> 약국명
    @JsonProperty("place_name")
    private String placeName;
    // 주소
    @JsonProperty("address_name")
    private String addressName;
    // 위도
    @JsonProperty("y")
    private double latitude;
    // 경도
    @JsonProperty("x")
    private double longitude;

    @JsonProperty("distance")
    private double distance;
}
