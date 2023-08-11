package com.junesworld.pharmacyrecommendation.pharmacy.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest // 통합 Test 환경
class PharmacyRepositoryTest extends Specification {

    @Autowired
    private PharmacyRepository pharmacyRepository

    def "test"() {

    }
}
