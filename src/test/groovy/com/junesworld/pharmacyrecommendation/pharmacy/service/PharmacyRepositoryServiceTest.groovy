package com.junesworld.pharmacyrecommendation.pharmacy.service

import com.junesworld.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.junesworld.pharmacyrecommendation.pharmacy.entity.Pharmacy
import com.junesworld.pharmacyrecommendation.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private  PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(inputAddress)
            .pharmacyName(name)
            .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }

    def "self invocation"() {
        given: // 약국 class DB에 저장
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .latitude(latitude)
            .longitude(longitude)
            .build()

        when: // bar method 시작점으로 약국 List 만들어 param 저장
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))
        // PharmacyRepositoryService
        // 내부에는 foo라는 method가 있고 Transactional 존재하고 저장
        // self invocation = 부가 기능 동작 X (롤백 동작X)

        then:
        def e = thrown(RuntimeException.class)
        def result = pharmacyRepositoryService.findAll()
        result.size() == 1 // 트랜젝션이 적용되지 않는다 (롤백 적용 X)
    }
}
