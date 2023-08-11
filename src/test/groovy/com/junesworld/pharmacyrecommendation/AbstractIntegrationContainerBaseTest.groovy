package com.junesworld.pharmacyrecommendation

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

// Groovy Class
@SpringBootTest // 통합 Test 환경 (specification)
abstract class AbstractIntegrationContainerBaseTest extends  Specification {

    static final GenericContainer MY_REDIS_CONTAINER

    // Instance 화
    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6") // Docker Img 이름
            .withExposedPorts(6379) // Docker에서 expose한 port

        MY_REDIS_CONTAINER.start()

        // Springboot에 port 알려주기
        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }
}
