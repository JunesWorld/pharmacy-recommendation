package com.junesworld.pharmacyrecommendation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
public class MetaDto {
    @JsonProperty("total_count")
    private Integer totalCount; // 검색어에 검색된 문서 수
}
