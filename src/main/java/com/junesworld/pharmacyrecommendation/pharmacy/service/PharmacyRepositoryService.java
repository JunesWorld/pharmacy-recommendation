package com.junesworld.pharmacyrecommendation.pharmacy.service;

import com.junesworld.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import com.junesworld.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor // 생성자 주입
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) { // (pk값, 변경 될 주소)
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        // Validation Check!
        if(Objects.isNull(entity)) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }
}
