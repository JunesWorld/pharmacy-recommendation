package com.junesworld.pharmacyrecommendation.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
// Meta, Document Dto 한꺼번에 받을 수 있는 Dto
public class KakaoResponseDto {

    private MetaDto metaDto;

    private List<DocumentDto> documentList;
}
